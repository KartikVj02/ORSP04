package com.ncs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

	public static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	public static final String OP_GO = "Go";
	public static final String OP_RESET = "Reset";

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		}
		log.debug("ForgetPasswordCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		log.debug("ForgetPasswordCtl Method populatebean Ended");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("doget method of ForgetPasswordCtl started");
		doPost(request, response);
		log.debug("doget method of ForgetPasswordCtl end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("doPost method of ForgetPasswordCtl started");

		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean) populateBean(request);

		// get model
		UserModel model = new UserModel();

		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				model.forgetPassword(bean.getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your email id.", request);
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("doPost method of ForgetPasswordCtl end");
	}

	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
