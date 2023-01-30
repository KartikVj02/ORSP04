package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ncs.Model.CollegeModel;
import com.ncs.Model.CourseModel;
import com.ncs.Model.FacultyModel;
import com.ncs.Model.SubjectModel;
import com.ncs.beans.BaseBean;
import com.ncs.beans.CollegeBean;
import com.ncs.beans.CourseBean;
import com.ncs.beans.FacultyBean;
import com.ncs.beans.SubjectBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	protected void preload(HttpServletRequest request) {

		System.out.println("preload method started ");

		CourseModel cmodel = new CourseModel();
		CollegeModel comodel = new CollegeModel();
		SubjectModel smodel = new SubjectModel();

		ArrayList<CourseBean> clist = new ArrayList<CourseBean>();
		ArrayList<CollegeBean> colist = new ArrayList<CollegeBean>();
		ArrayList<SubjectBean> slist = new ArrayList<SubjectBean>();

		try {
			clist = (ArrayList<CourseBean>) cmodel.list();
			colist = (ArrayList<CollegeBean>) comodel.list();
			slist = (ArrayList<SubjectBean>) smodel.list();

			System.out.println(clist.size() + "CourseBean");
			System.out.println(colist.size() + "CollegeBean");
			System.out.println(slist.size() + "subjectBean");

			request.setAttribute("CourseList", clist);
			request.setAttribute("CollegeList", colist);
			request.setAttribute("SubjectList", slist);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		System.out.println("validate  in ");

		log.debug("Validate Method of Faculty Ctl Started");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "FirstName"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue("error.name", "Invalid First"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue("error.name", "Invalid Last"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeid"))) {
			request.setAttribute("collegeid", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("courseid", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectid"))) {
			request.setAttribute("subjectid", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}

		System.out.println("validate out ");
		log.debug("validate Ended");
		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("populate bean faculty ctl started");
		System.out.println(" populate bean ctl  in ");
		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		System.out.println(request.getParameter("firstname"));
		bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
		System.out.println(request.getParameter("lastname"));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		System.out.println(request.getParameter("gender"));
		bean.setEmailId(DataUtility.getString(request.getParameter("loginid")));
		System.out.println(request.getParameter("loginid"));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileno")));
		System.out.println(request.getParameter("mobileno"));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println(request.getParameter("dob"));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeid")));
		System.out.println(request.getParameter("collegeid"));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseid")));
		System.out.println(request.getParameter("courseid"));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectid")));
		System.out.println(request.getParameter("subjectid"));

		// bean.setCourseName(DataUtility.getString(request.getParameter("courseid")));
		// bean.setSubjectName(DataUtility.getString(request.getParameter("subjectid")));
		populateDTO(bean, request);
		log.debug("populate bean faculty ctl Ended");
		System.out.println(" populate bean ctl out ");
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Do get of faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// Get Model
		FacultyModel model = new FacultyModel();
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			FacultyBean bean;
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		System.out.println(" do get out ");
		log.debug("Do get of  faculty ctl Ended");
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post of  faculty ctl Started");
		System.out.println("Do post of  faculty ctl Started ");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// Get Model
		FacultyModel model = new FacultyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Added", request);

					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Faculty already Exist", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		// System.out.println(" do post out ");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post of  faculty ctl Ended");
		System.out.println(" Do post of  faculty ctl Ended ");
	}

	@Override
	protected String getView() {

		return ORSView.FACULTY_VIEW;
	}

}
