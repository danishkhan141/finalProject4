package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.FacultyBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.FacultyModel;

public class FacultyModelTest {
	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws ParseException {
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPK();
//		testFindByLogin();
//		testSearch();
		testList();
	}

	private static void testAdd() {
		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");

//			bean.setId(5234L);

			// bean.setId(1L);
			System.out.println("test add....");
			bean.setFirstName("danish ");
			bean.setLastName("khan");
			bean.setGender("Male");
			bean.setEmailId("danishkhan.khan080@gmail.com");
			bean.setMobileNo("7415214365");
			bean.setCollegeId(111L);
			bean.setCollegeName("java");
			bean.setCourseId(111L);
			bean.setCourseName("java");
			bean.setDob(sdf.parse("03-12-1990"));
			bean.setSubjectId(111L);
			bean.setSubjectName("java");
			bean.setCreatedBy("danish");
			bean.setModifiedBy("shivam");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			System.out.println("test add....2");
			long pk = model.add(bean);
			System.out.println("test add....3");
			FacultyBean addedbean = model.findByPK(pk);
			System.out.println("test add....4");
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 3L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			FacultyBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {
		try {
			System.out.println("test.....update line 104");
			FacultyBean bean = model.findByPK(2L);
			System.out.println("test.....update line 106");
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");

			bean.setFirstName("prachi");
			bean.setLastName("pacharne");
			bean.setGender("female");
			bean.setEmailId("danishkhan.khan080@gmail.com");
			bean.setMobileNo("7415214365");
			bean.setCollegeId(121L);
			bean.setCollegeName("java");
			bean.setCourseId(121L);
			bean.setCourseName("java");
			bean.setDob(sdf.parse("03-12-1990"));
			bean.setSubjectId(111L);
			bean.setSubjectName("java");
			bean.setCreatedBy("danish");
			bean.setModifiedBy("shivam");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			System.out.println("test.....update line 124");
			model.update(bean); // not passed any argument because common bean object is made in model
			System.out.println("test.....update line 126");
			/*
			 * FacultyBean updatedbean = model.findByPK(2L); if
			 * (!"abc".equals(updatedbean.getPK()) { System.out.println("Test Update fail");
			 * }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 2L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.print(bean.getId() + "	" + bean.getFirstName());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getDob());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getGender());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

//Tests find a user by login
	/*
	 * public static void testFindByLogin() { try { FacultyBean bean = new
	 * FacultyBean(); bean = model.findByLogin("danishkhan.khan080@gmail.com"); if
	 * (bean == null) { System.out.println("Test Find By Login fail"); }
	 * System.out.println(bean.getId()); System.out.println(bean.getFirstName());
	 * System.out.println(bean.getLastName()); System.out.println(bean.getLogin());
	 * System.out.println(bean.getPassword()); System.out.println(bean.getDob());
	 * System.out.println(bean.getRoleId());
	 * System.out.println(bean.getUnSuccessfulLogin());
	 * System.out.println(bean.getGender());
	 * System.out.println(bean.getLastLoginIP());
	 * System.out.println(bean.getLock()); } catch (ApplicationException e) {
	 * e.printStackTrace(); } }
	 */

	public static void testSearch() {
		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			bean.setFirstName("d");
			list = model.search(bean, 0, 0);

			System.out.println("search");
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.print(bean.getId() + "		");
				System.out.print(bean.getFirstName() + "		");
				System.out.print(bean.getLastName() + "		");
				System.out.print(bean.getCollegeId() + "		");
				System.out.print(bean.getCollegeName() + "		");
				System.out.print(bean.getDob() + "		");
				System.out.print(bean.getCourseId() + "		");
				System.out.print(bean.getCourseName() + "		");
				System.out.print(bean.getGender() + "		");
				System.out.print(bean.getSubjectId() + "		");
				System.out.print(bean.getSubjectName() + "		");
				System.out.print(bean.getMobileNo() + "		");
				System.out.print(bean.getEmailId() + "		");
				System.out.print(bean.getCreatedBy() + "		");
				System.out.print(bean.getModifiedBy() + "		");
				System.out.print(bean.getCreatedDatetime() + "		");
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getDob());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getGender());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmailId());
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
