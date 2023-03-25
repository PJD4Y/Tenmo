package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.security.Principal;
import java.util.List;

public interface TransferDao {
    Transfer recordTransfer(Transfer transfer, String username);

    List<Transfer> getTransferByUserId (int id);

    List<Transfer> getTransfersByAccountId (int accountId);

    void updateTransferStatusApproved(Transfer transfer);

    void updateTransferStatusRejected (Transfer transfer);

    int getAccountIdByUsername(String username);

    int getAccountIdByUserId(int userId);

}
