package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    AccountController(JdbcTemplate jdbcTemplate) {
        this.accountDao = new JdbcAccountDao(jdbcTemplate);
        this.userDao = new JdbcUserDao(jdbcTemplate);
        this.transferDao = new JdbcTransferDao(jdbcTemplate);
    }


    @RequestMapping(path = "/accounts/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        return accountDao.getBalance(userDao.findByUsername(principal.getName()));
    }



//    @PreAuthorize("permitAll")
//    @RequestMapping(path = "/accounts/{id}/{amount}", method = RequestMethod.PUT)
//    public void transferMoney(@PathVariable int id, @PathVariable BigDecimal amount, Principal principal) {
//        Transfer transfer = new Transfer();
//        transfer.setAccountFrom(userDao.findIdByUsername(principal.getName()));
//        transfer.setAccountTo(id);
//        transfer.setAmount(amount);
//        // transfer.setTransferId(1);
//        transfer.setTransferTypeId(2);
//        transfer.setTransferStatusId(1);
//        if(userDao.findIdByUsername(principal.getName()) != id) {
//            transferDao.recordTransfer(transfer);
//            accountDao.transferMoneyTo(transfer);
//            accountDao.transferMoneyFrom(transfer);
//        }
//
//}

}
