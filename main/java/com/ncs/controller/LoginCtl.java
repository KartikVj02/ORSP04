package com.ncs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ncs.Model.RoleModel;
import com.ncs.Model.UserModel;
import com.ncs.beans.BaseBean;
import com.ncs.beans.RoleBean;
import com.ncs.beans.UserBean;
import com.ncs.exceptions.ApplicationException;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.PropertyReader;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("LoginCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug(" Method doGet Started");
		System.out.println(" Method doGet Started of LoginCtl");

		HttpSession session = request.getSession(false);
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("doGet of LoginCtl: " + op);
		if (OP_LOG_OUT.equals(op) && !OP_SIGN_IN.equals(op)) {

			session.invalidate();
			ServletUtility.setSuccessMessage("User Logout Succesfully", request);
			ServletUtility.forward(getView(), request, response);
			return;
		} /*
			 * else{ ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			 * return; }
			 */

		ServletUtility.forward(getView(), request, response);

		log.debug("LoginCtl Method doGet Ended");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(" Login Ctl Method doPost Started");

		HttpSession session = request.getSession(true);
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				ServletUtility.setBean(bean, request);

				if (bean != null) {
					session.setAttribute("user", bean);

					long rollId = bean.getRoleId();
					System.out.println("role id in loginctl :--------" + rollId);

					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}

					// Code of The URI

					String str = (String) session.getAttribute("URI");
					if (str == null || "null".equalsIgnoreCase(str)) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}

				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);

				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		// ServletUtility.setBean(bean, request);
		log.debug("loginCtl Method doPost Ended");
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}