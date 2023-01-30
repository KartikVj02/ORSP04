package com.ncs.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ncs.beans.RoleBean;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.JDBCDataSource;


/**
 * @author Kartik Vijayvargiya
 *
 */
public class RoleModel {

	public static Logger log = Logger.getLogger(RoleModel.class);

	public int nextPK() throws DataBaseException {
		log.debug("model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
			ResultSet rSet = pstmt.executeQuery();
			while (rSet.next()) {
				pk = rSet.getInt(1);
				System.out.println(pk);
			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model nextpk end");
		return pk + 1;
	}

	public long add(RoleBean bean) throws Exception {
		log.debug("Rolemodel add  method Started");
		Connection conn = null;
		int pk = 0;

		RoleBean duplicataRole = findByName(bean.getName());
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());

			int n = pstmt.executeUpdate();
			if (n > 0) {
				return pk;
			} else {
				return 0;
			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return 0;

	}

	public RoleBean findByPK(long pk) throws Exception {
		log.debug("model findByPk Started");
		Connection conn = null;
		RoleBean bean = new RoleBean();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pStatement = conn.prepareStatement("select * from st_role where id = ?");
			pStatement.setLong(1, pk);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
		} catch (SQLException e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByPk end");
		return bean;

	}

	public int delete(RoleBean bean) {
		log.debug("model delete Started");
		Connection conn = null;
		int n = 0;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ? ");
			pstmt.setLong(1, bean.getId());

			n = pstmt.executeUpdate();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
		if (n > 0) {
			return n;
		} else {
			return 0;
		}

	}

	public RoleBean findByName(String name) throws Exception {
		log.debug("model findByName Started");
		Connection conn = null;
		RoleBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_role where name = ?");
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new RoleBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByName end");
		return bean;
	}

	public void update(RoleBean bean) throws Exception {
		log.debug("model update Started");
		Connection conn = null;

		long pk = bean.getId();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_role set name= ?, description = ?, created_By=?, modified_By=?, created_DateTime=?, modified_DateTime=? where id = '"
							+ pk + "' ");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDateTime());
			pstmt.setTimestamp(6, bean.getModifiedDateTime());

			pstmt.executeUpdate();

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");
	}

	public List search(RoleBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	public List search(RoleBean bean, int pageNo, int pageSize) throws Exception {
		log.debug("model search Started");
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_role where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}

		}
		ArrayList<RoleBean> list = new ArrayList<RoleBean>();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return list;

	}

	public ArrayList<RoleBean> list() {
		log.debug("model list started");
		Connection conn = null;

		RoleBean bean = null;
		ArrayList<RoleBean> arrayList = new ArrayList<RoleBean>();

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_role");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new RoleBean();

				bean.setId(rSet.getLong(1));
				bean.setName(rSet.getString(2));
				bean.setDescription(rSet.getString(3));
				bean.setCreatedBy(rSet.getString(4));
				bean.setModifiedBy(rSet.getString(5));
				bean.setCreatedDateTime(rSet.getTimestamp(6));
				bean.setModifiedDateTime(rSet.getTimestamp(7));

				arrayList.add(bean);
			}

		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		}
		log.debug("model list end");
		return arrayList;
	}

}
