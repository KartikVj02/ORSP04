package com.ncs.beans;

import java.io.Serializable;
import java.sql.Timestamp;




/**
 * @author Kartik Vijayvargiya
 *
 */
public abstract class BaseBean implements Serializable, DropDownListBean, Comparable<BaseBean> {

	
	private static final long serialVersionUID = 1L;
	/** Non Business primary key. */
	protected long id;
	/** Contains USER ID who created this database record. */
	protected String createdBy;
	/** Contains USER ID who modified this database record. */
	protected String modifiedBy;
	/** Contains Created Timestamp of database record. */
	protected Timestamp createdDateTime;
	/** Contains Modified Timestamp of database record. */
	protected Timestamp modifiedDateTime;

	
	/**
	 * accessor.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */

	public void setId(long l) {
		this.id = l;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */

	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * Gets the created datetime.
	 *
	 * @return the created datetime
	 */
	
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}
	/**
	 * Sets the created datetime.
	 *
	 * @param createdDatetime the new created datetime
	 */
	
	public void setCreatedDateTime(Timestamp timestamp) {
		this.createdDateTime = timestamp;
	}

	/**
	 * Gets the modified datetime.
	 *
	 * @return the modified datetime
	 */
	public Timestamp getModifiedDateTime() {
		return modifiedDateTime;
	}

	/**
	 * Sets the modified datetime.
	 *
	 * @param modifiedDatetime the new modified datetime
	 */
	
	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BaseBean next) {
		return getValue().compareTo(next.getValue());
	}

}
