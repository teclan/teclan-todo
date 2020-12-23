package teclan.spring.ctrl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/upload1")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request) throws IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = "E:/upload/" + file.getOriginalFilename();

                    File f = new File(path);
                    f.getParentFile().mkdirs();

                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        return ResultUtil.get(200, "上传成功");
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject fileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
        saveFiles(file);
        return ResultUtil.get(200, "上传成功");

    }


    public static String saveFiles( MultipartFile file) {
            // 保存文件
            try {
                String path = "E:/upload/" + file.getOriginalFilename();
                File dest = new File(path);
                if (!dest.exists()) {//不存在创建目录
                    dest.mkdirs();
                }
                file.transferTo(dest);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "";
    }


    public static void inputStreamToFile(InputStream is, String fileName) throws IOException {
        OutputStream outputStream = null;
        File file = new File(fileName);

        file.getParentFile().mkdirs();


        OutputStream os =new FileOutputStream(file);

        int bytesRead =0;
        byte[] buffer =new byte[8192];

        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        is.close();
        os.close();
    }

}
