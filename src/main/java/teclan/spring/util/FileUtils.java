package teclan.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    private static final DecimalFormat DF = new DecimalFormat("######0.00");

    public static void deleteFiles(File file) {
        if (!file.exists()) {
            LOGGER.warn("\nthe file {} is not exists!", file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFiles(files[i]);
            }
        }
        file.delete();
        LOGGER.info("文件已删除：{}",file.getAbsolutePath());
    }

    /**
     * @param fileName 文件路径
     * @param content  文件内容
     * @author Teclan 向文件追加内容，如果文件不存在，创建文件
     */
    public static void write2File(String fileName, String content) {
        FileWriter writer = null;
        try {
            creatIfNeed(fileName);
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }

    public static void creatIfNeed(String fileName) {
        try {
            File parentFile = new File(fileName).getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            new File(fileName).createNewFile();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static String getFileSize(File file) {
        String size = "";
        double length = file.length();

        length = length * 1.0 / 1024; // KB
        size = DF.format(length) + "KB";

        if (length > 1024) {
            length = length / 1024; // M
            size = DF.format(length) + "MB";
        }

        if (length > 1024) {
            length = length / 1024; // G
            size = DF.format(length) + "GB";
        }
        return size;
    }

    public static String getSuffix(String filePath) {
        return getSuffix(new File(filePath));
    }

    public static String getSuffix(File file) {
        String fileName = file.getName();
        LOGGER.info("即将解析文件类型:{}",fileName);
        String suffix = "未知";
        if (fileName.lastIndexOf(".") > 0) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        return suffix;
    }
}
