package com.ncs.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ncs.Model.FacultyModel;
import com.ncs.beans.FacultyBean;
import com.ncs.beans.UserBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DuplicateRecordException;

public class FacultyModelTest {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws ParseException, ApplicationException {
		 testAdd();
		// testDelete();
		// testUpdate();
		// testSearch();
		// testList();
		//testFindByEmailId();
	}

	private static void testAdd() throws ParseException, ApplicationException {

		FacultyBean bean = new FacultyBean();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		bean.setFirstName("ketan");
		bean.setLastName("parik");
		bean.setGender("male");
		bean.setEmailId("kp@gmail.com");
		bean.setMobileNo("7709092099");
		bean.setCollegeId(15L);
		//bean.setCollegeName("mist");
		bean.setCourseId(1L);
		//bean.setCourseName("hhhh");
		bean.setDob(sdf.parse("04/06/1999"));
		bean.setSubjectId(1L);
		//bean.setSubjectName("Data Structure");

		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		try {
			long pk = model.add(bean);
			if (pk != 0) {
				System.out.println("successfully Added");
			}
			System.out.println("test add successfull");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {
			FacultyBean bean = new FacultyBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete successfully");

			FacultyBean deletedBean = model.findByPk(pk);
			if (deletedBean != null) {

				System.out.println("Test Delete fail");

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws DuplicateRecordException {

		try {
			FacultyBean bean = model.findByPk(3L);
			bean.setCollegeId(2L);
			bean.setFirstName("kaalu");
			bean.setLastName("vijay");
			model.update(bean);

			FacultyBean updatedbean = model.findByPk(3L);
			if (!"kaalu".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update fail");
			} else {
				System.out.println("Test Update Successfully");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		FacultyBean bean = new FacultyBean();
		List list = new ArrayList<FacultyBean>();
		try {
			bean.setCollegeName("mist");

			list = model.search(bean, 1, 10);

			if (list.size() < 0) {
				System.out.println("test search failed");
			}
			Iterator<FacultyBean> i = list.iterator();

			while (i.hasNext()) {
				bean = (FacultyBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testList() {

		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			list = model.list(1, 18);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByEmailId() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = model.findByEmail("vijayhardik99@gmail.com");
			if (bean != null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getSubjectId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
