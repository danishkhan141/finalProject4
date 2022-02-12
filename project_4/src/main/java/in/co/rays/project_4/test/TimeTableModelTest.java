package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.TimeTableModel;

public class TimeTableModelTest {

	public static TimeTableModel model = new TimeTableModel();

	public static void main(String[] args) throws ParseException {
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPK();
		testSearch();
//		testList();

	}

	private static void testAdd() {
		try {
			TimeTableBean bean = new TimeTableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
//			bean.setId(5234L);

			// bean.setId(1L);
			bean.setCourseId(1L);
			bean.setCourseName("khan");
			bean.setSubjectId(1L);
			bean.setSubjectName("java");
			bean.setSemester("first");
			bean.setExamDate(sdf.parse("30-12-1990"));
			bean.setExamTime("3 hrs");
			bean.setDescription("1234");
			bean.setCreatedBy("danish");
			bean.setModifiedBy("shivam");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			TimeTableBean addedbean = model.findByPK(pk);
			System.out.println("Test add succ..");
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			TimeTableBean bean = new TimeTableBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			TimeTableBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {
		try {
			TimeTableBean bean = model.findByPK(2L);
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");

			bean.setCourseId(11L);
			bean.setCourseName("corporate");
			bean.setSubjectId(11L);
			bean.setSubjectName("python");
			bean.setSemester("second");
			bean.setExamDate(sdf.parse("30-12-1990"));
			bean.setExamTime("3 hrs");
			bean.setDescription("1234");
			bean.setCreatedBy("danish");
			bean.setModifiedBy("shivam");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
//			UserBean updatedbean = model.findByPK(2L);
//			if (!"abc".equals(updatedbean.getLogin())) {
//				System.out.println("Test Update fail");	
//		}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			TimeTableBean bean = new TimeTableBean();
			long pk = 1L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				bean.setExamDate(sdf.parse("11-09-2023"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list = model.search(bean, 0, 0);
			// bean.setExamDate(Date d= new Date(bean.getExamDate().getTime()));
			System.out.println("search");
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getSemester());
				System.out.println(bean.getExamDate());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getSemester());
				System.out.println(bean.getExamDate());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
