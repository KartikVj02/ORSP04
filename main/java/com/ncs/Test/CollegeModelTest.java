package com.ncs.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ncs.Model.CollegeModel;
import com.ncs.beans.CollegeBean;
import com.ncs.exceptions.ApplicationException;

public class CollegeModelTest {

	public static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws Exception {

		 testAdd();
		// testFindByPk();
		// testFindByName();
		// testDelete();
		// testUpdate();
		// testSearch();
		//testList();

	}

	private static void testAdd() {

		CollegeBean bean = new CollegeBean();
		try {

			bean.setName("Delhi college");
			bean.setAddress("Dhar road");
			bean.setState("MP");
			bean.setCity("indore");
			bean.setPhoneNo("9090988888");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long no = model.add(bean);

			if (no > 0) {
				System.out.println("College added successfully");
			} else {
				System.out.println("failed to add college");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		CollegeBean bean = new CollegeBean();
		long pk = 1L;

		bean = model.findByPK(pk);

		if (bean == null) {
			System.out.println("id doesn't exist");
		} else {

			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());

		}

	}

	public static void testFindByName() {

		CollegeBean bean = new CollegeBean();

		bean.setName("sgsits");
		try {
			bean = model.findByName(bean.getName());

			if (bean == null) {
				System.out.println("name doesn't exist");
			} else {
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getName());
				System.out.println(bean.getPhoneNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() {
		try {
			long pk = 2L;

			CollegeBean bean = new CollegeBean();

			bean.setId(pk);

			model.delete(bean);
			System.out.println("Test Delete successfull");
			CollegeBean deletedBean = new CollegeBean();
			deletedBean = model.findByPK(pk);

			if (deletedBean == null) {
				System.out.println("test delete failed");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws ApplicationException {

		CollegeBean bean = new CollegeBean();

		bean = model.findByPK(3L);

		bean.setName("sgsits semi gov");
		bean.setAddress("lantern square near rani sati gate");
		try {
			model.update(bean);

			CollegeBean updatedBean = new CollegeBean();

			updatedBean = model.findByPK(bean.getId());

			if (!updatedBean.getName().equalsIgnoreCase(bean.getName())) {
				System.out.println("failed to update college");
			} else {
			}
			System.out.println("updated succesfull");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testSearch() {

		CollegeBean bean = new CollegeBean();
		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
		bean.setName("ips");
		bean.setAddress("rau road");

		try {

			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("college search failed");
			}

			Iterator<CollegeBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (CollegeBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testList() throws ApplicationException {

		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
		CollegeBean bean = new CollegeBean();
		try {

			list = (ArrayList<CollegeBean>) model.list();

			if (list.size() < 0) {
				System.out.println("test list failed");
			}

			Iterator<CollegeBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (CollegeBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());

			}

		} catch (Exception e) {

		}

	}

}
