package com.ncs.controller;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ncs.Model.UserModel;
import com.ncs.beans.BaseBean;
import com.ncs.beans.UserBean;
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
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {

	public static Logger log = Logger.getLogger(MyProfileCtl.class);

	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";
	public static final String OP_SAVE = "save";

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MyProfileCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

			return pass;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			System.out.println("firstName" + request.getParameter("firstName"));
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		log.debug("MyProfileCtl Method validate Ended");

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("MyProfileCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		System.out.println(request.getParameter("login"));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		System.out.println(request.getParameter("firstName"));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		System.out.println(request.getParameter("lastName"));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
		System.out.println(request.getParameter("mobile"));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		System.out.println(request.getParameter("gender"));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println(request.getParameter("dob"));
		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("MyprofileCtl Method doGet Started");

		HttpSession session = request.getSession();

		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserBean bean;
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MyProfileCtl Method doGet Ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		log.debug("MyprofileCtl Method doPost Started");

		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					UserBean.setFirstName(bean.getFirstName());
					UserBean.setLastName(bean.getLastName());
					UserBean.setGender(bean.getGender());
					UserBean.setMobileNo(bean.getMobileNo());
					UserBean.setDob(bean.getDob());

					model.update(UserBean);

				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Error While Updating", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("MyProfileCtl Method doPost Ended");

	}

	@Override
	protected String getView() {

		return ORSView.MY_PROFILE_VIEW;
	}

}
