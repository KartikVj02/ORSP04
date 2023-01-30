package com.ncs.controller;

import java.io.IOException;

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
import com.ncs.exceptions.RecordNotFoundException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

	public static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {

			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
		 * request.setAttribute(
		 * "oldPassword","Password should contain 8 letter with alpha-numeric and special Character"
		 * ); pass = false; }
		 */
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Old password and New password should not be same!!");
			pass = false;
		}

		else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword",
					"Password should contain 8 letter with alpha-numeric,capital latter and special Character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword",
					"Password should contain 8 letter with alpha-numeric,capital latter and special Character");
			pass = false;
		}

		else if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New password and Confirm password must be same!!");
			pass = false;
		}

		log.debug("ChangePasswordCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ChangePasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
		System.out.println("1" + request.getParameter("oldPassword"));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		System.out.println("3" + request.getParameter("confirmPassword"));

		populateDTO(bean, request);

		log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		log.debug("ChangePasswordCtl Method doGet Started");

		HttpSession session = request.getSession();
		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();

			String op = DataUtility.getString(request.getParameter("operation"));

		String newPassword = (String) request.getParameter("newPassword");

		UserModel model = new UserModel();
		UserBean bean = (UserBean) populateBean(request);

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
				if (flag == true) {
					bean = model.findByLogin(UserBean.getLogin());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			}

			catch (RecordNotFoundException e) {

				ServletUtility.setErrorMessage(e.getMessage(), request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("ChangePasswordCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
