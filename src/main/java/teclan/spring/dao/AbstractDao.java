package teclan.spring.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.Asserts;
import teclan.spring.util.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractDao {

    public String transform4Query(Map<String, Object> parameter){

        StringBuffer sb = new StringBuffer(" where 1=1 ");

        for(String key:parameter.keySet()){
            if("limit".equalsIgnoreCase(key) || "offset".equalsIgnoreCase(key) || "pageSize".equalsIgnoreCase(key) || "oderBy".equalsIgnoreCase(key)|| "sort".equalsIgnoreCase(key)|| "currentPage".equalsIgnoreCase(key)){
                continue;
            }
            sb.append(key.endsWith("_fuzzy")?String.format(" and  %s like '%s' ",key.substring(0,key.length()-6),"%"+parameter.get(key)+"%"):String.format(" and %s = '%s' ",key,parameter.get(key)));
        }

        sb.append(String.format(" order by %s %s limit %s,%s ",parameter.get("oderBy"),parameter.get("sort"),parameter.get("offset"),parameter.get("limit")));

        return sb.toString();
    }

    public String transform4Count(Map<String, Object> parameter){
        StringBuffer sb = new StringBuffer(" where 1=1 ");
        for(String key:parameter.keySet()){

            if("limit".equalsIgnoreCase(key) || "offset".equalsIgnoreCase(key) || "pageSize".equalsIgnoreCase(key) || "oderBy".equalsIgnoreCase(key)|| "sort".equalsIgnoreCase(key)|| "currentPage".equalsIgnoreCase(key)){
                continue;
            }

            sb.append(key.endsWith("_fuzzy")?String.format(" and  %s like '%s' ",key.substring(0,key.length()-6),"%"+parameter.get(key)+"%"):String.format(" and %s = '%s' ",key,parameter.get(key)));
        }

        return sb.toString();
    }

    public QueryObject getQueryObject4Insert(JSONObject o, String tableName){
        QueryObject queryObject = new QueryObject();

        List<String> colomus = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        Object[] values = new Object[o.keySet().size()];
        int i=0;
        for(String key:o.keySet()){
            colomus.add(camelToUnderline(key));
            values[i++]=o.get(key);
        }
        for(i=0;i<colomus.size();i++){
            placeholders.add("?");
        }

        queryObject.setSql(String.format("insert into %s (%s) values (%s)",tableName, Objects.Joiner(",",colomus),Objects.Joiner(",",placeholders)));
        queryObject.setValues(values);

        return queryObject;
    }

    public static String camelToUnderline(String v){

        StringBuffer sb = new StringBuffer();
        for(int i=0;i<v.length();i++){
            if(Character.isUpperCase(v.charAt(i))){
                sb.append("_").append(Character.toLowerCase(v.charAt(i)));
            }else{
                sb.append(Character.toLowerCase(v.charAt(i)));
            }
        }
        return sb.toString();
    }

}
