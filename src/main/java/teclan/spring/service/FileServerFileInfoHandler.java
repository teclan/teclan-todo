package teclan.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import teclan.netty.handler.AbstractFileInfoHandler;
import teclan.netty.model.FileInfo;
import teclan.netty.model.PackageType;
import teclan.netty.service.FileServer;
import teclan.spring.util.FileUtils;
import teclan.spring.util.Objects;
import teclan.spring.util.ResultUtil;

import java.io.File;
import java.util.Map;

@Service
public class FileServerFileInfoHandler extends AbstractFileInfoHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServerFileInfoHandler.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void push(String remote,FileInfo fileInfo) throws Exception {
        LOGGER.info("推送文件:{}",fileInfo);
        jdbcTemplate.update("insert into file_push (`id`,`remote`,``src`,`dst`) values (?,?,?,?)", fileInfo.getId(), remote,FileUtils.afterFormatFilePath(fileInfo.getSrcFileName()),FileUtils.afterFormatFilePath(fileInfo.getDstFileName()));
    }

    @Override
    public void writeFail(FileInfo fileInfo) throws Exception {
        LOGGER.info("接收文件失败,请求重发:{}",fileInfo);

        Map<String,Object> map =jdbcTemplate.queryForMap(String.format("select remote,src,dst from file_push where id =?", fileInfo.getId()));
        String remote = Objects.getOrDefault(map,"remote");
        String src = Objects.getOrDefault(map,"src");
        String dst = Objects.getOrDefault(map,"dst");

        File file = new File(src);
        if(!file.exists()){
            LOGGER.warn("客户端请求重复下载，但文件不存在...{}",src);
            return;
        }

        try {
            FileServer.push(remote,src,dst,file.getName());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }

    }
}
