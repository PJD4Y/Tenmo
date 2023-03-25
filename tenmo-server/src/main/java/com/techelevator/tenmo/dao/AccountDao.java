package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    BigDecimal getBalance(User user);

    void transferMoneyTo(Transfer transfer);

    void transferMoneyFrom(Transfer transfer);
}
