package com.BankZero.Kimbo.DAO;

import com.BankZero.Kimbo.BankingException;

import com.BankZero.Kimbo.Model.User;
import com.BankZero.Kimbo.Model.UserCredentials;

public interface DAOUser {

	public UserCredentials registerDetails(UserCredentials uDetails) throws BankingException;
	public User authUser(User user) throws BankingException;
	public User authAdminUser(User user) throws BankingException;
	public UserCredentials displayDetails(long userID) throws BankingException;
	public boolean checkUnique(UserCredentials udetails) throws BankingException;
	public boolean resetPassword(UserCredentials uDetails) throws BankingException;
	
}
