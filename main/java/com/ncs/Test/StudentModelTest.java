package com.ncs.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ncs.Model.StudentModel;
import com.ncs.beans.StudentBean;
import com.ncs.exceptions.ApplicationException;

public class StudentModelTest {

	public static StudentModel model = new StudentModel();

	public static void main(String[] args) throws ParseException {

		 testAdd();
		// testFindByPK();
		// testFindByEmailId();
		// testDelete();
	    // testUpdate();
		// testSearch();
		// testList();
	}

	private static void testAdd() throws ParseException {

		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {

			bean.setCollegeId(13L);
			//bean.setCollegeName("kk");
			bean.setFirstName("nilesh");
			bean.setLastName("patel");
			bean.setDob(sdf.parse("07-06-1997"));
			bean.setMobileNo("7778899999");
			bean.setEmail("np@gmail.com");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);

			if (pk == 0) {
				System.out.println("test add failed");
			} else {
				System.out.println("student added");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testFindByPK() {

		long pk = 2L;
		try {
			StudentBean findBean = model.findByPK(pk);

			if (findBean != null) {
				System.out.println("student exits");
				System.out.println(findBean.getId());
				System.out.println(findBean.getFirstName());
				System.out.println(findBean.getLastName());
			} else {
				System.out.println("id does not exits");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testFindByEmailId() {

		StudentBean bean = new StudentBean();
		bean.setEmail("vijayhardik99@gmail.com");
		try {
			StudentBean findByEmailBean = model.findByEmail(bean.getEmail());

			if (findByEmailBean == null) {
				System.out.println("email does not exits");
			} else {
				System.out.println("email exits");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {

			StudentBean bean = new StudentBean();
			bean.setId(3L);
			model.delete(bean);
			StudentBean deletedBean = model.findByPK(bean.getId());

			if (deletedBean != null) {
				System.out.println("test delete failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		long pk = 9L;
		try {
			StudentBean bean = model.findByPK(pk);
			bean.setCollegeId(3L);
			bean.setCollegeName("sgsits");
			bean.setFirstName("hardik");
			bean.setDob(sdf.parse("08-01-1997"));
			model.update(bean);
			StudentBean updatedBean = model.findByPK(pk);
			if (!updatedBean.getFirstName().equalsIgnoreCase(bean.getFirstName())) {
				System.out.println("test update failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testSearch() {
		ArrayList<StudentBean> list = new ArrayList<StudentBean>();

		StudentBean bean = new StudentBean();
		bean.setFirstName("hardik");
		try {
			list = model.search(bean);

			if (list.size() < 0) {
				System.out.println("test search failed");
			}

			Iterator<StudentBean> i = list.iterator();

			while (i.hasNext()) {

				bean = (StudentBean) i.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testList() {
		ArrayList<StudentBean> arrayList = new ArrayList<StudentBean>();
		StudentBean bean = new StudentBean();
		try {
			arrayList = model.list();
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (arrayList.size() < 0) {
			System.out.println("test list is failed");
		}

		Iterator<StudentBean> i = arrayList.iterator();
		while (i.hasNext()) {
			bean = (StudentBean) i.next();

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
		}

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
