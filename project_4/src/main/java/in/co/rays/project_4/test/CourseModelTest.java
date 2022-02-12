package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CourseModel;;


public class CourseModelTest {

	public static CourseModel model = new CourseModel();

	public static void main(String[] args) throws ParseException {
//			testAdd();
//			testDelete();
//			testUpdate();
//			testFindByPK();
			testSearch();
//			testList();

	}

	
	private static void testAdd() {
		try {
			CourseBean bean = new CourseBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYY");
//				bean.setId(5234L);

			// bean.setId(1L);
			System.out.println("Test Add 1");
			bean.setName("danish123 ");
			bean.setDescription("khan");
			bean.setDuration("3 years");
			bean.setCreatedBy("danish123");
			bean.setModifiedBy("shivam1234");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			System.out.println("Test Add 2");
			long pk = model.add(bean);
			System.out.println("Test Add 3");
			CourseBean addedbean = model.findByPK(pk);
			System.out.println(addedbean + "Test add succ.." + pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 9L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			CourseBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			CourseBean bean = model.findByPK(6L);
			bean.setName("Sachin");
			bean.setDescription("Cricketer");
			bean.setDuration("12 years 7 months");
			model.update(bean);
//				UserBean updatedbean = model.findByPK(2L);
//				if (!"abc".equals(updatedbean.getLogin())) {
//					System.out.println("Test Update fail");	
//			}
			System.out.println("test update successfull....");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			System.out.println("test findbypk");
			CourseBean bean = new CourseBean();
			long pk = 10L;
			bean = model.findByPK(pk);

			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getValue());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			System.out.println("search" + list);
			list = model.search(bean, 0, 0);
			// bean.setName("xyz");
			System.out.println("search" + list);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());
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
