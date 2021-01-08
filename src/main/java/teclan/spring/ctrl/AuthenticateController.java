package teclan.spring.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teclam.jwt.JwtFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import teclan.spring.cache.GuavaCache;
import teclan.spring.util.HttpTool;
import teclan.spring.util.Objects;
import teclan.spring.util.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 认证控制器
 */
@Controller
public class AuthenticateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public void login(HttpServletRequest request, HttpServletResponse response,ModelAndView modelAndView) throws IOException {
        response.sendRedirect("/teclan-spring-mvc/resource/login/login.html");
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login( HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);
            String user = parameter.getString("account");// 账号，唯一
            String password = parameter.getString("password");

           List<Map<String,Object>> maps = jdbcTemplate.queryForList("select * from user_info where account=?",user);

            if(maps==null || maps.isEmpty()){
                throw new Exception("用户不存在");
            }
            Map<String,Object> map = maps.get(0);
            if(!password.equals(map.get("password"))){
                throw new Exception("密码错误");
            }

            String token = JwtFactory.getJws(user);

            map.put("user",user);
            map.put("token",token);

            request.setAttribute("user",user);
            request.setAttribute("token",token);

            GuavaCache.set(user,token);

            return ResultUtil.get(200, "登录成功",map);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/logout")
    @ResponseBody
    public JSONObject logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);
            String user = parameter.getString("account");// 账号，唯一

            GuavaCache.remove(user);

            return ResultUtil.get(200, "登出成功");

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, e.getMessage());
        }
    }
}
