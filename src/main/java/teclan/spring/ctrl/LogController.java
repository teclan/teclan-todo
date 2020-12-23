package teclan.spring.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import teclan.spring.dao.LogDao;
import teclan.spring.model.Log;
import teclan.spring.service.LogService;
import teclan.spring.util.HttpTool;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/log")
public class LogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject get(HttpServletRequest request, HttpServletResponse response,String id) {
       return logService.findOne(id);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response,String id) {
        return logService.delete(id);
    }

    @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteBatch(HttpServletRequest request, HttpServletResponse response,String ids) {
        return logService.deleteBatch(ids);
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(HttpServletRequest request, HttpServletResponse response) {
        return logService.create(request);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(HttpServletRequest request, HttpServletResponse response) {
        return logService.update(request);
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject query(HttpServletRequest request, HttpServletResponse response) {
        return logService.query(request);
    }



}
