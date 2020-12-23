package teclan.spring.util;

import java.util.HashMap;
import java.util.Map;

public class PagesUtils {

    public static Map<String, Object> getPageInfo(int currentPage, int pageSize, int totals) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", Math.ceil(totals * 1.0 / pageSize));
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        map.put("totals", totals);
        map.put("isFirst", currentPage==1); // 是否第一页
        map.put("isLast", currentPage==Math.ceil(totals * 1.0 / pageSize)); // 是否最后一页

        return map;
    }

    public static int getOffset(int currentPage, int pageSize) {
        return currentPage>0?(currentPage-1)*pageSize:0;
    }

    public static int getTotalPage(int total,int pageSize){
        return (int)Math.ceil(total*1.0/pageSize);

    }

    public static int getCurrentPage(int totalPages,int currentPage){
        return currentPage>totalPages?totalPages:currentPage;

    }
}
