package com.ncs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ncs.Model.CollegeModel;
import com.ncs.beans.BaseBean;
import com.ncs.beans.CollegeBean;
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
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("CollegeCtl Method validate Started");
		System.out.println("3 in validate method of CollegeCtl");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Invalid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.name", "Invalid State"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.name", "Invalid City"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.name", "Invalid City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		log.debug("CollegeCtl Method validate Ended");
		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CollegeCtl Method populatebean Started");

		System.out.println("5 in populateBean() method");

		CollegeBean bean = new CollegeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

		populateDTO(bean, request);
		log.debug("CollegeCtl Method populatebean Ended");
		return bean;
	}

	/**
	 * Contains display logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside doGet() method in clgCtl");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		CollegeModel model = new CollegeModel();

		if (id > 0) {
			CollegeBean bean;
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CollegeCtl Method doPost Started");

		System.out.println("4 in doPost() method");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("operation :" + op + " and id is : " + id);
		CollegeModel model = new CollegeModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);

			System.out.println("7 in doPost() save & update condition");

			try {

				if (id > 0) {
					System.out.println("inside id > 0 condition of collegeCtl for update ");

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("College is successfully Updated ", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("College is successfully Added ", request);

					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				// ServletUtility.setSuccessMessage("College is successfully Saved ", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}

		System.out.println("end of dopost() collegeCtl");
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		System.out.println("6");
		return ORSView.COLLEGE_VIEW;
	}

}
