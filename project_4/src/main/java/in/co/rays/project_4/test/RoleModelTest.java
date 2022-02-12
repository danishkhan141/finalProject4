package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.RoleModel;


public class RoleModelTest {

	public static RoleModel model = new RoleModel();

	public static void main(String[] args) {
	//	 testAdd();
//		 testDelete();
//		 testUpdate();
//		 testFindByPK();
//		 testFindByName();
		 testSearch();
// testList();
	}

	public static void testAdd() {
		try {
			RoleBean bean = new RoleBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			// bean.setId(1L);
			bean.setName("DANISH");
			bean.setDescription("E");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	// Tests delete a Role
	public static void testDelete() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 5L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Deleted...");
			RoleBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		try {
			RoleBean bean = model.findByPK(4L);
			bean.setName("Danish");
			bean.setDescription("Ejjjjng");
			model.update(bean);
//					RoleBean updatedbean=model.findByPK(1L);
//					if(!"12".equals(updatedbean.getName())) {
//						System.out.println("Test update fail");
//					}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	public static void testFindByPK() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 4L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find by pk failed");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testFindByName() {
		try {
			RoleBean bean = new RoleBean();
			bean = model.findByName("rinku");
			if (bean == null) {
				System.out.println("Test find by PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testSearch() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			System.out.println("s" +list);
			list = model.search(bean, 1, 10);
			System.out.println("s1" +list);
			//bean.setId(1);
			if (list.size() < 0) {
				System.out.println("s2" +list.size());
				System.out.println("Test search fail");
			}
			System.out.println("sa" +list.size());
			Iterator it = list.iterator();
			System.out.println("sb" +list.size());
			while (it.hasNext()) {
				System.out.println("sc" +list.size());
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testList() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
