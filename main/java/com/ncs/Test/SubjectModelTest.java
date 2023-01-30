package com.ncs.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ncs.Model.SubjectModel;
import com.ncs.beans.SubjectBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DuplicateRecordException;

public class SubjectModelTest {

	public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) throws ParseException {
		 testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testSearch();
		// testList();

	}

	public static void testAdd() throws ParseException {

		try {
			SubjectBean bean = new SubjectBean();

			// bean.setId(1L);
			bean.setSubjectName("Cooking Ethics");
			bean.setDescription("Basic details");
			bean.setCourseId(6L);
			//bean.setCourseName("BE");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("added successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a Student.
	 */
	public static void testDelete() {

		try {
			SubjectBean bean = new SubjectBean();
			long pk = 3L;
			bean.setId(pk);
			model.delete(bean);
			SubjectBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a Student.
	 */
	public static void testUpdate() {

		try {
			SubjectBean bean = model.findByPk(4L);

			bean.setSubjectName("c++");

			model.update(bean);

			SubjectBean updatedbean = model.findByPk(4L);
			if (!"c++".equals(updatedbean.getSubjectName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests find a Student by PK.
	 */
	public static void testFindByPK() {
		try {
			SubjectBean bean = new SubjectBean();
			long pk = 1L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getModifiedBy());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get Search.
	 */

	public static void testSearch() {

		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			bean.setSubjectName("c++");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get List.
	 */
	public static void testList() {

		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getSubjectName());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
