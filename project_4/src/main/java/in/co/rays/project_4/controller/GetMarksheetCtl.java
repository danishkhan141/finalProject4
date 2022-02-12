package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.MarksheetModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;
// TODO: Auto-generated Javadoc
/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 * 
 * @author Danish
 * @version 1.0
 * Copyright (c) SunilOS
 */
@WebServlet(name="GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {

    /** The log. */
    private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

    /* (non-Javadoc)
     * @see in.co.rays.project_4.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("GetMarksheetCTL Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }
        else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Roll No. must be in Formate (0000XX000000)");
			pass = false;
        }
        
        log.debug("GetMarksheetCTL Method validate Ended");
        return pass;
    }

    /* (non-Javadoc)
     * @see in.co.rays.project_4.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("GetMarksheetCtl Method populatebean Started");

        MarksheetBean bean = new MarksheetBean();

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        log.debug("GetMarksheetCtl Method populatebean Ended");
        return bean;
    }

    /**
     * Concept of Display method.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
     
    	ServletUtility.forward(getView(), request, response);
    }

    /**
     * Concept of Submit Method.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("GetMarksheetCtl Method doGet Started");
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        MarksheetModel model = new MarksheetModel();
        MarksheetBean bean = (MarksheetBean) populateBean(request);


        if (OP_GO.equalsIgnoreCase(op)) {

            try {
                bean = model.findByRollNo(bean.getRollNo());
        //        ServletUtility.setList(list, request);
                
             
      
                if (bean != null) {
                    ServletUtility.setBean(bean, request);
                }else {
                    ServletUtility.setErrorMessage("RollNo Does Not Exists",request);
                    
                }
            } catch (ApplicationException e) {
               e.printStackTrace();
            	log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
            else if (OP_RESET.equalsIgnoreCase(op)) {
            	ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
            	return ;
			}
        ServletUtility.forward(getView(), request, response);
        log.debug("MarksheetCtl Method doGet Ended");
    }

    /* (non-Javadoc)
     * @see in.co.rays.project_4.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.GET_MARKSHEET_VIEW ;
    }
}
