package teclan.spring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import teclan.spring.ctrl.AuthenticateController;
import teclan.spring.util.DateUtils;
import teclan.spring.util.ResultUtil;

import java.io.File;
import java.text.DecimalFormat;

@Service
public class FileService {
    private static final DecimalFormat DF = new DecimalFormat("######0.00");
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);


    public JSONObject list(String path){

        File file = new File(path);
        if(!file.exists()){
            return  ResultUtil.get(500, "文件路径不存在："+file);
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
