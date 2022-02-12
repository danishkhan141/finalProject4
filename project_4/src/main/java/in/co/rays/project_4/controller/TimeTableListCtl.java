package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.SubjectModel;
import in.co.rays.project_4.model.TimeTableModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeTableListCtl.
 * 
 * @author Session Facade
 * @version 1.0
 * Copyright (c) SunilOS
 * 
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = {"/ctl/TimeTableListCtl"})
public class TimeTableListCtl extends BaseCtl {

	/** The log. */
	private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	
	protected void preload(HttpServletRequest request) {

		CourseModel model = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		List<CourseBean> list = null;
		List<SubjectBean> list2 = null;
		try {
			list = model.list();
			list2 = smodel.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("courseList", list);
		request.setAttribute("subjectList", list2);

	}

	
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("TimeTableListCtl populateBean started line 58....");
		TimeTableBean bean = new TimeTableBean();

       //bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setCourseId(DataUtility.getLong(request.getParameter("clist")));
		
		bean.setSubjectId(DataUtility.getInt(request.getParameter("slist")));
		
		//bean.setSubjectName(DataUtility.getString(request.getParameter("slist")));
		
		bean.setExamDate(DataUtility.getDateSearch(request.getParameter("Exdate")));
		System.out.println("TimeTableListCtl populateBean ended line 70...");
		System.out.println("hidsbisisdddd"+request.getParameter("Exdate"));
		
		populateDTO(bean, request);
		
		System.out.println("populate bean==========>>>> " + bean.getExamDate());

		return bean;
	}
    
    /**
     * Contains display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TimeTableListCtl doGet started line 87...");
		List list  = null ;
       
		List nextList=null;
		
		int pageNo = 1;
		
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
          
       TimeTableModel model = new TimeTableModel();
		
       TimeTableBean bean =(TimeTableBean) populateBean(request);

//		String op = DataUtility.getString(request.getParameter("operation"));
   //   String[] ids = request.getParameterValues("ids");
	    

		try {

			System.out.println("TimeTableListCtl doGet started try block before model.search line 108...");
			list = model.search(bean, pageNo, pageSize);
			nextList=model.search(bean,pageNo+1,pageSize);
			request.setAttribute("nextlist", nextList.size());
			ServletUtility.setBean(bean, request);
			
	//		ServletUtility.setList(list, request);
			
			if (list==null && list.size()==0) {
				
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

			System.out.println("TimeTableListCtl doGet ended line 87...");
		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
	}
    
    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list=null;
		List nextList=null;
		
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;


		TimeTableBean bean = (TimeTableBean) populateBean(request);	
		
		TimeTableModel model = new TimeTableModel();
		
		String[] ids = (String[]) request.getParameterValues("ids");
				
			        if (OP_SEARCH.equalsIgnoreCase(op)) {
				    pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;	
					} 
			        
					else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
						pageNo--;
					}
					else if (OP_NEW.equalsIgnoreCase(op)) 
					{
						ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
						return ;
					}
					
					else if (OP_RESET.equalsIgnoreCase(op)||OP_BACK.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
						return;
					}
					else if (OP_DELETE.equalsIgnoreCase(op)) {
						pageNo=1;
						if (ids != null && ids.length > 0) {
							TimeTableBean bean3 = new TimeTableBean();

							for (String id2 : ids) {
								int id1 = DataUtility.getInt(id2);
								bean3.setId(id1);
								try {
									model.delete(bean3);
								} catch (ApplicationException e) {
									e.printStackTrace();
									ServletUtility.handleException(e, request, response);
									return;
								}
								ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
							}
						
						}else{
							ServletUtility.setErrorMessage("Select at least one Record", request);
						}
					}
			    	try {
						list = model.search(bean, pageNo, pageSize);
						
						nextList=model.search(bean,pageNo+1,pageSize);
						
						request.setAttribute("nextlist", nextList.size());
						
						ServletUtility.setBean(bean, request);
					}
					catch(ApplicationException e){	
						ServletUtility.handleException(e, request, response);
						return;
					}
			   if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)) 
			{
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);			
		}

	
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
