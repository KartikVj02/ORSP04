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
import com.ncs.beans.BaseBean;
import com.ncs.beans.CourseBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {

	/** The log. */
	public static Logger log = Logger.getLogger(CourseListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {

		CourseModel model = new CourseModel();
		List<CourseBean> clist = null;

		try {
			clist = model.list(0, 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		request.setAttribute("CourseList", clist);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("cname")));
		// bean.setName(DataUtility.getString(request.getParameter("cname")));
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("do get method of CourseCtl Started");
		List list = null;

		List nextList = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CourseBean bean = (CourseBean) populateBean(request);

		CourseModel model = new CourseModel();

//		String op = DataUtility.getString(request.getParameter("operation"));
		// String[] ids = request.getParameterValues("ids");

		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("do get method of CourseCtl End");
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("after delete wapas");
		ArrayList<CourseBean> list = null;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(request.getParameter("pageSize")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		CourseBean bean = (CourseBean) populateBean(request);

		CourseModel model = new CourseModel();

		System.out.println("-----------------)))((((" + ids);
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				CourseBean deletebean = new CourseBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
						// ServletUtility.forward(getView(), request, response);
					} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Course Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setBean(bean, request);

			// System.out.println("(-----------------)"+ list.size());
			// ServletUtility.setList(list, request);
			// }

		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

		ServletUtility.setBean(bean, request);

		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
