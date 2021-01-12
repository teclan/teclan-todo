package teclan.spring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import teclan.netty.service.FileServer;
import teclan.spring.ctrl.AuthenticateController;
import teclan.spring.util.DateUtils;
import teclan.spring.util.PropertyConfigUtil;
import teclan.spring.util.ResultUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private static final DecimalFormat DF = new DecimalFormat("######0.00");
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private final static PropertyConfigUtil propertyconfigUtil;
    private static String FILE_SERVER_ROOT = "" ;

    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        FILE_SERVER_ROOT = propertyconfigUtil.getValue("file_root_dir");
    }

    public JSONObject download(JSONObject jsonObject){
        String serverPath = jsonObject.getString("serverPath"); // 服务器路径
        String[] paths = jsonObject.getString("paths").split(",");//服务器文件路径
        String localPath = jsonObject.getString("localPath");//下载到客户端的文件路径
        String remote = jsonObject.getString("remote"); // 客户端
        for(String path:paths){
            try {
                // TODO
                // 添加权限控制
                FileServer.push(remote,FILE_SERVER_ROOT+File.separator+serverPath,localPath,path );
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return ResultUtil.get(200, "发送指令成功");
    }


    public JSONObject upload(JSONObject jsonObject){
        String serverPath = jsonObject.getString("serverPath"); // 服务器路径
        String[] paths = jsonObject.getString("paths").split(",");//服务器文件路径
        String localPath = jsonObject.getString("localPath");//下载到客户端的文件路径
        String remote = jsonObject.getString("remote"); // 客户端
        for(String path:paths){
            try {
                // TODO
                // 添加权限控制
                // 入库记录
                //
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return ResultUtil.get(200, "发送指令成功");
    }

    public JSONObject delete(JSONObject jsonObject){
        String[] paths = jsonObject.getString("paths").split(",");//服务器文件路径
        String remote = jsonObject.getString("remote"); // 客户端
        for(String path:paths){
            try {
                // TODO
                // 添加权限控制
                // 入库记录并删除文件
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return ResultUtil.get(200, "发送指令成功");
    }

    public JSONObject list(String path){

        String abPath = "";
        if("/".equals(path)){
            abPath = propertyconfigUtil.getValue("file_root_dir");
        }else {
            abPath = propertyconfigUtil.getValue("file_root_dir")+File.separator+path;
        }

        File file = new File(abPath);
        if(!file.exists()){
            LOGGER.error("文件路径不存在{},请求路径:{}",abPath,path);
            return  ResultUtil.get(500, "文件路径不存在："+path);
        }

        JSONObject jsonObject = new JSONObject(true);

        JSONObject headers = new JSONObject(true);
        headers.put("name","文件明名");
        headers.put("size","文件大小");
        headers.put("updatedAt","最后修改时间");
        headers.put("auth","权限");
        jsonObject.put("headers",headers);

        JSONArray datas = new JSONArray();
        JSONObject up = new JSONObject();
        up.put("name",".."); // 上级目录
        up.put("size","");
        up.put("updatedAt", "");
        up.put("auth", "");
        datas.add(up);


        File[] files = file.listFiles();
        for(File f:files){
            JSONObject object = new JSONObject(true);
            object.put("name",f.getName());
            object.put("size",getFileSize(f));
            object.put("updatedAt", DateUtils.getDataString(f.lastModified()));
            object.put("auth", "公开");
            datas.add(object);
        }
        jsonObject.put("datas",datas);
         JSONObject ret = ResultUtil.get(200, "获取成功",jsonObject);
        LOGGER.info("获取文件列表返回:{}",ret);
        return ret;
    }

    private static String getFileSize(File file) {
        String size = "";
        double length = file.length();

        length = length * 1.0 / 1024; // KB
        size = DF.format(length) + "KB";

        if (length > 1024) {
            length = length / 1024; // M
            size = DF.format(length) + "MB";
        }

        if (length > 1024) {
            length = length / 1024; // G
            size = DF.format(length) + "GB";
        }
        return size;
    }
}
