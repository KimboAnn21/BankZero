package com.BankZero.Kimbo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.BankZero.Kimbo.DAO.DAOAccount;
import com.BankZero.Kimbo.DAO.DAOAccountImpl;
import com.BankZero.Kimbo.DAO.DAOUser;
import com.BankZero.Kimbo.BankingException;
import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.User;
import com.BankZero.Kimbo.Model.UserCredentials;
import com.BankZero.Kimbo.Service;

public class AccountImpl implements Service {
	
	private DAOAccount acctDAO;
	
	public String hashPass(String password) {
			StringBuffer message=new StringBuffer();
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] hash = md.digest(password.getBytes("UTF-8"));

				for (byte w : hash) {
					message.append(String.format("%02x", w));
				}
			}catch (Exception e) {
				System.out.println(e);
			}
		return message.toString();
	}

	public User login(User user) throws BankingException {
		try {
			User user1 = new User();
			System.out.println("service layer: "+user1);
			user1.setPassWord(hashPass(user1.getPassWord()));
			user1 = (((User) user1).authUser());
			if(user1.getAcctId()!=0) {
				return user1;
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	@Override
	public User adminLogin(User user) throws BankingException {
		try {
			User user2 = new User();
			System.out.println("service layer: "+user);
			user.setPassWord(hashPass(user.getPassWord()));
			user = ((DAOUser) user2).authAdminUser(user);
			if(user.getAcctId()!=0) {
				return user;
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}
	@Override
	public UserCredentials signUp(UserCredentials uDetails) {
		User user3 = new User();
		uDetails.setPassWord(hashPass(uDetails.getPassWord()));
		user3.registerDetails(uDetails);
		return null;
	}

	public Account createChecking(Account account) throws BankingException {
		System.out.println("createChecking obj: " + account);
		DAOAccountImpl dao = new DAOAccountImpl();
		account = dao.registerAccount(account);
		return account;
	}

	@Override
	public UserCredentials displayDetails(long acctId) throws BankingException {
		UserCredentials uDetails = new UserCredentials(acctId, null, null, 0, null, null, null, null);
		DAOUser dao = getUserDao();
		uDetails = dao.displayDetails(acctId);
		return uDetails;
	}

	private DAOUser getUserDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(long acctId) throws BankingException {
		Account acct = getDAOAccount().findAccountById(acctId);
		return acct;
	}
	
	public DAOAccount getDAOAccount() {
		if(acctDAO==null) {
			acctDAO=new DAOAccountImpl();
		}
		return acctDAO;
	}
	
//	public DAOUser getUserDao() {
//		if(uDAO==null) {
//			uDAO = (DAOUser) new DAOUser();
//		}
//		return uDAO;
//	}

	@Override
	public boolean checkEmail(UserCredentials uDetails) throws BankingException {
		User user = new User();
		boolean b = user.checkUnique(uDetails);
		if (b) {
			throw new BankingException("Email account is already registered");
		} 
		System.out.print(b);
		return b;
	}

	@Override
	public boolean resetPassword(UserCredentials uDetails) throws BankingException {
		
		User user1 = new User();
		uDetails.setPassWord(hashPass(uDetails.getPassWord()));
		if (user1.resetPassword(uDetails)) {
		return true;
	}
		return false;
}
		

	@Override
	public List<Account> displayAccounts(long acctId) throws BankingException {
		DAOAccount dao = new DAOAccountImpl();
		List<Account> accountList = new ArrayList<>();
		accountList = dao.displayAccounts(acctId);
		return accountList;
	}

	@Override
	public Account makeTransaction(long acctId, long ownerId, double trans) throws BankingException {
		Account acct = new Account();
		acct = getDAOAccount().makeTransaction(acctId, ownerId, trans);
		return acct;
	}
	
}
