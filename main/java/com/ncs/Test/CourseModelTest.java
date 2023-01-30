package com.ncs.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ncs.Model.CourseModel;
import com.ncs.beans.CourseBean;
import com.ncs.exceptions.ApplicationException;

public class CourseModelTest {

	public static CourseModel model = new CourseModel();

	public static void main(String[] args) throws ApplicationException {
		 testAdd();
		// testFindByName();
		// testFindByPk();
		// testDelete();
		// testUpdate();
		// testList();
		//testSearch();
	}

	private static void testAdd() {

		CourseBean bean = new CourseBean();
		try {

			bean.setName("H.M");
			bean.setDuration("3 years");
			bean.setDescription("Hotel Management");

			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long no = model.add(bean);

			if (no > 0) {
				System.out.println("Course added successfully");
			} else {
				System.out.println("failed to add Course");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPk() throws ApplicationException {

		CourseBean bean = new CourseBean();
		long pk = 1L;

		bean = model.findByPk(pk);

		if (bean == null) {
			System.out.println("id doesn't exist");
		} else {

			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
		}

	}

	public static void testFindByName() {

		CourseBean bean = new CourseBean();

		bean.setName("BCA");
		try {
			bean = model.findByName(bean.getName());

			if (bean == null) {
				System.out.println("name doesn't exist");
			} else {

				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() {
		try {
			long pk = 2L;

			CourseBean bean = new CourseBean();

			bean.setId(pk);

			model.delete(bean);
			CourseBean deletedBean = new CourseBean();
			deletedBean = model.findByPk(pk);

			if (deletedBean == null) {
				System.out.println("test delete successfull");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws ApplicationException {

		CourseBean bean = new CourseBean();

		bean = model.findByPk(2L);

		bean.setDescription("like BE computer science");
		try {
			model.update(bean);

			CourseBean updatedBean = new CourseBean();

			updatedBean = model.findByPk(bean.getId());

			if (!updatedBean.getName().equalsIgnoreCase(bean.getName())) {
				System.out.println("failed to update college");
			} else {
			}
			System.out.println("updated succesfull");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testList() throws ApplicationException {

		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		CourseBean bean = new CourseBean();
		try {

			list = (ArrayList<CourseBean>) model.list();

			if (list.size() < 0) {
				System.out.println("test list failed");
			}

			Iterator<CourseBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (CourseBean) i.next();

				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());

			}

		} catch (Exception e) {

		}

	}

	private static void testSearch() {

		CourseBean bean = new CourseBean();
		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		bean.setName("BE");

		try {

			list = (ArrayList<CourseBean>) model.search(bean);
			if (list.size() < 0) {
				System.out.println("college search failed");
			}

			Iterator<CourseBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (CourseBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
