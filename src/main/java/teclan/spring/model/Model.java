package teclan.spring.model;

import com.alibaba.fastjson.JSON;

public abstract class Model {
    public  abstract String getTableName();

    public String toString(){
        return JSON.toJSONString(this);
    }
}
