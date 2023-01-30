package com.ncs.Model;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ncs.beans.UserBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DataBaseException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.exceptions.RecordNotFoundException;
import com.ncs.utils.EmailBuilder;
import com.ncs.utils.EmailMessage;
import com.ncs.utils.EmailUtility;
import com.ncs.utils.JDBCDataSource;

/**
 * @author Kartik Vijayvargiya
 *
 */
public class UserModel {

	public static Logger log = Logger.getLogger(UserModel.class);

	public int nextPK() throws Exception {
		log.debug("model nextPk started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_user");

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				pk = rSet.getInt(1);
			}
			rSet.close();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			throw new DataBaseException("Exception :Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("model nextPk end");
		return pk + 1;

	}

	public long add(UserBean bean) throws Exception {
		log.debug("model add started");
		Connection conn = null;
		int pk = 0;

		UserBean existsBean = findByLogin(bean.getLogin());
		if (existsBean != null) {
			throw new DuplicateRecordException("login Id already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pk = nextPK();
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			// pstmt.setDate(6, (java.sql.Date) (bean.getDob()));
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setInt(9, bean.getUnSuccessfullLogin());
			pstmt.setString(10, bean.getGender());
			pstmt.setTimestamp(11, bean.getLastLogin());
			pstmt.setString(12, bean.getLock());
			pstmt.setString(13, bean.getRegisteredIP());
			pstmt.setString(14, bean.getLastLoginIP());

			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDateTime());
			pstmt.setTimestamp(18, bean.getModifiedDateTime());

			pstmt.executeUpdate();
			conn.commit(); // End transaction

			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				// throw new ApplicationException("Exception : add rollback exception " +
				// ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}

	public UserBean findByLogin(String login) {
		log.debug("model findByLogin started");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where Login=?");
			pstmt.setString(1, login);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new UserBean();

				bean.setId(rSet.getLong(1));
				bean.setFirstName(rSet.getString(2));
				bean.setLastName(rSet.getString(3));
				bean.setLogin(rSet.getString(4));
				bean.setPassword(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setRoleId(rSet.getLong(8));
				bean.setUnSuccessfullLogin(rSet.getInt(9));
				bean.setGender(rSet.getString(10));
				bean.setLastLogin(rSet.getTimestamp(11));
				bean.setLock(rSet.getString(12));
				bean.setRegisteredIP(rSet.getString(13));
				bean.setLastLoginIP(rSet.getString(14));
				bean.setCreatedBy(rSet.getString(15));
				bean.setModifiedBy(rSet.getString(16));
				bean.setCreatedDateTime(rSet.getTimestamp(17));
				bean.setModifiedDateTime(rSet.getTimestamp(18));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbyLogin end");
		return bean;

	}

	public UserBean findByPk(long pk) throws Exception {
		log.debug("model findByPK started");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id = ?");
			pstmt.setLong(1, pk);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new UserBean();

				bean.setId(rSet.getLong(1));
				bean.setFirstName(rSet.getString(2));
				bean.setLastName(rSet.getString(3));
				bean.setLogin(rSet.getString(4));
				bean.setPassword(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setRoleId(rSet.getLong(8));
				bean.setUnSuccessfullLogin(rSet.getInt(9));
				bean.setGender(rSet.getString(10));
				bean.setLastLogin(rSet.getTimestamp(11));
				bean.setLock(rSet.getString(12));
				bean.setRegisteredIP(rSet.getString(13));
				bean.setLastLoginIP(rSet.getString(14));
				bean.setCreatedBy(rSet.getString(15));
				bean.setModifiedBy(rSet.getString(16));
				bean.setCreatedDateTime(rSet.getTimestamp(17));
				bean.setModifiedDateTime(rSet.getTimestamp(18));

			}
			pstmt.close();
			rSet.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByPk end");
		return bean;
	}

	public void delete(UserBean bean) {
		log.debug("model delete started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			conn.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
			}

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");

	}

	public void update(UserBean bean) throws Exception {
		log.debug("model update started");
		Connection conn = null;

		UserBean beanExist = findByLogin(bean.getLogin());
		// Check if updated LoginId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareCall(
					"update st_user set first_name=?, last_name=?, login=?, password=?, dob=?, mobile_no=? , role_id=? ,unsuccessful_login=? , gender=?, last_login=?, user_lock=?, registered_ip=?, last_login_ip=?, created_by=?, modified_by=?, modified_date=? where id = ?");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
//			pstmt.setDate(5, (java.sql.Date) bean.getDob());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));

			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setInt(8, bean.getUnSuccessfullLogin());
			pstmt.setString(9, bean.getGender());
			pstmt.setTimestamp(10, bean.getLastLogin());
			pstmt.setString(11, bean.getLock());
			pstmt.setString(12, bean.getRegisteredIP());
			pstmt.setString(13, bean.getLastLoginIP());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			// pstmt.setTimestamp(16, bean.getCreatedDateTime());
			pstmt.setTimestamp(16, bean.getModifiedDateTime());
			pstmt.setLong(17, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (ApplicationException e) {
			log.error("Database Exception.." + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");
	}

	public ArrayList<UserBean> search(UserBean bean, int pageNo, int pageSize) throws Exception {
		log.debug("model search started");

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getGender());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getUnSuccessfullLogin() > 0) {
				sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfullLogin());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}
			if (bean.getLastLogin() != null && bean.getLastLogin().getTime() > 0) {
				sql.append(" AND LAST_LOGIN = " + bean.getLastLogin());
			}
			if (bean.getRegisteredIP() != null && bean.getRegisteredIP().length() > 0) {
				sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIP() + "%'");
			}
			if (bean.getLastLoginIP() != null && bean.getLastLoginIP().length() > 0) {
				sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIP() + "%'");
			}

		}
		System.out.println(sql);

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfullLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/* ArrayList<UserBean> list = new ArrayList<UserBean>(); */

