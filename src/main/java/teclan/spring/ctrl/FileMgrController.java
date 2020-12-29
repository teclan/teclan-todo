package teclan.spring.ctrl;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import teclan.spring.service.FileMgrService;
import teclan.spring.service.LogService;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

@Controller
@RequestMapping("/filemgr")
public class FileMgrController {

    @Autowired
    private FileMgrService fileMgrService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject query(HttpServletRequest request, HttpServletResponse response) {
        return fileMgrService.query(request);
    }
}
