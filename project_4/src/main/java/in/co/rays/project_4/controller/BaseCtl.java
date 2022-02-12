package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.ServletUtility;

public abstract class BaseCtl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* The Constant OP_SAVE. */
	public static final String OP_SAVE = "Save";

	/* The Constant OP_CANCEL. */
	public static final String OP_CANCEL = "Cancel";

	/* The Constant OP_UPDATE. */
	public static final String OP_UPDATE = "Update";

	/* The Constant OP_RESET. */
	public static final String OP_RESET = "Reset";

	/* The Constant OP_LIST. */
	public static final String OP_LIST = "List";

	/* The Constant OP_SEARCH. */
	public static final String OP_SEARCH = "Search";

	/* The Constant OP_VIEW. */
	public static final String OP_VIEW = "View";

	/* The Constant OP_NEXT. */
	public static final String OP_NEXT = "Next";

	/* The Constant OP_PREVIOUS. */
	public static final String OP_PREVIOUS = "Previous";

	/* The Constant OP_NEW. */
	public static final String OP_NEW = "New";

	/* The Constant OP_DELETE. */
	public static final String OP_DELETE = "Delete";

	/* The Constant OP_GO. */
	public static final String OP_GO = "Go";

	/* The Constant OP_BACK. */
	public static final String OP_BACK = "Back";
//	public static final String OP_LOG_OUT = "Logout";

	/* Success message key constant. */
	public static final String MSG_SUCCESS = "success";

	/* Error message key constant. */
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	protected boolean validate(HttpServletRequest request) {
		System.out.println("BaseCtl.validate(request) line 76....");
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form.
	 *
	 * @param request the request
	 */
	protected void preload(HttpServletRequest request) {
		System.out.println("BaseCtl.preload(request) line 86...");
	}

	/**
	 * Populates bean object from request parameters.
	 *
	 * @param request the request
	 * @return the base bean
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("BaseCtl.populateBean(request) line 96....");
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
		System.out.println("BaseCtl.populateDTO(BaseBean, request) line 108....");

		String createdBy = request.getParameter("createdBy");
		
		String modifiedBy = null;
		System.out.println("BaseCtl.populateDTO(BaseBean, request) line 113....createdBy:"+createdBy+ "modifiedBy:"+modifiedBy);
		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
			System.out.println("BaseCtl.populateDTO(BaseBean, request) line 119....createdBy:"+createdBy+ "modifiedBy:"+modifiedBy);
		} else {
			modifiedBy = userbean.getLogin();
			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
				System.out.println("BaseCtl.populateDTO(BaseBean, request) line 125....createdBy:"+createdBy+ "modifiedBy:"+modifiedBy);
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));
		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		System.out.println("BaseCtl.populateDTO(BaseBean, request) line 136....CreatedBy:"+dto.getCreatedBy()+ "ModifiedBy:"+dto.getModifiedBy()+"createdDatetime:"+dto.getCreatedDatetime()+"modifiedDatetime"+dto.getModifiedDatetime());
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
		System.out.println("BaseCtl.service(request, response) before preload line 149....");
		// Load the preloaded data required to display at HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		
		// Check if operation is not DELETE, VIEW, CANCEL, Reset, and NULL then
		// perform input data validation
		System.out.println(op+ "(operation)BaseCtl.service() before validator if loop line no 157....");
		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			
			System.out.println(op+ "in basectl.service() after validator if loop line 161...." + validate(request));
			/*
			 * Check validation, If fail then send back to page with error messages
			 */
			if (!validate(request)) {
				System.out.println("basectl.service() line 166....");
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		System.out.println(" BaseCtl.service() before super service line no 172------------------->>>>>>>>");
		// service method of servlet interface
		super.service(request, response);
		System.out.println(" BaseCtl.service() after super service line no 174------------------->>>>>>>>");
	}

	/**
	 * Returns the VIEW page of this Controller.
	 *
	 * @return the view
	 */
	protected abstract String getView();
	
}