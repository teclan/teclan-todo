package teclan.spring.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import teclan.spring.model.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface LogDao extends Dao{

}
