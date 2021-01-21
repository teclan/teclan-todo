package teclan.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import teclan.netty.handler.AbstractFileInfoHandler;
import teclan.netty.handler.FileServerHanlder;
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
        jdbcTemplate.update("insert into file_push (`id`,`remote`,`src`,`dst`) values (?,?,?,?)", fileInfo.getId(), remote,FileUtils.afterFormatFilePath(fileInfo.getSrcFileName()),FileUtils.afterFormatFilePath(fileInfo.getDstFileName()));
    }

    @Override
    public void writeFail(FileInfo fileInfo) throws Exception {
        fileInfo.setPackageType(PackageType.CMD_NEED_REPEAT);
        send(FileServerHanlder.getClinetInfos(fileInfo.getRouter()),fileInfo);
        LOGGER.info("接收文件失败,请求重发:{}",fileInfo);
    }

}
