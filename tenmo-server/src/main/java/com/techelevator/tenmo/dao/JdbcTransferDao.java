package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getAccountIdByUsername(String username){
        int accountId = 0;
        String sql = "SELECT account_id FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if(results.next()){
            accountId = results.getInt("account_id");
        }
        return accountId;
    }

    @Override
    public int getAccountIdByUserId(int userId){
        int accountId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()){
            accountId = results.getInt("account_id");
        }
        return accountId;
    }


    @Override
    public Transfer recordTransfer(Transfer transfer, String username) {
            int accountId = transfer.getAccountTo();
            String sql = "INSERT INTO transfer (amount, transfer_type_id, transfer_status_id, account_from, account_to) VALUES (?, ?, ?, ?, ?) ;";
            transfer.setTransferStatusId(1);
            transfer.setTransferTypeId(1);
            transfer.setAccountFrom(getAccountIdByUsername(username));
            transfer.setAccountTo(getAccountIdByUserId(accountId));
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo());
            transfer.setTransferStatusId(1);
            return transfer;

    }

    List<Transfer> transfers = new ArrayList<>();

    //check on this join logic
    @Override
    public List<Transfer> getTransferByUserId(int id) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer JOIN account ON transfer.account_to = account.account_id JOIN tenmo_user ON account.user_id = tenmo_user.user_id  WHERE tenmo_user.user_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            transfers.add(MapToTransfer(results));
        }
        return transfers;
    }
    @Override
    public List<Transfer> getTransfersByAccountId (int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE (account_from = ? OR account_to = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while(results.next()) {
            transfers.add(MapToTransfer(results));
        }
        return transfers;
    }
    @Override
    public void updateTransferStatusApproved (Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = 2 WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transfer.getTransferId());
    }

    @Override
    public void updateTransferStatusRejected (Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = 3 WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transfer.getTransferId());
    }



    private Transfer MapToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer(
        (rowSet.getInt("transfer_id")),
        (rowSet.getInt("transfer_type_id")),
        (rowSet.getInt("transfer_status_id")),
        (rowSet.getInt("account_from")),
        (rowSet.getInt("account_to")),
        (rowSet.getBigDecimal("amount")));

        return transfer;
    }
}
