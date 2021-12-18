package io.yongjiang.datasource.mapper;

import io.yongjiang.datasource.mapper.provider.UserProvider;
import io.yongjiang.datasource.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * Created by Yong on 2021/12/18.
 */
@Mapper
@Repository
public interface UserMapper {

    @Results(value = {
        @Result(property = "accountId", column = "account_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
        @Result(property = "accountName", column = "account_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
        @Result(property = "phoneNumber", column = "phone_number", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = UserProvider.class, method = "queryUser")
    User queryUser(@Param("accountId") String accountId);
}
