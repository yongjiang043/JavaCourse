package io.yongjiang.datasource.services;

import io.yongjiang.datasource.annotation.DataSourceSpec;
import io.yongjiang.datasource.constant.Constants;
import io.yongjiang.datasource.mapper.UserMapper;
import io.yongjiang.datasource.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserFromMaster(String accountId) {
        return userMapper.queryUser(accountId);
    }

    @DataSourceSpec(name = Constants.SLAVE)
    public User queryUserFromSlave(String accountId) {
        return userMapper.queryUser(accountId);
    }
}


