package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.management.modelmbean.ModelMBean;

import org.apache.log4j.Logger;

import com.ncs.beans.CollegeBean;
import com.ncs.beans.CourseBean;
import com.ncs.beans.FacultyBean;
import com.ncs.beans.StudentBean;
import com.ncs.beans.SubjectBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;

public class FacultyModel {

	public static Logger log = Logger.getLogger(FacultyModel.class);

	public int nextPK() throws DataBaseException {

		log.debug("faculty model nextPk method started");
		int pk = 0;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				pk = rSet.getInt(1);
			}

		} catch (Exception e) {
			log.error("Exception in Database :" + e);
			throw new DataBaseException("Exception in getting pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("faculty model nextpk end");
		return pk + 1;
	}

	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getSubjectName());

		FacultyBean facultyBean = findByEmail(bean.getEmailId());

		if (facultyBean != null) {
			throw new DuplicateRecordException("Email id already exits");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pk = nextPK();

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getEmailId());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setLong(9, bean.getCourseId());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setDate(11, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(12, bean.getSubjectId());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDateTime());
			pstmt.setTimestamp(17, bean.getModifiedDateTime());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Faculty Model Delete method End");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model delete method End");
	}

	public FacultyBean findByPk(long pk) throws ApplicationException {
		log.debug("Faculty Model findByPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		Connection conn = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in findByPK in faculty model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model FindByPK method end");
		return bean;
	}

	public FacultyBean findByEmail(String emailId) throws ApplicationException {

		Connection conn = null;
		FacultyBean bean = null;

		System.out.println("faculty add find by name");
		log.debug("Faculty Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE emailId=?");

		System.out.println(" faculty  find by name 1");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, emailId);
			System.out.println("resultset" + emailId);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(" faculty  find by name 1 while");
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				System.out.println(" faculty  find by name 3");
			}
			rs.close();
		} catch (Exception e) {
			log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in faculty model in findbyName method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println(" faculty  find by name 4");
		log.debug("Faculty Model findbyName method End");
		return bean;
	}

	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		
		CollegeModel collegeModel = new CollegeModel();	
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		
		CourseModel courseModel = new CourseModel();	
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		
		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getSubjectName());

		FacultyBean beanExist = findByEmail(bean.getEmailId());
		// Check if updated EmailId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("EmailId is already exist");
		}		


		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET COLLEGEID=?,SUBJECTID=?,COURSEID=?, FIRSTNAME=?, LASTNAME=?, GENDER=?, DOB=?,  EMAILID=?, MOBILENO=? , COURSENAME=?,  COLLEGENAME=?, SUBJECTNAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=? WHERE ID=? ");

			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setLong(2, bean.getSubjectId());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setString(6, bean.getGender());
			pstmt.setDate(7, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(8, bean.getEmailId());
			pstmt.setString(9, bean.getMobileNo());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setString(11, bean.getCollegeName());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDateTime());
			pstmt.setTimestamp(16, bean.getModifiedDateTime());
			pstmt.setLong(17, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DATABASE EXCEPTION ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in rollback faculty model .." + ex.getMessage());
			}
			throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model update method End");
	}

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model search  method Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND collegeId = " + bean.getCollegeId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
			}

			if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
				sql.append(" AND EmailId like '" + bean.getEmailId() + "%'");
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
			}

			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MobileNo like '" + bean.getMobileNo() + "%'");
			}

			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND collegeName like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND courseId = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND courseName like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND SubjectId = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND subjectName like '" + bean.getSubjectName() + "%'");
			}
		}

		// if page no is greater then zero then apply pagination
		System.out.println("model page ........" + pageNo + " " + pageSize);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("final sql  " + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));

				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				System.out.println("out whiile");
				list.add(bean);
				System.out.println("list size ----------->" + list.size());
			}
			rs.close();

		} catch (Exception e) {
			log.error("database Exception .. ", e);
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in Search method of
			// Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model search  method End");
		// System.out.println("retuen >>>>>>>>>>>>>>>"+list.size());
		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model List method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		Connection conn = null;
		ArrayList list = new ArrayList();

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception ......", e);
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model List method End");
		return list;
	}

}
