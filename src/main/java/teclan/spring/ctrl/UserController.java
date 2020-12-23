package teclan.spring.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teclan.spring.util.HttpTool;
import teclan.spring.util.PagesUtils;
import teclan.spring.util.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/page")
    @ResponseBody
    public JSONObject page(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            int currentPage = parameter.getInteger("currentPage");
            int pageSize = parameter.getInteger("pageSize");


            LOGGER.info("\n\n查询用户信息: {}",parameter);


            int total = jdbcTemplate.queryForObject("select count(*) from user_info",Integer.class);
            int totalPages = PagesUtils.getTotalPage(total,pageSize);

            currentPage = PagesUtils.getCurrentPage(totalPages,currentPage);

            int offset = PagesUtils.getOffset(currentPage,pageSize);

            List<Map<String,Object>> maps = jdbcTemplate.queryForList(String.format("select id,name,phone,id_card from user_info limit %s,%s",offset,pageSize));

            return ResultUtil.get(200, "查询成功",maps,PagesUtils.getPageInfo(currentPage,pageSize,total));


        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "查询失败",e.getMessage());
        }


    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            int id = parameter.getIntValue("id");

           int row = jdbcTemplate.update("delete from user_info where id=?",id);

           if(row>0){
               return ResultUtil.get(200, "删除成功");
           }else{
               return ResultUtil.get(403, "记录不存在");
           }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(200, "删除失败",e.getMessage());
        }

    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            int id = parameter.getIntValue("id");

            List<Map<String,Object>> maps = jdbcTemplate.queryForList(String.format("select id,name,phone,id_card from user_info where id=%s",id));

            return ResultUtil.get(200, "查询成功",maps.get(0));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(200, "查询失败",e.getMessage());
        }
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public JSONObject update(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            String id = parameter.getString("id");
            String name = parameter.getString("name");
            String phone = parameter.getString("phone");
            String idCard = parameter.getString("id_card");


            int row = jdbcTemplate.update("update user_info set name=?,phone=?,id_card=? where id=?",name,phone,idCard,id);

            if(row>0){
                return ResultUtil.get(200, "修改成功");
            }else{
                return ResultUtil.get(403, "记录不存在");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(200, "修改失败",e.getMessage());
        }
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            String id = parameter.getString("id");
            String name = parameter.getString("name");
            String phone = parameter.getString("phone");
            String idCard = parameter.getString("id_card");


            int row = jdbcTemplate.update("insert into user_info (id,code,name,phone,id_card) values (?,?,?,?,?) ",id,id,name,phone,idCard);

            if(row>0){
                return ResultUtil.get(200, "添加成功");
            }else{
                return ResultUtil.get(403, "添加失败");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(200, "添加失败",e.getMessage());
        }
    }
}
