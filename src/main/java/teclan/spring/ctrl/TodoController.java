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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/todo")
public class TodoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

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


            int total = jdbcTemplate.queryForObject("select count(*) from todo",Integer.class);
            int totalPages = PagesUtils.getTotalPage(total,pageSize);

            currentPage = PagesUtils.getCurrentPage(totalPages,currentPage);

            int offset = PagesUtils.getOffset(currentPage,pageSize);

            List<Map<String,Object>> maps = jdbcTemplate.queryForList(String.format("select id,title,content,parent_id from todo limit %s,%s",offset,pageSize));

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

           int row = jdbcTemplate.update("delete from todo where id=?",id);

           if(row>0){
               return ResultUtil.get(200, "删除成功");
           }else{
               return ResultUtil.get(403, "记录不存在");
           }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "删除失败",e.getMessage());
        }

    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            int id = parameter.getIntValue("id");

            List<Map<String,Object>> maps = jdbcTemplate.queryForList(String.format("select a.id,a.title,a.content,a.parent_id,b.title parent_title from todo a left join todo b on a.parent_id=b.id where a.id=%s",id));

            return ResultUtil.get(200, "查询成功",maps.get(0));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "查询失败",e.getMessage());
        }
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public JSONObject update(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            String id = parameter.getString("id");
            String title = parameter.getString("title");
            String content = parameter.getString("content");
            String parent_id = parameter.getString("parent_id");


            int row = jdbcTemplate.update("update todo set title=?,content=?,parent_id=? where id=?",title,content,parent_id,id);

            if(row>0){
                return ResultUtil.get(200, "修改成功");
            }else{
                return ResultUtil.get(403, "记录不存在");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "修改失败",e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllIdAndTitle")
    @ResponseBody
    public JSONObject getAllIdAndTitle(HttpServletRequest request, HttpServletResponse response) {
        try {

            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,title from todo");
            return ResultUtil.get(200, "查询成功",maps);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "获取失败", e.getMessage());
        }
    }
}
