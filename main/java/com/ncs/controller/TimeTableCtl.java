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
import com.ncs.Model.TimeTableModel;
import com.ncs.beans.CourseBean;
import com.ncs.beans.SubjectBean;
import com.ncs.beans.TimeTableBean;
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
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		SubjectModel smodel = new SubjectModel();

		ArrayList<CourseBean> clist = new ArrayList<CourseBean>();
		ArrayList<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = cmodel.list();
			slist = smodel.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		log.debug("validate method of TimeTable Ctl End");
		return pass;
	}

	protected TimeTableBean populateBean(HttpServletRequest request) {
		log.debug("populateBean method of TimeTable Ctl start");
		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		System.out.println(request.getParameter("subjectId") + "subject id in populate of TTCtl");
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		System.out.println("courseId>>>>>>> " + request.getParameter("courseId"));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		System.out.println(request.getParameter("semester"));
		bean.setExamDate(DataUtility.getDate(request.getParameter("ExDate")));
		System.out.println(request.getParameter("ExDate"));
		bean.setExamTime(DataUtility.getString(request.getParameter("ExTime")));
		System.out.println(request.getParameter("ExTime"));
		log.debug("populateBean method of TimeTable Ctl End");
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do Get method of TimeTable Ctl Started");
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = null;
		if (id > 0) {
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		log.debug("do Get method of TimeTable Ctl End");
		System.out.println("Timetable ctl .do get End........>>>>>");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of TimeTable Ctl start");

		List list = null;

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Updated", request);

				} else {

					model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Added", request);

				}
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}
}
