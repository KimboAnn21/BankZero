package com.BankZero.Kimbo.Driver;

import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.AccountOwner;

public class Driver {
	
	public static void main(String[] args) {
		
		 Account account = new Account(0, null, null, 0, 0);
		 account.getAcctId();
		
//I think the code is supposed to call Service, not AccountOwner		
//		Service service = new Service();
		
		System.out.println(AccountOwner.getAllAccountOwners());
	}
}

