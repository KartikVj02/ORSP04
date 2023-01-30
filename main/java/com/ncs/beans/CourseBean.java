package com.ncs.beans;

/**
 * @author Kartik Vijayvargiya
 *
 */

public class CourseBean extends BaseBean {

	private String name;
	private String description;
	private String duration;

	
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
