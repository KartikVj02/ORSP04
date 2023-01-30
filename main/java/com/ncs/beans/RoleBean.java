package com.ncs.beans;

import java.sql.Timestamp;


/**
 * @author Kartik Vijayvargiya
 *
 */
public class RoleBean extends BaseBean {

	public static final int ADMIN = 2;
	public static final int FACULTY = 3;
	public static final int STUDENT = 4;
	public static final int COLLEGE_SCHOOL = 5;
	public static final int KIOSK = 6;

	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {
		return name;
	}


}
