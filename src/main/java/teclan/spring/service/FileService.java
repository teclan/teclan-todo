package teclan.spring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import teclan.netty.service.FileServer;
import teclan.spring.util.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private final static PropertyConfigUtil propertyconfigUtil;
    private static String FILE_SERVER_ROOT = "" ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        FILE_SERVER_ROOT = propertyconfigUtil.getValue("file_root_dir");
    }

    public JSONObject download(JSONObject jsonObject){
        JSONArray array = jsonObject.getJSONArray("paths");
        List<String> paths = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            String o = array.getString(i);
            paths.add(o);
        }
        String local = jsonObject.getString("local");//下载到客户端的文件路径
        String remote = jsonObject.getString("remote");
        String user = jsonObject.getString("user");
        String tunnel = jsonObject.getString("tunnel");
        List<String> absolutePaths = new ArrayList<>();
        for(String path:paths){
            String absolutePath = FILE_SERVER_ROOT+"/"+remote+"/"+path;
            absolutePaths.add(absolutePath);
        }

        Map<String,Object> map =jdbcTemplate.queryForMap(String.format("select count(*) from file_mgr where absolute_path in ('%s') and owner<>'%s' and permissions<>'public'", Objects.joiner("','",absolutePaths),user));
        int count = Integer.valueOf(Objects.getOrDefault(map," count(*)","0"));

        if(count>0){
            return ResultUtil.get(403, "存在隐私文件，不允许下载，整个操作被拒绝！");
        }

        for(String path:paths){
            try {
                FileServer.push(tunnel,FILE_SERVER_ROOT+"/"+remote,local,path );
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }

        return ResultUtil.get(200, "发送指令成功");
    }


    public JSONObject root(){
        return ResultUtil.get(200, "查询成功",FILE_SERVER_ROOT);
    }
    public JSONObject upload(JSONObject jsonObject){
        JSONArray array = jsonObject.getJSONArray("paths");
        List<String> paths = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            String o = array.getString(i);
            paths.add(o);
        }
        String remote = jsonObject.getString("remote"); // 客户端上传的服务端路径
        for(String fileName:paths){
            try {
                String absolutePath = FileUtils.afterFormatFilePath(remote+"/"+fileName);
                String user = jsonObject.getString("user");
                 String dateTime = DateUtils.getDateTime();
                 String createdAt = dateTime;
                String updatedAt = dateTime;
                addFileMgrRecord(new Object[]{fileName,absolutePath,user,createdAt,updatedAt});
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return ResultUtil.get(200, "发送指令成功");
    }


    public JSONObject delete(JSONObject jsonObject){
        JSONArray array = jsonObject.getJSONArray("paths");
        List<String> paths = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            String o = array.getString(i);
            paths.add(o);
        }
        String remote = jsonObject.getString("remote"); // 客户端
        String user = jsonObject.getString("user");
        List<String> absolutePaths = new ArrayList<>();
        for(String path:paths){
            String absolutePath = FILE_SERVER_ROOT+"/"+remote+"/"+path;
            absolutePaths.add(absolutePath);
        }

        Map<String,Object> map =jdbcTemplate.queryForMap(String.format("select count(*) from file_mgr where absolute_path in ('%s') and owner<>'%s' and permissions<>'public'", Objects.joiner("','",absolutePaths),user));
        int count = Integer.valueOf(Objects.getOrDefault(map," count(*)","0"));

        if(count>0){
            return ResultUtil.get(403, "存在隐私文件，不允许删除，整个操作被拒绝！");
        }

        for(String path:absolutePaths){
            FileUtils.deleteFiles(new File(path));
        }
        return ResultUtil.get(200, "发送指令成功");
    }

    public JSONObject list(String path){

        String abPath = "";
        if("/".equals(path)){
            abPath = propertyconfigUtil.getValue("file_root_dir");
        }else {
            abPath = propertyconfigUtil.getValue("file_root_dir")+"/"+path;
        }

        File file = new File(abPath);
        if(!file.exists()){
            LOGGER.error("文件路径不存在{},请求路径:{}",abPath,path);
            return  ResultUtil.get(500, "文件路径不存在："+path);
        }

        JSONObject jsonObject = new JSONObject(true);

        JSONObject headers = new JSONObject(true);
        headers.put("name","文件名");
        headers.put("type","文件类型");
        headers.put("size","文件大小");
        headers.put("updatedAt","最后修改时间");
        headers.put("permissions","权限");
        headers.put("owner","作者");
        jsonObject.put("headers",headers);

        JSONArray datas = new JSONArray();
        JSONObject up = new JSONObject();
        up.put("name",".."); // 上级目录
        up.put("type","文件夹");
        up.put("size","");
        up.put("updatedAt", "");
        up.put("permissions", "");
        up.put("owner", "");
        datas.add(up);


        File[] files = file.listFiles();
        for(File f:files){
            JSONObject object = new JSONObject(true);
            object.put("name",f.getName());
            object.put("type",f.isDirectory()?"文件夹":FileUtils.getSuffix(f));
            object.put("size", FileUtils.getFileSize(f));
            object.put("updatedAt", DateUtils.getDataString(f.lastModified()));
            object.put("permissions", "公开");
            object.put("author", "");
            datas.add(object);
        }
        jsonObject.put("datas",datas);
         JSONObject ret = ResultUtil.get(200, "获取成功",jsonObject);
        LOGGER.info("获取文件列表返回:{}",ret);
        return ret;
    }


    /**
     * 将文件设为隐私，文件所有者必须为自己本人
     * @param jsonObject
     * @return
     */
    public JSONObject setPrivate(JSONObject jsonObject){
        JSONArray array = jsonObject.getJSONArray("paths");
        List<String> paths = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            String o = array.getString(i);
            paths.add(o);
        }
        String remote = jsonObject.getString("remote");
        String user = jsonObject.getString("user");
        List<String> absolutePaths = new ArrayList<>();
        for(String path:paths){
            String absolutePath = FILE_SERVER_ROOT+"/"+remote+"/"+path;
            absolutePaths.addAll(FileUtils.getFileLis(new File(absolutePath)));
        }

        Map<String,Object> map =jdbcTemplate.queryForMap(String.format("select count(*) from file_mgr where absolute_path in ('%s') and owner='%s'", Objects.joiner("','",absolutePaths),user));
        int count = Integer.valueOf(Objects.getOrDefault(map," count(*)","0"));

        if(count!=absolutePaths.size()){
            return ResultUtil.get(403, "只能对本人上传的文件设置为隐私，当前选中文件中存在非本人文件，整个操作被拒绝！");
        }

        return ResultUtil.get(200, "设置成功");
    }


    /**
     * 将文件设为公开，文件所有者必须为自己本人
     * @param jsonObject
     * @return
     */
    public JSONObject setPublic(JSONObject jsonObject){
        JSONArray array = jsonObject.getJSONArray("paths");
        List<String> paths = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            String o = array.getString(i);
            paths.add(o);
        }
        String remote = jsonObject.getString("remote");
        String user = jsonObject.getString("user");
        List<String> absolutePaths = new ArrayList<>();
        for(String path:paths){
            String absolutePath = FILE_SERVER_ROOT+"/"+remote+"/"+path;
            absolutePaths.addAll(FileUtils.getFileLis(new File(absolutePath)));
        }

        Map<String,Object> map =jdbcTemplate.queryForMap(String.format("select count(*) from file_mgr where absolute_path in ('%s') and owner='%s'", Objects.joiner("','",absolutePaths),user));
        int count = Integer.valueOf(Objects.getOrDefault(map," count(*)","0"));

        if(count!=absolutePaths.size()){
            return ResultUtil.get(403, "只能对本人上传的文件设置为公开，当前选中文件中存在非本人文件，整个操作被拒绝！");
        }

        return ResultUtil.get(200, "设置成功");
    }



    private void addFileMgrRecord(Object[] values){
        jdbcTemplate.update("insert into file_mgr (`filename`,`absolute_path`,`owner`,`created_at`,`updated_at`) values (?,?,?,?,?)",values);
    }
}
