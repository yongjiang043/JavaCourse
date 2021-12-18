package io.yongjiang.datasource.services;

import static org.junit.Assert.assertEquals;

import io.yongjiang.datasource.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testQueryUser() {

        String accountId = "001";
        User user1 = userService.queryUserFromMaster(accountId);

        User user2 = userService.queryUserFromSlave(accountId);

        assertEquals(user1.getAccountName(), "master_user");

        assertEquals(user2.getAccountName(), "slave_user");
    }
}
