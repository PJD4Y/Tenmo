package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{
    private JdbcAccountDao dao;

    @Before
    public void setup() {

      //  dao = new JdbcAccountDao(dataSource);
    }
    @Test
    public void returnsCorrectBalance(){
        User testUser = new User();
        testUser.setId(0);
        testUser.setUsername("JohnnyH");
        testUser.setPassword("asdfghjkl");

        BigDecimal expected = BigDecimal.valueOf(100.00);

        BigDecimal actual = dao.getBalance(testUser);

        Assert.assertEquals(expected, actual);
    }


}
