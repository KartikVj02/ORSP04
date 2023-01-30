package com.ncs.Test;

import java.awt.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ncs.Model.MarksheetModel;
import com.ncs.beans.MarksheetBean;

public class MarksheetModelTest {

	public static MarksheetModel model = new MarksheetModel();

	public static void main(String[] args) {
		 testAdd();
		// testFindByPK();
		// testFindByRollNo();
		// testDelete();
		// testUpdate();
		// testSearch();
		// testList();
		//testGetMeritList();
	}

	private static void testAdd() {

		MarksheetBean bean = new MarksheetBean();
		try {
			bean.setRollNo("20210015");
			bean.setStudentId(14L);
			//bean.setName("hardik");
			bean.setPhysics(99);
			bean.setChemistry(99);
			bean.setMaths(99);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);

			if (pk != 0) {
				System.out.println("successfull added marksheet");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {

		long pk = 1L;
		try {
			MarksheetBean findBypkbean = model.findByPK(pk);

			if (findBypkbean == null) {
				System.out.println("test find by pk failed");
			}
			System.out.println(findBypkbean.getId());
			System.out.println(findBypkbean.getName());
			System.out.println(findBypkbean.getStudentId());
			System.out.println(findBypkbean.getRollNo());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testFindByRollNo() {

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo("101");
		try {
			MarksheetBean findByRollBean = model.findByRollNo(bean.getRollNo());

			if (findByRollBean == null) {
				System.out.println("test find by roll no failed");
			}
			System.out.println(findByRollBean.getId());
			System.out.println(findByRollBean.getRollNo());
			System.out.println(findByRollBean.getStudentId());
			System.out.println(findByRollBean.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		MarksheetBean bean = new MarksheetBean();

		long pk = 1L;
		bean.setId(pk);

		try {
			model.delete(bean);

			MarksheetBean deletedBean = model.findByPK(pk);

			if (deletedBean != null) {
				System.out.println("test delete failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() {

		MarksheetBean bean = model.findByPK(1L);

		bean.setPhysics(77);
		bean.setChemistry(88);
		try {

			model.update(bean);

			MarksheetBean updateBean = model.findByPK(1L);

			if (updateBean.getPhysics() != bean.getPhysics()) {
				System.out.println("test update failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testSearch() {

		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();

		MarksheetBean bean = new MarksheetBean();

//		bean.setId(1L);
		bean.setName("hardik");
		try {

			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("test search failed");
			}

			Iterator<MarksheetBean> i = list.iterator();

			while (i.hasNext()) {
				bean = (MarksheetBean) i.next();

				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testList() {
		MarksheetBean bean = new MarksheetBean();
		ArrayList<MarksheetBean> arrList = new ArrayList<MarksheetBean>();
		try {
			arrList = model.list();

			if (arrList.size() < 0) {
				System.out.println("test list is failed");
			}

			Iterator<MarksheetBean> i = arrList.iterator();

			while (i.hasNext()) {
				bean = (MarksheetBean) i.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testGetMeritList() {

		ArrayList<MarksheetBean> arrayList = new ArrayList<MarksheetBean>();
		MarksheetBean bean = null;
		try {
			arrayList = model.getMeritList();

			if (arrayList.size() < 0) {
				System.out.println("test get merit list is failed");
			}

			Iterator<MarksheetBean> i = arrayList.iterator();

			while (i.hasNext()) {	
				bean = (MarksheetBean) i.next();
			
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
