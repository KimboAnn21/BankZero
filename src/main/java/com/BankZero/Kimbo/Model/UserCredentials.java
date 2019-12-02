package com.BankZero.Kimbo.Model;

public class UserCredentials extends User{

		private String firstName;
		private String lastName;
		private String contact;
		private String email;
		
		
		public UserCredentials() {
			// Blank constructor
	}
	//super gets acct#, userName, password & pin	
		public UserCredentials(Long acctId, String userName, String passWord, int pin, String firstName, String lastName, String contact, String email) {
			super(acctId, userName, passWord, pin);
			this.firstName = firstName;
			this.lastName = lastName;
			this.contact = contact;
			this.email = email;
			
		}

		public UserCredentials(String firstName, String lastName, String contact, String email) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.contact = contact;
			this.email = email;
			
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "UserDetails [firstName=" + firstName + ", lastName=" + lastName + ", contact=" + contact + ", email="
					+ email + "]";
		}
		
		public String toStringDetails() {
			return "Name = " + firstName + " " + lastName + "\nContact number = " + contact + "\nEmail Address = "
					+ email ;
		}

//		public String getSql() {
//			// TODO Auto-generated method stub
//			return null;
//		}
	}

