package teclan.spring.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teclan.spring.dao.Dao;
import teclan.spring.dao.FileMgrDao;
import teclan.spring.util.FileUtils;
import teclan.spring.util.ResultUtil;

import java.io.File;
import java.util.Map;

@Service
public class FileMgrService extends AbstractService {
    private final Logger LOGGER = LoggerFactory.getLogger(FileMgrService.class);
    @Autowired
    private FileMgrDao fileMgrDao;

    @Override
    public JSONObject delete(String id) {
        try {
            Map<String,Object> o =  getDao().findOne(id);
            String absolutePath = o.getOrDefault("absolute_path","").toString();
            String filename = o.getOrDefault("filename","").toString();
            FileUtils.deleteFiles(new File((absolutePath+File.separator+filename)));
            int row = getDao().delete(id);
            return ResultUtil.get(200, row > 0 ? "删除成功" : "删除失败", "受影响行数:" + row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.get(500, "删除失败", e.getMessage());
        }
    }



    @Override
    protected Dao getDao() {
        return fileMgrDao;
    }

}
