package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao {

private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //public JdbcAccountDao(DataSource dataSource){
//    this.jdbcTemplate = new JdbcTemplate(dataSource);
//}
    @Override
    public BigDecimal getBalance(User user) {
        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE tenmo_user.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        BigDecimal balance = BigDecimal.valueOf(0);
        while(results.next()){
            balance = results.getBigDecimal("balance");
        }
       // String stringResults = results.toString();
       // Double doubleResults = Double.parseDouble(stringResults);
       // BigDecimal decimalResults = BigDecimal.valueOf(doubleResults);
       // return decimalResults;
        return balance;
    }

    @Override
    public void transferMoneyTo(Transfer transfer) {
        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountTo());
    }


    @Override
    public void transferMoneyFrom(Transfer transfer) {
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom());
    }



}
