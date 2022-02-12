package in.co.rays.project_4.util;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.controller.BaseCtl;
import in.co.rays.project_4.controller.ORSView;
import in.co.rays.project_4.model.AppRole;
import in.co.rays.project_4.model.BaseModel;
import in.co.rays.project_4.model.UserModel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides utility operation for Servlet container like forward,
 * redirect, handle generic exception, manage success and error message, manage
 * default Bean and List, manage pagination parameters
 *
 * @author Danish
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class ServletUtility {
	
	

    /**
     * Forward to given JSP/Servlet
     *
     * @param page
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void forward(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
    	System.out.println("ServletUtility.forward(page, request, response) line 44....");
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Forward to Layout View
     *
     * @param page
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void forwardView(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
    	System.out.println("ServletUtility.forward(page, request, response) line 60....");
        request.setAttribute("bodyPage", page);
        RequestDispatcher rd = request
                .getRequestDispatcher(ORSView.LAYOUT_VIEW);
        rd.forward(request, response);
    }

    /**
     * Redirect to given JSP/Servlet
     *
     * @param page
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void redirect(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
    	System.out.println("ServletUtility.redirect(page, request, response) line 78....");
    	response.sendRedirect(page);
    }

    /**
     * Redirect to Application Error Handler Page
     *
     * @param e
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void handleException(Exception e, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
    	System.out.println("ServletUtility.handleException(e, request, response) line 93....");
    	request.setAttribute("exception", e);
        response.sendRedirect(ORSView.ERROR_CTL);
    }

    /**
     * Gets error message from request
     *
     * @param property
     * @param request
     * @return
     */
    public static String getErrorMessage(String property,
            HttpServletRequest request) {
    	System.out.println("ServletUtility.getErrorMessage(property, request) line 107....");
        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * returns all input error messages
     *
     * @deprecated Use HTMLUtil method instead
     * @param request
     * @return
     */
    public static String getErrorMessageHtml(HttpServletRequest request) {
    	System.out.println("ServletUtility.getErrorMessageHTML(request) line 124....");
        Enumeration<String> e = request.getAttributeNames();

        StringBuffer sb = new StringBuffer("<UL>");
        String name = null;

        while (e.hasMoreElements()) {
            name = e.nextElement();
            if (name.startsWith("error.")) {
                sb.append("<LI class='error'>" + request.getAttribute(name)
                        + "</LI>");
            }
        }
        sb.append("</UL>");
        return sb.toString();
    }

    /**
     * Gets a message from request
     *
     * @param property
     * @param request
     * @return
     */
    public static String getMessage(String property, HttpServletRequest request) {
    	System.out.println("ServletUtility.getMessage(property, request) line 149....");
    	String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets error message to request
     *
     * @param msg
     * @param request
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
    	System.out.println("ServletUtility.setErrorMessage(msg, request) line 165....");
    	request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Gets error message from request
     *
     * @param request
     * @return
     */
    public static String getErrorMessage(HttpServletRequest request) {
    	System.out.println("ServletUtility.getErrorMessage(request) line 176....");
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets success message to request
     *
     * @param msg
     * @param request
     */
    public static void setSuccessMessage(String msg, HttpServletRequest request) {
    	System.out.println("ServletUtility.setSuccessMessage(msg, request) line 192....");
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    /**
     * Gets success message from request
     *
     * @param request
     * @return
     */
    public static String getSuccessMessage(HttpServletRequest request) {
    	System.out.println("ServletUtility.getSuccessMessage(request) line 203....");
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    public static void setModel(BaseModel model, HttpServletRequest request) {
    	System.out.println("ServletUtility.setModel(model, request) line 213....");
        request.setAttribute("model", model);
    }

    /**
     * Sets default Bean to request
     *
     * @param bean   
     * @param request
     */
    public static void setBean(BaseBean bean, HttpServletRequest request) {
    	System.out.println("ServletUtility.setBean() line 224....");
    	request.setAttribute("bean", bean);
    }

    public static void setUserModel(UserModel model, HttpServletRequest request) {
    	System.out.println("ServletUtility.setUserModel(model, request) line 229....");
    	request.getSession().setAttribute("user", model);
    }

    /**
     * Gets default bean from request
     *
     * @param request
     * @return
     */

    public static BaseBean getBean(HttpServletRequest request) {
    	System.out.println("ServletUtility.getBean(request) line 241....");
        return (BaseBean) request.getAttribute("bean");
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
    	System.out.println("ServletUtility.isLoggedIn(request) line 246....");
        UserModel model = (UserModel) request.getSession().getAttribute("user");
        return model != null;
    }

    /**
     * Returns logged in user role
     *
     * @param request
     * @return
     */

   public static long getRole(HttpServletRequest request) {
	   System.out.println("ServletUtility.getRole(request) line 259....");
      UserModel model = (UserModel) request.getSession().getAttribute("user");
      if (model != null) {
          return model.getRoleId();
      } else {
          return AppRole.STUDENT;
     }
  }

    /**
     * gets Model from request scope
     *
     * @param request
     * @return
     */
    public static BaseModel getModel(HttpServletRequest request) {
    	System.out.println("ServletUtility.getModel(request) line 275....");
        return (BaseModel) request.getAttribute("model");
    }

    /**
     * Get request parameter to display. If value is null then return empty
     * string
     *
     * @param property
     * @param request
     * @return
     */

    public static String getParameter(String property,
            HttpServletRequest request) {
    	System.out.println("ServletUtility.getParameter(property, request) line 290....");
        String val = (String) request.getParameter(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets default List to request
     *
     * @param list
     * @param request
     */
    public static void setList(List list, HttpServletRequest request) {
    	System.out.println("ServletUtility.setList(list, request) line 306....");
        request.setAttribute("list", list);
    }

    /**
     * Gets default list from request
     *
     * @param request
     * @return
     */
    public static List getList(HttpServletRequest request) {
    	System.out.println("ServletUtility.getList(request) line 316....");
        return (List) request.getAttribute("list");
    }

    /**
     * Sets Page Number for List pages
     *
     * @param pageNo
     * @param request
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
    	System.out.println("ServletUtility.setPageNo(pageNo, request) line 328....");
        request.setAttribute("pageNo", pageNo);
    }

    /**
     * Gets Page Number for List pages
     *
     * @param request
     * @return
     */
    public static int getPageNo(HttpServletRequest request) {
    	System.out.println("ServletUtility.getPageNo(request) line 339....");
        return  (Integer) request.getAttribute("pageNo");
    }

    /**
     * Sets Page Size for list pages
     *
     * @param pageSize
     * @param request
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
    	System.out.println("ServletUtility.setPageSize(pageSize, request) line 350....");
        request.setAttribute("pageSize", pageSize);
    }

  
    public static int getPageSize(HttpServletRequest request) {
    	System.out.println("ServletUtility.getPageSize(request) line 356....");
        return  (Integer) request.getAttribute("pageSize");
    }

}