	public ArrayList<UserBean> list() throws Exception {
		log.debug("model list started");
		Connection conn = null;
		UserBean bean = null;
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user");
			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {
				bean = new UserBean();

				bean.setId(rSet.getLong(1));
				bean.setFirstName(rSet.getString(2));
				bean.setLastName(rSet.getString(3));
				bean.setLogin(rSet.getString(4));
				bean.setPassword(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setRoleId(rSet.getLong(8));
				bean.setUnSuccessfullLogin(rSet.getInt(9));
				bean.setGender(rSet.getString(10));
				bean.setLastLogin(rSet.getTimestamp(11));
				bean.setLock(rSet.getString(12));
				bean.setRegisteredIP(rSet.getString(13));
				bean.setLastLoginIP(rSet.getString(14));
				bean.setCreatedBy(rSet.getString(15));
				bean.setModifiedBy(rSet.getString(16));
				bean.setCreatedDateTime(rSet.getTimestamp(17));
				bean.setModifiedDateTime(rSet.getTimestamp(18));

				list.add(bean);

			}

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}

	public UserBean authenticate(String login, String password) throws Exception {
		log.debug("model authenticate started");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login= ? and password=?");

			pstmt.setString(1, login);
			pstmt.setString(2, password);

			ResultSet rSet = pstmt.executeQuery();

			while (rSet.next()) {

				bean = new UserBean();

				bean.setId(rSet.getLong(1));
				bean.setFirstName(rSet.getString(2));
				bean.setLastName(rSet.getString(3));
				bean.setLogin(rSet.getString(4));
				bean.setPassword(rSet.getString(5));
				bean.setDob(rSet.getDate(6));
				bean.setMobileNo(rSet.getString(7));
				bean.setRoleId(rSet.getLong(8));
				bean.setUnSuccessfullLogin(rSet.getInt(9));
				bean.setGender(rSet.getString(10));
				bean.setLastLogin(rSet.getTimestamp(11));
				bean.setLock(rSet.getString(12));
				bean.setRegisteredIP(rSet.getString(13));
				bean.setLastLoginIP(rSet.getString(14));
				bean.setCreatedBy(rSet.getString(15));
				bean.setModifiedBy(rSet.getString(16));
				bean.setCreatedDateTime(rSet.getTimestamp(17));
				bean.setModifiedDateTime(rSet.getTimestamp(18));

			}

			rSet.close();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception.." + e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model authenticate end");

		return bean;
	}

	public long registerUser(UserBean bean) {
		log.debug("model registerUser started");

		long pk = 0;
		try {
			pk = add(bean);
		} catch (Exception e) {
			log.error("database Exception.." + e);
			e.printStackTrace();
		}
		log.debug("model registered User end");
		return pk;
	}

	public ArrayList getRoles(UserBean bean) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_user where role_Id=?");
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfullLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model get roles End");
		return list;
	}

	public boolean forgetPassword(String login) throws RecordNotFoundException, ApplicationException {

		UserBean bean = findByLogin(login);
		boolean flag = false;
		if (bean == null) {
			throw new RecordNotFoundException("Email id does not exists.");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);

		flag = true;

		return flag;
	}

	public long getRoleId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean resetPassword(UserBean bean) throws Exception {

		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);

		UserBean userData = findByPk(bean.getId());
		userData.setPassword(newPassword);
		try {
			update(userData);

		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", bean.getLogin());
		map.put("password", newPassword);
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	public boolean changePassword(long id, String oldPassword, String newPassword) throws Exception {

		log.debug("model changePassword Started");
		System.out.println("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findByPk(id);

		System.out.println("beanExit size is :    " + beanExist.getPassword());
		System.out.println("newPassword :     " + newPassword);
		System.out.println("oldPassword :    " + oldPassword);

		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (Exception e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;
	}

	public ArrayList<UserBean> search(UserBean bean) throws Exception {
		return search(bean, 0, 0);
	}

}
