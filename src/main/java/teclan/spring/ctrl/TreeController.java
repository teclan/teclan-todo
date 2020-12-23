package teclan.spring.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teclan.spring.util.HttpTool;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/tree")
public class TreeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeController.class);

    @RequestMapping(value = "/test")
    @ResponseBody
    public JSONObject test(HttpServletRequest request, HttpServletResponse response) {
        try {

            String json = HttpTool.readJSONString(request);
            JSONObject parameter = JSON.parseObject(json);

            Integer id = parameter.getIntValue("id");

            return ResultUtil.get(200, "成功",getChridren(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "失败");
        }
    }

    // 模拟数据
    private JSONArray getChridren(int parentId) {

        JSONArray childre = new JSONArray();

        for(int i=1;i<=2;i++){
            JSONObject node = new JSONObject();
            int id = parentId*10+i;
            node.put("id",id);
            node.put("name","节点"+id);
            node.put("parentId",parentId);
            childre.add(node);
        }

        return childre;
    }

}
