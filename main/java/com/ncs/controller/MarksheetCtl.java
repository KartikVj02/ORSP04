package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ncs.Model.MarksheetModel;
import com.ncs.Model.StudentModel;
import com.ncs.beans.BaseBean;
import com.ncs.beans.MarksheetBean;
import com.ncs.beans.StudentBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.exceptions.RecordNotFoundException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel model = new StudentModel();
		try {
			ArrayList<StudentBean> arrList = model.list();
			System.out.println(arrList.size() + " :Inside preLoad of MarksheetCtl");
			request.setAttribute("studentList", arrList);
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MarksheetCtl Method validate Started");
		System.out.println("MarksheetCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can not be greater than 100");
			pass = false;
		}
		
		//added to prevent negative entry..
		if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks can not be less than 0");
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can not be greater than 100");
			pass = false;
		}
		
		//added to prevent negative entry..
				if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
					request.setAttribute("chemistry", "Marks can not be less than 0");
					pass = false;
				}

		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can not be greater than 100");
			pass = false;
		}
		
		//added to prevent negative entry..
				if (DataUtility.getInt(request.getParameter("maths")) < 0) {
					request.setAttribute("maths", "Marks can not be less than 0");
					pass = false;
				}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		log.debug("MarksheetCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(request.getParameter("id"));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		System.out.println(request.getParameter("rollNo"));

		bean.setStudentId(DataUtility.getLong(request.getParameter("name")));
		System.out.println(request.getParameter("name"));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		System.out.println(request.getParameter("physics"));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		System.out.println(request.getParameter("chemistry"));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		System.out.println(request.getParameter("maths"));

		populateDTO(bean, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("MarksheetCtl Method doGet Started");
		log.debug("MarksheetCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(request.getParameter("id") + " :MarksheetCtl doGet()");
		if (id > 0) {
			System.out.println("id is greater than 0");
			MarksheetBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
		System.out.println("MarksheetCtl Method doGet Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Updated ", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Added ", request);

				}
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		/*
		 * else if (OP_DELETE.equalsIgnoreCase(op)) {
		 * 
		 * MarksheetBean bean = (MarksheetBean) populateBean(request);
		 * System.out.println("in try"); try { model.delete(bean);
		 * ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
		 * System.out.println("in try"); return; } catch (Exception e) {
		 * System.out.println("in catch"); log.error(e);
		 * ServletUtility.handleException(e, request, response); return; }
		 * 
		 * }
		 */
		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		System.out.println("get view of MarksheetctlO");
		return ORSView.MARKSHEET_VIEW;
	}

}
