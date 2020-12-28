package teclan.spring.dao;

public class QueryObject {

    private String sql;
    private Object[] values;

    public QueryObject(){

    }

    public QueryObject(String sql,Object[] values){
        this.sql=sql;
        this.values=values;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
