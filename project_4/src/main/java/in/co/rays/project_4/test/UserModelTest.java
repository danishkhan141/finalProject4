package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.UserModel;

public class UserModelTest {
	public static UserModel model = new UserModel();

	public static void main(String[] args) throws ParseException {
		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPK();
//		testFindByLogin();
//		testSearch();
//		testGetRoles();
//		testList();
//		testAuthenticate();
//		testRegisterUser();
//	testChangePassword();
//		testForgetPassword();
//		testResetPassword();
	}

	private static void testAdd() {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYY");
//			bean.setId(5234L);

			// bean.setId(1L);
			bean.setFirstName("danish ");
			bean.setLastName("khan");
			bean.setLogin("danishkhan.khan080@gmail.com");
			bean.setPassword("pass1234");
			bean.setDob(sdf.parse("30-12-1990"));
			bean.setMobileNo("7415214365");
			bean.setRoleId(111L);
			bean.setUnSuccessfulLogin(22);
			bean.setGender("Male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setRegisteredIP("223412234");
			bean.setLastLoginIP("23445544");
			bean.setCreatedBy("danish");
			bean.setModifiedBy("shivam");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			UserBean addedbean = model.findByPK(pk);
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
			UserBean bean = new UserBean();
			long pk = 9L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			UserBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			UserBean bean = model.findByPK(8L);
			bean.setFirstName("cmc1 limited");
			bean.setLastName("pvt ltd2");
			bean.setLogin("abc121@gmail2.com");
			bean.setPassword("1234561");
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
			UserBean bean = new UserBean();
			long pk = 12L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLoginIP());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByLogin() {
		try {
			UserBean bean = new UserBean();
			bean = model.findByLogin("danishkhan.khan080@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By Login fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLoginIP());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testGetRoles() {
		try {
			UserBean bean = new UserBean();
			bean.setRoleId(122L);
			List list = new ArrayList();
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("Test Find By PK fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLoginIP());
				System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.search(bean, 0, 0);
			bean.setFirstName("xyz");
			System.out.println("search");
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLoginIP());
				System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLoginIP());
				System.out.println(bean.getLock());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testAuthenticate() {
		try {
			UserBean bean = new UserBean();
			bean.setLogin("abc121@gmail.com");
			bean.setPassword("1629");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully Login");
			} else {
				System.out.println("Invalid login Id & password");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testRegisterUser() throws ParseException {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setId(7L);
			bean.setFirstName("danish");
			bean.setLastName("khan1");
			bean.setLogin("khan@gmail.cm");
			bean.setPassword("pass12134");
			bean.setDob(sdf.parse("31/12/1989"));
			bean.setRoleId(3);
			long pk = model.registerUser(bean);
			System.out.println("Successfully Register");
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getDob());
			UserBean registerbean = model.findByPK(pk);
			if (registerbean != null) {
				System.out.println("Test registration fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testChangePassword() {
		try {
			UserBean bean = model.findByLogin("danishkhan.khan080@gmail.com");
			String oldPassword = bean.getPassword();
			System.out.println("usertest line317 " + bean.getPassword());
			bean.setId(12);
			bean.setPassword("88");
			bean.setConfirmPassword("88");
			String newPassword = bean.getPassword();
			try {
				model.changePassword(12L, oldPassword, newPassword);
				System.out.println("password has been changed successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testForgetPassword() {
		try {
			boolean b = model.forgetPassword("abc121@gmail.com");
			System.out.println("Success: Test Forget Password Success");
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testResetPassword() {
		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("abc121@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
