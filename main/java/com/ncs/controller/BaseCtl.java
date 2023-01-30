package com.ncs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.beans.BaseBean;
import com.ncs.beans.UserBean;
import com.ncs.utils.DataUtility;
import com.ncs.utils.DataValidator;
import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
public abstract class BaseCtl extends HttpServlet {

	/** The Constant OP_SAVE. */
	public static final String OP_SAVE = "Save";

	/** The Constant OP_CANCEL. */
	public static final String OP_CANCEL = "Cancel";

	/** The Constant OP_UPDATE. */
	public static final String OP_UPDATE = "Update";

	/** The Constant OP_RESET. */
	public static final String OP_RESET = "Reset";

	/** The Constant OP_LIST. */
	public static final String OP_LIST = "List";

	/** The Constant OP_SEARCH. */
	public static final String OP_SEARCH = "Search";

	/** The Constant OP_VIEW. */
	public static final String OP_VIEW = "View";

	/** The Constant OP_NEXT. */
	public static final String OP_NEXT = "Next";

	/** The Constant OP_PREVIOUS. */
	public static final String OP_PREVIOUS = "Previous";

	/** The Constant OP_NEW. */
	public static final String OP_NEW = "New";

	/** The Constant OP_DELETE. */
	public static final String OP_DELETE = "Delete";

	/** The Constant OP_GO. */
	public static final String OP_GO = "Go";

	/** The Constant OP_BACK. */
	public static final String OP_BACK = "Back";
//	public static final String OP_LOG_OUT = "Logout";

	/** Success message key constant. */
	public static final String MSG_SUCCESS = "success";

	/** Error message key constant. */
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form.
	 *
	 * @param request the request
	 */
	protected void preload(HttpServletRequest request) {
	}

	/**
	 * Populates bean object from request parameters.
	 *
	 * @param request the request
	 * @return the base bean
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		return null;
	}

	/**
	 * Populates Generic attributes in DTO.
	 *
	 * @param dto     the dto
	 * @param request the request
	 * @return object of the bean
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		System.out.println("6 in baseCtl populateDTO() method");
		
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {
			modifiedBy = userbean.getLogin();
			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));
		if (cdt > 0) {
			dto.setCreatedDateTime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDateTime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDateTime(DataUtility.getCurrentTimestamp());
		return dto;
	}

	/**
	 * Call the service method of this Controller.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("1 in service() method of basectl");
		
		// Load the preloaded data required to display at HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation

		System.out.println(op + " : operation in basectl");

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			// Check validation, If fail then send back to page with error
			// messages

			System.out.println("2 inside five condition");
			
			System.out.println("in basectl service " + validate(request) + ">>>>>>>>>>>>>>>");
			if (!validate(request)) {
				
				BaseBean bean = (BaseBean) populateBean(request);
				// System.out.println("=======-----======)))"+bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				System.out.println("checking (!validation) ");
				return;
			}
		}
		System.out.println("before super service------------------->>>>>>>>");
		
		super.service(request, response);
		// System.out.println("after super service------------------->>>>>>>>");

	}

	/**
	 * Returns the VIEW page of this Controller.
	 *
	 * @return the view
	 */

	protected abstract String getView();
}