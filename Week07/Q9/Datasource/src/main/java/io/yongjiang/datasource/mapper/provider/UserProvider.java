package io.yongjiang.datasource.mapper.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {

    private static final String tableName = "customer_account";

    public String queryUser(Map<String, Object> parameter) {
        return new SQL() {
            {
                SELECT("account_id, account_name, phone_number");

                FROM(tableName);

                WHERE("account_id = #{accountId}");
            }
        }.toString();
    }
}
