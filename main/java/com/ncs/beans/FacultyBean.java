package com.ncs.beans;

import java.util.Date;


/**
 * @author Kartik Vijayvargiya
 *
 */
public class FacultyBean extends BaseBean {

	private String firstName;
	private String lastName;
	private String gender;
	private String emailId;
	private String mobileNo;
	private long collegeId;
	private String collegeName;
	private long courseId;
	private String courseName;
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	private Date dob;
	private long subjectId;
	private String subjectName;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		 return id + "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	


}
