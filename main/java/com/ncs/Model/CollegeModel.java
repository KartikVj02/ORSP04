package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.Size2DSyntax;

import org.apache.log4j.Logger;

import com.ncs.beans.CollegeBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;

/**
 * @author Kartik Vijayvargiya
 *
 */
public class CollegeModel {

	private static Logger log = Logger.getLogger(CollegeModel.class);

	public int nextPk() throws DataBaseException {
		log.debug("Model nextPK Started");

		System.out.println("9 in CollegeModel nextPk() method");

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				pk = rSet.getInt(1);
			}
			rSet.close();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;

	}

	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		System.out.println("8 in CollegeModel add() method");
		int pk = 0;

		CollegeBean dupliacteBean = findByName(bean.getName());

		if (dupliacteBean != null) {
			throw new DuplicateRecordException("College name alraedy exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");

			pk = nextPk();

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDateTime());
			pstmt.setTimestamp(10, bean.getModifiedDateTime());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("10 in collegeModel add() method end");
		log.debug("model add end");
		return pk;
	}

	public CollegeBean findByPK(long pk) {
		log.debug("Model findByPk Started");
		System.out.println("inside findByPk() of CollegeModel");
		Connection conn = null;
		CollegeBean bean = new CollegeBean();

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_college where id =?");
			pstmt.setLong(1, pk);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new CollegeBean();

				bean.setId(rSet.getLong(1));
				bean.setName(rSet.getString(2));
				bean.setAddress(rSet.getString(3));
				bean.setState(rSet.getString(4));
				bean.setCity(rSet.getString(5));
				bean.setPhoneNo(rSet.getString(6));
				bean.setCreatedBy(rSet.getString(7));
				bean.setModifiedBy(rSet.getString(8));
				bean.setCreatedDateTime(rSet.getTimestamp(9));
				bean.setModifiedDateTime(rSet.getTimestamp(10));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		}
		log.debug("Model findByPK End");
		System.out.println("end of findByPK() in collegeModel");
		return bean;
	}

	public CollegeBean findByName(String name) {
		log.debug("Model findByName Started");

		Connection conn = null;
		CollegeBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_college where name =?");

			pstmt.setString(1, name);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new CollegeBean();

				bean.setId(rSet.getLong(1));
				bean.setName(rSet.getString(2));
				bean.setAddress(rSet.getString(3));
				bean.setState(rSet.getString(4));
				bean.setCity(rSet.getString(5));
				bean.setPhoneNo(rSet.getString(6));
				bean.setCreatedBy(rSet.getString(7));
				bean.setModifiedBy(rSet.getString(8));
				bean.setCreatedDateTime(rSet.getTimestamp(9));
				bean.setModifiedDateTime(rSet.getTimestamp(10));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		}
		log.debug("Model findByName End");
		return bean;
	}

	public void delete(CollegeBean bean) throws ApplicationException {
		log.debug("Model delete Started");

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();

			} catch (Exception e2) {
				throw new ApplicationException("Exception : Delete rollback exception" + e2.getMessage());
			}
			throw new ApplicationException("Exception: Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete End");
	}

	public void update(CollegeBean bean) throws ApplicationException {
		log.debug("Model update Started");
		System.out.println("Starting update() method of collegeModel");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_college set name=?, address=?, state=?, city=?, phone_no=?,created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id =?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDateTime());
			pstmt.setTimestamp(9, bean.getModifiedDateTime());
			pstmt.setLong(10, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in getting update");
		}
		System.out.println("End update() method of collegeModel");
		log.debug("Model update End");
	}

	public ArrayList<CollegeBean> list() throws Exception {
		return list(0, 0);
	}

	public ArrayList<CollegeBean> list(int pageNo, int pageSize) throws Exception {
		log.debug("Model list Started");

		Connection conn = null;
		CollegeBean bean = null;
		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_college");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new CollegeBean();

				bean.setId(rSet.getLong(1));
				bean.setName(rSet.getString(2));
				bean.setAddress(rSet.getString(3));
				bean.setState(rSet.getString(4));
				bean.setCity(rSet.getString(5));
				bean.setPhoneNo(rSet.getString(6));
				bean.setCreatedBy(rSet.getString(7));
				bean.setModifiedBy(rSet.getString(8));
				bean.setCreatedDateTime(rSet.getTimestamp(9));
				bean.setModifiedDateTime(rSet.getTimestamp(10));

				list.add(bean);

			}

		} catch (ApplicationException e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");

		return list;
	}

	public List search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");

		Connection conn = null;
		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();

		try {

			StringBuffer sqlBuffer = new StringBuffer("select * from st_college where 1 = 1");

			if (bean != null) {
				if (bean.getId() > 0) {
					sqlBuffer.append(" AND id = " + bean.getId());
				}
				if (bean.getName() != null && bean.getName().length() > 0) {
					sqlBuffer.append(" AND NAME like '" + bean.getName() + "%'");
				}
				if (bean.getAddress() != null && bean.getAddress().length() > 0) {
					sqlBuffer.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
				}
				if (bean.getState() != null && bean.getState().length() > 0) {
					sqlBuffer.append(" AND STATE like '" + bean.getState() + "%'");
				}
				if (bean.getCity() != null && bean.getCity().length() > 0) {
					sqlBuffer.append(" AND CITY like '" + bean.getCity() + "%'");
				}
				if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
					sqlBuffer.append(" AND PHONE_NO = " + bean.getPhoneNo());
				}

			}

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				// Calculate start record index
				pageNo = (pageNo - 1) * pageSize;
				sqlBuffer.append(" Limit " + pageNo + ", " + pageSize);
			}

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sqlBuffer.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();

			System.out.println("list Size" + list.size() + bean);

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}

	public ArrayList<CollegeBean> search(CollegeBean bean) throws Exception {
		return (ArrayList<CollegeBean>) search(bean, 0, 0);
	}

}
