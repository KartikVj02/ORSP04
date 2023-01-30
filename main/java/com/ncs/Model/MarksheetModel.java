package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ncs.beans.MarksheetBean;
import com.ncs.beans.StudentBean;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;


/**
 * @author Kartik Vijayvargiya
 *
 */

public class MarksheetModel {

	private static Logger log = Logger.getLogger(MarksheetModel.class);

	public int nextPK() {

		log.debug("next pk started");
		Connection conn = null;
		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {

				pk = rSet.getInt(1);

			}
			rSet.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("database exception " + e);
			e.printStackTrace();
		}
		log.debug("model next pk end");
		return pk + 1;

	}

	public long add(MarksheetBean bean) throws DuplicateRecordException {
		log.debug("model add started");
		Connection conn = null;
		int pk = 0;

		StudentModel model = new StudentModel();
		StudentBean studentbean = model.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		System.out.println("inside model >>>>" + bean.getName());

		MarksheetBean duplicateBean = findByRollNo(bean.getRollNo());

		if (duplicateBean != null) {
			throw new DuplicateRecordException("roll no already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDateTime());
			pstmt.setTimestamp(11, bean.getModifiedDateTime());

			pstmt.executeUpdate();
			conn.commit(); // End transaction
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

	public MarksheetBean findByPK(long pk) {
		log.debug("model findByPK started");
		Connection conn = null;
		MarksheetBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_marksheet where id = ?");
			pstmt.setLong(1, pk);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new MarksheetBean();

				bean.setId(rSet.getLong(1));
				bean.setRollNo(rSet.getString(2));
				bean.setStudentId(rSet.getLong(3));
				bean.setName(rSet.getString(4));
				bean.setPhysics(rSet.getInt(5));
				bean.setChemistry(rSet.getInt(6));
				bean.setMaths(rSet.getInt(7));
				bean.setCreatedBy(rSet.getString(8));
				bean.setModifiedBy(rSet.getString(9));
				bean.setCreatedDateTime(rSet.getTimestamp(10));
				bean.setModifiedDateTime(rSet.getTimestamp(11));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByPk end");
		return bean;
	}

	public MarksheetBean findByRollNo(String rollNo) {
		log.debug("model findByRollNo started");
		Connection conn = null;
		MarksheetBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_marksheet where roll_No = ?");
			pstmt.setString(1, rollNo);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new MarksheetBean();

				bean.setId(rSet.getLong(1));
				bean.setRollNo(rSet.getString(2));
				bean.setStudentId(rSet.getLong(3));
				bean.setName(rSet.getString(4));
				bean.setPhysics(rSet.getInt(5));
				bean.setChemistry(rSet.getInt(6));
				bean.setMaths(rSet.getInt(7));
				bean.setCreatedBy(rSet.getString(8));
				bean.setModifiedBy(rSet.getString(9));
				bean.setCreatedDateTime(rSet.getTimestamp(10));
				bean.setModifiedDateTime(rSet.getTimestamp(11));

			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByRollNo end");
		return bean;

	}

	public void delete(MarksheetBean bean) {
		log.debug("model delete started");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");

	}

	public void update(MarksheetBean bean) throws DuplicateRecordException {
		log.debug("model update started");

		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());
		// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}

		// get Student Name
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());

		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_marksheet set roll_No =?, student_id =?, name=?, physics=?, chemistry=?, maths=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where  id = ?");

			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDateTime());
			pstmt.setTimestamp(10, bean.getModifiedDateTime());
			pstmt.setLong(11, bean.getId());

			pstmt.executeUpdate();

			pstmt.close();
		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");
	}

	public ArrayList<MarksheetBean> search(MarksheetBean bean) {
		return search(bean, 0, 0);
	}

	public ArrayList<MarksheetBean> search(MarksheetBean bean, int pageNo, int pageSize) {
		log.debug("model search started");

		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != null && bean.getPhysics() > 0) {
				sql.append(" AND physics = " + bean.getPhysics());
			}
			if (bean.getChemistry() != null && bean.getChemistry() > 0) {
				sql.append(" AND chemistry = " + bean.getChemistry());
			}
			if (bean.getMaths() != null && bean.getMaths() > 0) {
				sql.append(" AND maths = '" + bean.getMaths());
			}

		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
			
		}

		 System.out.println("marksheetModel Search() sql.append: "+sql);
		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {

				bean = new MarksheetBean();

				bean.setId(rSet.getLong(1));
				bean.setRollNo(rSet.getString(2));
				bean.setStudentId(rSet.getLong(3));
				bean.setName(rSet.getString(4));
				bean.setPhysics(rSet.getInt(5));
				bean.setChemistry(rSet.getInt(6));
				bean.setMaths(rSet.getInt(7));
				bean.setCreatedBy(rSet.getString(8));
				bean.setModifiedBy(rSet.getString(9));
				bean.setCreatedDateTime(rSet.getTimestamp(10));
				bean.setModifiedDateTime(rSet.getTimestamp(11));

				list.add(bean);
			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return list;
	}

	public ArrayList<MarksheetBean> list() {
		log.debug("model list started");
		Connection conn = null;

		MarksheetBean bean = null;
		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_marksheet");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {

				bean = new MarksheetBean();

				bean.setId(rSet.getLong(1));
				bean.setRollNo(rSet.getString(2));
				bean.setStudentId(rSet.getLong(3));
				bean.setName(rSet.getString(4));
				bean.setPhysics(rSet.getInt(5));
				bean.setChemistry(rSet.getInt(6));
				bean.setMaths(rSet.getInt(7));
				bean.setCreatedBy(rSet.getString(8));
				bean.setModifiedBy(rSet.getString(9));
				bean.setCreatedDateTime(rSet.getTimestamp(10));
				bean.setModifiedDateTime(rSet.getTimestamp(11));

				list.add(bean);
			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}

	public ArrayList<MarksheetBean> getMeritList() {
		return getMeritList(0, 0);
	}

	public ArrayList<MarksheetBean> getMeritList(int pageNo, int pageSize) {
		log.debug("model merirList started");

		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		StringBuffer sql = new StringBuffer(
				"select id , roll_no, student_id, name, physics, chemistry, maths, (physics+chemistry+maths) as total from st_marksheet order by total desc");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;

		MarksheetBean bean = null;
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rSet = pstmt.executeQuery();

			System.out.println(sql);
			while (rSet.next()) {

				bean = new MarksheetBean();

				bean.setId(rSet.getLong(1));
				bean.setRollNo(rSet.getString(2));
				bean.setStudentId(rSet.getLong(3));
				bean.setName(rSet.getString(4));
				bean.setPhysics(rSet.getInt(5));
				bean.setChemistry(rSet.getInt(6));
				bean.setMaths(rSet.getInt(7));
				/*
				 * bean.setCreatedBy(rSet.getString(8)); bean.setModifiedBy(rSet.getString(9));
				 * bean.setCreatedDateTime(rSet.getTimestamp(10));
				 * bean.setModifiedDateTime(rSet.getTimestamp(11));
				 */
				list.add(bean);
			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model merirList end");
		return list;
	}

}
