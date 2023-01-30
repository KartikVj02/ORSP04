package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ncs.Model.CourseModel;
import com.ncs.Model.SubjectModel;
import com.ncs.beans.CourseBean;
import com.ncs.beans.SubjectBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.exceptions.DuplicateRecordException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(SubjectCtl.class);

	protected void preload(HttpServletRequest request) {

		System.out.println("preload enter");

		CourseModel cmodel = new CourseModel();

		try {
			ArrayList<CourseBean> cList = cmodel.list();
			request.setAttribute("CourseList", cList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		System.out.println("preload out");
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate Method of Subject Ctl start");
		System.out.println("subjectctl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Invalid Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("coursename"))) {
			request.setAttribute("coursename", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		log.debug("validate Method of Subject Ctl  End");
		System.out.println("subjectctl validate ended");
		return pass;
	}

	protected SubjectBean populateBean(HttpServletRequest request) {
		log.debug("Populate bean Method of Subject Ctl start");
		System.out.println("populate bean enter");

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setCourseId(DataUtility.getLong(request.getParameter("coursename")));
		System.out.println(request.getParameter("coursename"));
		bean.setSubjectName(DataUtility.getString(request.getParameter("name")));
		System.out.println(request.getParameter("name"));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		System.out.println(request.getParameter("description"));

		populateDTO(bean, request);

		log.debug("PopulateBean Method of Subject Ctl End");
		System.out.println("populate bean out");
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do get Method of Subject Ctl start ");

		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectModel model = new SubjectModel();
		SubjectBean bean = null;
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("Do get Method of Subject Ctl End");
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post Method of Subject Ctl start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		SubjectModel model = new SubjectModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" Subject is Succesfully Updated ", request);

				} else {
					long pk = model.add(bean);
					System.out.println(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" Subject is Succesfully Added ", request);
				}
				// ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			/*
			 * catch (ApplicationException e) { log.error(e);
			 * ServletUtility.handleException(e, request, response); return; } catch
			 * (DuplicateRecordException e) { ServletUtility.setBean(bean, request);
			 * ServletUtility.setErrorMessage("Subject name already Exsist", request); }
			 */
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("Do post Method of Subject Ctl End");
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}
