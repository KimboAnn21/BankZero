package com.BankZero.Kimbo;

import java.util.List;

import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.User;

public interface AORepository {

		List<Account> getAllAccountOwners();
		 User getAccountById();
	
	}
