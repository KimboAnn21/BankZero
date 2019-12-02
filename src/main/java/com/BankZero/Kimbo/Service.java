package com.BankZero.Kimbo;

import java.util.List;


import com.BankZero.Kimbo.BankingException;
import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.User;
import com.BankZero.Kimbo.Model.UserCredentials;


public interface Service {

	public String hashPass(String password) throws BankingException;
	public UserCredentials signUp(UserCredentials uDetails) throws BankingException;
	public User login(User user) throws BankingException;
	public Account createChecking(Account account) throws BankingException;
	public Account getAccountById(long acctId) throws BankingException;
	public boolean checkEmail(UserCredentials uDetails) throws BankingException;
	public UserCredentials displayDetails(long acctId) throws BankingException;
	public boolean resetPassword(UserCredentials uDetails) throws BankingException;
	public List<Account> displayAccounts(long acctId) throws BankingException;
	public Account makeTransaction(long acctId, long ownerId, double trans) throws BankingException;
	public User adminLogin(User user) throws BankingException;
}
