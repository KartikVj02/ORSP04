package com.ncs.beans;

import java.sql.Timestamp;
import java.util.Date;

/*import java.sql.Timestamp;
import java.util.Date;*/


/**
 * @author Kartik Vijayvargiya
 *
 */
public class UserBean extends BaseBean {

	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";

	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private String confirmPassword;
	private Date dob;
	private String mobileNo;
	private long roleId;
	private int unSuccessfullLogin;
	private String gender;
	private Timestamp lastLogin;
	private String lock;
	private String registeredIP;
	private String lastLoginIP;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getUnSuccessfullLogin() {
		return unSuccessfullLogin;
	}

	public void setUnSuccessfullLogin(int unSuccessfullLogin) {
		this.unSuccessfullLogin = unSuccessfullLogin;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getRegisteredIP() {
		return registeredIP;
	}

	public void setRegisteredIP(String registeredIP) {
		this.registeredIP = registeredIP;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	public String getValue() {
		return login;
	}

}
