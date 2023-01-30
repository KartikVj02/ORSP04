package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ncs.beans.CourseBean;
import com.ncs.beans.SubjectBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;

/**
 * @author Kartik Vijayvargiya
 *
 */
public class SubjectModel {

	public static Logger log = Logger.getLogger(SubjectModel.class);

	public int nextPK() throws DataBaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM st_subject");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;

		// get Course Name
		CourseModel cModel = new CourseModel();
		CourseBean courseBean = cModel.findByPk(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectBean duplicateBean = findByName(bean.getSubjectName());
		if (duplicateBean != null) {
			throw new DuplicateRecordException("Subject Name already exist");
		}

		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getSubjectName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setLong(4, bean.getCourseId());
			pstmt.setString(5, bean.getCourseName());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDateTime());
			pstmt.setTimestamp(9, bean.getModifiedDateTime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * catch (Exception e) { log.error("Database Exception..", e); try {
		 * conn.rollback(); } catch (Exception ex) { throw new
		 * ApplicationException("Exception : add rollback exception " +
		 * ex.getMessage()); } throw new
		 * ApplicationException("Exception : Exception in add Subject"); }
		 */
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Subject Model Delete method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("database Exception ...", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Delete Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model Delete method End");
	}

	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET SubjectName=?,CourseID=?,CourseNAME=?,Description=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getSubjectName());
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDateTime());
			pstmt.setTimestamp(8, bean.getModifiedDateTime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Subject ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public SubjectBean findByName(String name) throws ApplicationException {
		log.debug("Subject Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE subjectName=?");
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception....", e);
			e.printStackTrace();
			// throw new ApplicationException("Exception in findByName Method of Subject
			// Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Subject Model findByName method End");
		return bean;
	}

	public SubjectBean findByPk(long pk) throws ApplicationException {
		log.debug("Subject Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Subject Model findByPk method End");
		return bean;
	}

	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Subject Model search method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND CourseID = " + bean.getCourseId());
			}

			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SubjectName like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND CourseName like '" + bean.getCourseName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND description like '" + bean.getDescription() + " % ");
			}

		}

		// Page Size is greater then Zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("sql is" + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in search Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model search method End");
		return list;
	}

	/**
	 * Get List of Subject.
	 *
	 * @return list : List of Subject
	 * @throws ApplicationException the application exception
	 */
	public ArrayList<SubjectBean> list() throws ApplicationException {
		return list(0, 0);
	}

	public ArrayList<SubjectBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Subject Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model list method End");
		return list;
	}

}
