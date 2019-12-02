package com.BankZero.Kimbo.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.Connection.PostgreSQL.PostgreSQLConnection;
import com.BankZero.Kimbo.DAO.DAOAccount;
import com.BankZero.Kimbo.BankingException;
import com.BankZero.Kimbo.Model.Account;
import com.BankZero.Kimbo.Model.Transaction;

public class DAOAccountImpl implements DAOAccount {
	
		public Account registerAccount(Account account) throws BankingException {
			System.out.println("DAO: " + account);
			try (Connection connection = PostgreSQLConnection.getConnection()){
				String sql = "{call create_acct(?,?,?,?,?,?)}";
				CallableStatement callableStatement = connection.prepareCall(sql);
				callableStatement.setDouble(2, account.getBalance());;
				callableStatement.setDate(3, new Date(account.getOpenDate().getTime()));
				callableStatement.setString(4, account.getAcct_type());
				callableStatement.setDouble(5, account.getInterest());
				callableStatement.setLong(6, account.getOwnerId());
		//?????		callableStatement.registerOutParameter(1, java.sql.Types.NUMERIC);
				callableStatement.execute();
					account.setAcctId(callableStatement.getLong(1));
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e);
			}
			return account;
		}

		@Override
		public List<Account> displayAccounts(long acctId) throws BankingException {
			List<Account> accountList = new ArrayList<>();
			try(Connection connection = PostgreSQLConnection.getConnection()){
				String sql = "SELECT acct_id, balance, begin_date, acct_type, interest_rate FROM acct_table WHERE owner_id = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, acctId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Account acct = new Account();
					acct.setAcctId(resultSet.getLong("acct_id"));
					acct.setBalance(resultSet.getDouble("balance"));
					acct.setOpenDate(resultSet.getDate("begin_date"));
					acct.setAcct_type(resultSet.getString("acct_type"));
					acct.setInterest(resultSet.getDouble("interest_rate"));
					accountList.add(acct);
				}
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e);
				System.out.println("display accounts method");
			}
			return accountList;
		}

		@Override
		public Account makeTransaction(long acctId, long ownerId, double trans) throws BankingException {
			Account account = new Account();
			account.setAcctId(acctId);
			account.setOwnerId(ownerId);
			try (Connection connection = PostgreSQLConnection.getConnection()) {
				String sql = "UPDATE acct_table SET balance = ? WHERE acct_id = ? AND owner_id = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDouble(1, trans);
				preparedStatement.setLong(2, acctId);
				preparedStatement.setLong(3, ownerId);

				int resultSet = preparedStatement.executeUpdate();
				if (resultSet == 1) {
					account.setBalance(trans);
				}
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e);
				throw new BankingException("Internal error occurred... Please try again later");
			}
			return account;
		}
		

		@Override
		public Account findAccountById(long id) throws BankingException {
			Account acct = new Account();
			try(Connection connection = PostgreSQLConnection.getConnection()) {
				String sql = "SELECT * FROM acct_table WHERE acct_id = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					acct.setBalance(resultSet.getDouble("balance"));
					acct.setAcctId(id);
					acct.setOwnerId(resultSet.getLong("owner_id"));
					System.out.println(acct);
				} else {
					System.out.println("Something went wrong, account not found");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return acct;
		}

		@Override
		public List<Transaction> showHistory(long acctId) throws BankingException {
			List<Transaction> transactions = new ArrayList<>();
			try(Connection connection = PostgreSQLConnection.getConnection()){
				String sql = "SELECT trans_id, trans_date, trans_amount, new_balance FROM transactions WHERE acct_id = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, acctId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Transaction transaction = new Transaction(null, acctId, acctId, acctId);
					transaction.setTransId(resultSet.getLong("trans_id"));
					transaction.setTransDate(resultSet.getDate("trans_date"));
					transaction.setBalance(resultSet.getDouble("new_balance"));
					transaction.setAmount(resultSet.getDouble("trans_amount"));
					transactions.add(transaction);
				}
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e);
			}
			return transactions;
		}

		@Override
		public Transaction addTransaction(Transaction t) throws BankingException {
			try (Connection connection = PostgreSQLConnection.getConnection()){
				String sql = "{call gen_transaction ?,?,?,?,?}";
				CallableStatement callableStatement = connection.prepareCall(sql);
				callableStatement.setLong(1, t.getAcctId());
				callableStatement.setDate(2, t.getTransDate());
				callableStatement.setDouble(3, t.getAmount());
				callableStatement.setDouble(4, t.getBalance());
				callableStatement.registerOutParameter(5, java.sql.Types.NUMERIC);
				
				callableStatement.execute();
				t.setTransId(callableStatement.getLong(5));
			} catch(ClassNotFoundException | SQLException e) {
				
			}
			return t;
		}

	}

