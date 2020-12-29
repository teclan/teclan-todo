package teclan.spring.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teclan.spring.dao.Dao;
import teclan.spring.dao.FileMgrDao;
import teclan.spring.util.HttpTool;
import teclan.spring.util.PagesUtils;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class FileMgrService extends AbstractService {
    private final Logger LOGGER = LoggerFactory.getLogger(FileMgrService.class);
    @Autowired
    private FileMgrDao fileMgrDao;

    @Override
    protected Dao getDao() {
        return fileMgrDao;
    }

}
