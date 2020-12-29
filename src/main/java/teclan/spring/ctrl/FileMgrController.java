package teclan.spring.ctrl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import teclan.spring.service.AbstractService;
import teclan.spring.service.FileMgrService;
import teclan.spring.service.LogService;
import teclan.spring.util.HttpTool;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

@Controller
@RequestMapping("/filemgr")
public class FileMgrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    private FileMgrService fileMgrService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject query(HttpServletRequest request, HttpServletResponse response) {
        return fileMgrService.query(request);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            String id = parameter.getString("id");

            return fileMgrService.delete(id);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "删除失败",e.getMessage());
        }

    }
}
