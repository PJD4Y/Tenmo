package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;

    TransferController (JdbcTemplate jdbcTemplate) {
        this.transferDao = new JdbcTransferDao(jdbcTemplate);
        this.accountDao = new JdbcAccountDao(jdbcTemplate);
        this.userDao = new JdbcUserDao(jdbcTemplate);
    }

    @RequestMapping (path = "/transfer/{id}", method = RequestMethod.GET)
    public List<Transfer> getTransferByAccountId (@PathVariable int id) {

        return transferDao.getTransfersByAccountId(id);
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping (path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer (@Valid @RequestBody Transfer transfer) {
        Transfer transfer1 = null;
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            transfer1 = transferDao.recordTransfer(transfer, username);
            transferDao.updateTransferStatusApproved(transfer);
            accountDao.transferMoneyFrom(transfer);
            accountDao.transferMoneyTo(transfer);

        }catch (ResponseStatusException e){
            transferDao.updateTransferStatusRejected(transfer);
        }
        return transfer1;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(Principal principal){
        return transferDao.getTransferByUserId(userDao.findIdByUsername(principal.getName()));
    }

}
