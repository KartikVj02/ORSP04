package com.ncs.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ncs.Model.RoleModel;
import com.ncs.beans.RoleBean;

public class RoleModelTest {

	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {

		//testAdd();
		// testDelete();
		// testFindByPk();
		 testFindByName();
		// testUpdate();
		// testSearch();
		// testList();
	}

	public static void testAdd() throws Exception {

		RoleBean bean = new RoleBean();

		// bean.setId(1L);
		bean.setName("kkkkkkk");
		bean.setDescription("vijay");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		Date date = new Date();
		bean.setCreatedDateTime(new Timestamp(date.getTime()));
		bean.setModifiedDateTime(new Timestamp(date.getTime()));

		try {
			long pk = model.add(bean);
			RoleBean addedBean = model.findByPK(pk);

			if (addedBean == null) {
				System.out.println("Test Add failed");
			} else {
				System.out.println("test Add Successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws Exception {

		long pk = 1L;

		RoleBean bean = new RoleBean();

		bean.setId(pk);

		int id = model.delete(bean);

		RoleBean deleteBean = model.findByPK(pk);
		if (deleteBean != null) {
			System.out.println("Test delete failed");
		} else {
			System.out.println("Test delete successfull");
		}

	}

	public static void testFindByPk() throws Exception {

		long pk = 2L;

		RoleBean bean = model.findByPK(pk);

		if (bean != null) {

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} else {
			System.out.println("failed findByPk");
		}
	}

	public static void testFindByName() throws Exception {

		String name = "Kartik Vijayvargiya";

		RoleBean findBean = model.findByName(name);

		if (findBean != null) {
			System.out.println(findBean.getId());
			System.out.println(findBean.getName());
			System.out.println(findBean.getDescription());
		} else {
			System.out.println("failed testFindByName");
		}

	}

	public static void testUpdate() throws Exception {

		try {
			RoleBean bean = model.findByPK(2L);

			bean.setName("ram");
			bean.setDescription("hanuman");
			bean.setCreatedBy("ram");
			bean.setModifiedBy("jai shree ram");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			model.update(bean);

			RoleBean updateBean = model.findByPK(4L);

			if (updateBean.getName().equalsIgnoreCase("ram")) {
				System.out.println("updated successfully");
			} else {
				System.out.println("failed to update");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() throws Exception {

		RoleBean bean = new RoleBean();

		bean.setName("ram");

		List list = new ArrayList();

		try {
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testList() {

		ArrayList<RoleBean> arrList = new ArrayList<RoleBean>();
		arrList = model.list();
		RoleBean bean = new RoleBean();
		if (arrList.size() < 0) {
			System.out.println("List failed");
		}

		Iterator<RoleBean> iterator = arrList.iterator();

		while (iterator.hasNext()) {
			bean = (RoleBean) iterator.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		}

	}

}
