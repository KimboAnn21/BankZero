package com.BankZero.Kimbo.DAO;

import java.util.List;


import com.BankZero.Kimbo.BankingException;
import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.Transaction;


public interface DAOAccount {

	public List<Account> displayAccounts(long acctId) throws BankingException;
	public Account registerAccount(Account account) throws BankingException;
	public Account findAccountById(long id) throws BankingException;
	public Account makeTransaction(long acctId, long ownerId, double trans) throws BankingException;
	public Transaction addTransaction(Transaction t) throws BankingException;
	public List<Transaction> showHistory(long acctId) throws BankingException;
}