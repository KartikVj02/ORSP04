package com.ncs.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ncs.Model.UserModel;
import com.ncs.beans.RoleBean;
import com.ncs.beans.UserBean;
import com.ncs.exceptions.RecordNotFoundException;

public class UserModelTest {

	public static UserModel model = new UserModel();

	public static void main(String[] args) throws Exception {

		 testAdd();
		// testFindByLogin();
		// testFindByPK();
		// testDelete();
		 //testUpdate();
		// testSearch();
		// testList();
		// testAuthenticate();
		// testRegisterUser();
		// testGetRoles();
		// testForgetPassword();
		 //testResetPassword();
		//testChangePassword();
	}

	public static void testAdd() throws ParseException {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy");
		try {
			bean.setFirstName("varun");
			bean.setLastName("verma");
			bean.setLogin("varunV@gmail38.com");
			bean.setPassword("h@123");
			bean.setConfirmPassword("h@123");	
			bean.setDob(sdf.parse("07-06-1997"));
			bean.setRoleId(6L);
			bean.setUnSuccessfullLogin(2);
			bean.setGender("male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setCreatedBy("hardik");
			bean.setMobileNo("8978799778");
			bean.setModifiedBy("hardik");
			bean.setLastLoginIP("437:455:555");
			bean.setRegisteredIP("23:34:34:89");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);

			if (pk == 0) {
				System.out.println("cant add");
			} else {
				System.out.println("added successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByLogin() {

		UserBean bean = new UserBean();

		bean = model.findByLogin("h@g.com");

		if (bean == null) {

			System.out.println("Test find by Login is failed");
		}
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getGender());
	}

	public static void testFindByPK() throws Exception {

		UserBean bean = new UserBean();
		long pk = 2L;

		try {
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("find by pk is failed");
			}

			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() throws Exception {

		long pk = 2L;

		UserBean bean = new UserBean();
		bean.setId(pk);

		model.delete(bean);

		try {
			UserBean deleteBean = new UserBean();
			deleteBean = model.findByPk(pk);
			if (deleteBean == null) {
				System.out.println("test delete successfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() throws Exception {

		UserBean bean = model.findByPk(3L);

		bean.setFirstName("kaalu");
		bean.setLastName("mishra");
		bean.setLogin("k@g.com");
		bean.setPassword("k@123");

		try {
			model.update(bean);

			UserBean updateBean = model.findByPk(bean.getId());

			if (updateBean.getFirstName().equalsIgnoreCase(bean.getFirstName())
					&& updateBean.getLogin().equalsIgnoreCase(bean.getLogin())) {
				System.out.println("test update successfull");
			} else {
				System.out.println("test update failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() throws Exception {

		UserBean bean = new UserBean();
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		bean.setFirstName("hardik");
		try {
			list = model.search(bean);

			if (list.size() < 0) {
				System.out.println("Test search failed");
			}

			Iterator<UserBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (UserBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testList() throws Exception {
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		UserBean bean = new UserBean();
		try {
			list = model.list();

			Iterator<UserBean> i = list.iterator();

			while (i.hasNext()) {
				bean = (UserBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testAuthenticate() throws Exception {

		UserBean bean = new UserBean();
		bean.setLogin("h@g.com");
		bean.setPassword("h@123");
		try {
			UserBean authBean = model.authenticate(bean.getLogin(), bean.getPassword());

			if (authBean != null) {
				System.out.println("successfull login");
			} else {
				System.out.println("login id and password is not exist");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testRegisterUser() throws Exception {

		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		bean.setFirstName("kkkkkkkkkk");
		bean.setLastName("vijayvargiya");
		bean.setLogin("kkkk@g.com");
		bean.setPassword("k@123");
		bean.setConfirmPassword("4444");
		bean.setDob(sdf.parse("07-06-1997"));
		bean.setMobileNo("9898989898");
		bean.setRoleId(2L);
		bean.setUnSuccessfullLogin(4);
		bean.setGender("male");
		bean.setLastLogin(new Timestamp(new Date().getTime()));
		bean.setLock("Yes");
		bean.setCreatedBy("hardik");
		bean.setMobileNo("8978799778");
		bean.setModifiedBy("hardik");
		bean.setLastLoginIP("437:455:555");
		bean.setRegisteredIP("23:34:34:89");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		long pk = model.registerUser(bean);
		try {
			UserBean updateBean = model.findByPk(pk);
			if (updateBean == null) {
				System.out.println("test register user failed ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testGetRoles() {

		try {
			UserBean bean = new UserBean();
			ArrayList list = new ArrayList();
			bean.setRoleId(2L);
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("Test Get Roles fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfullLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testForgetPassword() throws RecordNotFoundException {

		UserBean bean = new UserBean();
		try {
			bean.setLogin("vijayhardik99@gmail.com");
			boolean result = model.forgetPassword(bean.getLogin());
			if (result == true) {
				System.out.println("forgot password is successfull");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testResetPassword() {

		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("vijayhardik99@gmail.com");

			boolean result = model.resetPassword(bean);

			if (result == true) {
				System.out.println("reset password successfull");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testChangePassword() throws Exception {
		try {
			UserBean bean = model.findByLogin("vijayhardik99@gmail.com");
			String oldPassword = bean.getPassword();
			// bean.setId(2L);
			bean.setPassword("hardik@123");

			String newPassword = bean.getPassword();

			boolean result = model.changePassword(bean.getId(), oldPassword, newPassword);
			if (result == true) {
				System.out.println("change password successfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
