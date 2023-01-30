package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ncs.beans.CollegeBean;
import com.ncs.beans.StudentBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;


/**
 * @author Kartik Vijayvargiya
 *
 */
public class StudentModel {

	private static Logger log = Logger.getLogger(StudentModel.class);

	public int nextPK() throws DataBaseException {
		log.debug("model nextpk started");
		Connection conn = null;
		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				pk = rSet.getInt(1);
			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			throw new DataBaseException("Exception : Exception in getting pk" + e);
		}
		log.debug("model nextpk end");
		return pk + 1;

	}

	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("model add started");
		Connection conn = null;
		int pk = 0;
		StudentBean duplicateBean = findByEmail(bean.getEmail());

		CollegeModel model = new CollegeModel();
		CollegeBean collegeBean = model.findByPK(bean.getCollegeId());

		if (duplicateBean != null) {
			throw new DuplicateRecordException("Student email already exists");
		}

		bean.setCollegeName(collegeBean.getName());
		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			PreparedStatement pstmt = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCollegeId());
			pstmt.setString(3, bean.getCollegeName());
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setString(8, bean.getEmail());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDateTime());
			pstmt.setTimestamp(12, bean.getModifiedDateTime());

			pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}

	public StudentBean findByPK(long pk) {
		log.debug("model findByPK started");
		Connection conn = null;
		StudentBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_student where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new StudentBean();

				bean.setId(rSet.getLong(1));
				bean.setCollegeId(rSet.getLong(2));
				bean.setCollegeName(rSet.getString(3));
				bean.setFirstName(rSet.getString(4));
				bean.setLastName(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setEmail(rSet.getString(8));
				bean.setCreatedBy(rSet.getString(9));
				bean.setModifiedBy(rSet.getString(10));
				bean.setCreatedDateTime(rSet.getTimestamp(11));
				bean.setModifiedDateTime(rSet.getTimestamp(12));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.error("model findByPK end");
		return bean;
	}

	public StudentBean findByEmail(String email) {
		log.error("model findByEmail started");
		Connection conn = null;
		StudentBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_student where email = ?");
			pstmt.setString(1, email);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new StudentBean();

				bean.setId(rSet.getLong(1));
				bean.setCollegeId(rSet.getLong(2));
				bean.setCollegeName(rSet.getString(3));
				bean.setFirstName(rSet.getString(4));
				bean.setLastName(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setEmail(rSet.getString(7));
				bean.setMobileNo(rSet.getString(8));
				bean.setCreatedBy(rSet.getString(9));
				bean.setModifiedBy(rSet.getString(10));
				bean.setCreatedDateTime(rSet.getTimestamp(11));
				bean.setModifiedDateTime(rSet.getTimestamp(12));
			}

			rSet.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.error("model findByEmail end");
		return bean;
	}

	public void delete(StudentBean bean) {
		log.error("model delete started");
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.error("model delete end");

	}

	public void update(StudentBean bean) throws DuplicateRecordException {
		log.error("model update started");
		System.out.println(bean.getCollegeName() + "student model update method");
		Connection conn = null;

		StudentBean beanExist = findByEmail(bean.getEmail());

		// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Email Id is already exist");
		}

		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_student set college_id= ?, college_name=?, first_name=?, last_name=?, date_of_birth=?, mobile_no=? , email=?, created_by=?, modified_by=?, created_datetime=? ,modified_datetime=? where id = ? ");

			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setString(2, bean.getCollegeName());
			pstmt.setString(3, bean.getFirstName());
			pstmt.setString(4, bean.getLastName());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDateTime());
			pstmt.setTimestamp(11, bean.getModifiedDateTime());
			pstmt.setLong(12, bean.getId());

			pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.error("model update end");
	}

	public ArrayList<StudentBean> search(StudentBean bean) {
		return search(bean, 0, 0);
	}

	public ArrayList<StudentBean> search(StudentBean bean, int pageNo, int pageSize) {
		log.error("model search started");
		StringBuffer sqlBuffer = new StringBuffer("select * from st_student where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sqlBuffer.append(" AND id = " + bean.getId());
			}
			if (bean.getCollegeId() > 0) {
				sqlBuffer.append(" AND College_Id = " + bean.getCollegeId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sqlBuffer.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}

			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sqlBuffer.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sqlBuffer.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sqlBuffer.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sqlBuffer.append(" AND EMAIL like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sqlBuffer.append(" AND COLLEGE_NAME = " + bean.getCollegeName());
			}

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sqlBuffer.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<StudentBean> list = new ArrayList<StudentBean>();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sqlBuffer.toString());

			ResultSet rSet = pstmt.executeQuery();

			CollegeModel model = new CollegeModel();
			CollegeBean updateBean = model.findByPK(bean.getCollegeId());
			bean.setCollegeName(updateBean.getName());

			while (rSet.next()) {
				bean = new StudentBean();

				bean.setId(rSet.getLong(1));
				bean.setCollegeId(rSet.getLong(2));
				bean.setCollegeName(rSet.getString(3));
				bean.setFirstName(rSet.getString(4));
				bean.setLastName(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setEmail(rSet.getString(8));
				bean.setCreatedBy(rSet.getString(9));
				bean.setModifiedBy(rSet.getString(10));
				bean.setCreatedDateTime(rSet.getTimestamp(11));
				bean.setModifiedDateTime(rSet.getTimestamp(12));
				list.add(bean);

			}
			rSet.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.error("model search end");
		return list;
	}

	public ArrayList<StudentBean> list() throws ApplicationException {
		return list(0, 0);
	}

	public ArrayList<StudentBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDateTime(rs.getTimestamp(11));
				bean.setModifiedDateTime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}
}
