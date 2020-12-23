package teclan.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teclan.spring.dao.Dao;
import teclan.spring.model.Log;
import teclan.spring.model.Model;
import teclan.spring.util.HttpTool;
import teclan.spring.util.PagesUtils;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractService implements Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    protected abstract Dao getDao();

    @Override
    public JSONObject findOne(String id) {
        try {
            Object model =  getDao().findOne(id);
            return ResultUtil.get(200, "查询成功", model);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "查询失败", e.getMessage());
        }
    }

    @Override
    public JSONObject delete(String id) {
        try {
            int row = getDao().delete(id);
            return ResultUtil.get(200, row > 0 ? "删除成功" : "删除失败", "受影响行数:" + row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "删除失败", e.getMessage());
        }
    }

    @Override
    public JSONObject deleteBatch(String ids) {
        try {
            int row = getDao().deleteBatch(ids.split(","));
            return ResultUtil.get(200, row > 0 ? "删除成功" : "删除失败", "受影响行数:" + row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "删除失败", e.getMessage());
        }
    }

    @Override
    public JSONObject create(HttpServletRequest request) {
        try {
            JSONObject json = HttpTool.readJSONParam(request);
            int row = getDao().create(json);
            return ResultUtil.get(200, row > 0 ? "创建成功" : "创建失败", "受影响行数:" + row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "创建失败", e.getMessage());
        }
    }

    @Override
    public JSONObject update(HttpServletRequest request) {
        try {
            JSONObject json = HttpTool.readJSONParam(request);
            int row = getDao().update(json);
            return ResultUtil.get(200, row > 0 ? "更新成功" : "更新失败", "受影响行数:" + row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "更新失败", e.getMessage());
        }
    }

    @Override
    public JSONObject query(HttpServletRequest request) {
        try {
            JSONObject jsonObject = HttpTool.readJSONParam(request);

            if (!jsonObject.containsKey("currentPage")
                    || !jsonObject.containsKey("pageSize")
                    || !jsonObject.containsKey("oderBy")
                    || !jsonObject.containsKey("sort")) {
                throw new Exception("[currentPage,pageSize,orderBy,sort]字段中至少缺失一个");
            }

            int pageSize =jsonObject.getInteger("pageSize");
            int currentPage = jsonObject.getInteger("currentPage");
            int total = getDao().countQuery(jsonObject);
            int totalPages = PagesUtils.getTotalPage(total, pageSize);
            currentPage = PagesUtils.getCurrentPage(totalPages, currentPage);
            int offset = PagesUtils.getOffset(currentPage, pageSize);

            jsonObject.put("offset", offset);
            jsonObject.put("limit", pageSize);

            List<Object> models = getDao().query(jsonObject);

            return ResultUtil.get(200, "查询成功", models,PagesUtils.getPageInfo(currentPage,pageSize,total));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "查询失败", e.getMessage());
        }
    }
}
