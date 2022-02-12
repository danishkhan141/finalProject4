package in.co.rays.project_4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CollegeModel;


public class CollegeModelTest {
	
	 
    public static CollegeModel model = new CollegeModel();

    
    public static void main(String[] args) throws DuplicateRecordException {
         testAdd();
//         testDelete();
//         testUpdate();
//         testFindByName();
//         testFindByPK();
//         testSearch();
//           testList();

    }

    
    public static void testAdd() throws DuplicateRecordException {

        try {
            CollegeBean bean = new CollegeBean();
            // bean.setId(2L);
            bean.setName("jsp6");
            bean.setAddress("borawan6");
            bean.setState("mp6");
            bean.setCity("indore6");
            bean.setPhoneNo("073124233");
            bean.setCreatedBy("Admin1");
            bean.setModifiedBy("Admin1");
            bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
            bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
            long pk = model.add(bean);
            System.out.println("Test add succ");
            CollegeBean addedBean = model.findByPK(pk);
            if (addedBean == null) {
                System.out.println("Test add fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    
    public static void testDelete() {

        try {
            CollegeBean bean = new CollegeBean();
            long pk = 3L;
            bean.setId(pk);
            model.delete(bean);
            System.out.println("Test Delete succ");
            CollegeBean deletedBean = model.findByPK(pk);
            if (deletedBean != null) {
                System.out.println("Test Delete fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

   
    public static void testUpdate() {

        try {
            CollegeBean bean = model.findByPK(1L);
            bean.setName("jsp7");
            bean.setAddress("vv2");
            model.update(bean);
            System.out.println("Test Update succ");
			/*
			 * CollegeBean updateBean = model.findByPK(1L); if
			 * (!"jsp6".equals(updateBean.getName())) {
			 * System.out.println("Test Update fail"); }
			 */
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests find a College by Name.
     */

    public static void testFindByName() {

        try {
            CollegeBean bean = model.findByName("jsp");
            if (bean == null) {
                System.out.println("Test Find By Name fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhoneNo());
            System.out.println(bean.getCreatedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    
    public static void testFindByPK() {
        try {
            CollegeBean bean = new CollegeBean();
            long pk = 1L;
            bean = model.findByPK(pk);
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhoneNo());
            System.out.println(bean.getCreatedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getModifiedDatetime());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    

    public static void testSearch() {
        try {
            CollegeBean bean = new CollegeBean();
            List list = new ArrayList();
            bean.setName("jsp");
            // bean.setAddress("borawan");
            list = model.search(bean, 1, 10);
            if (list.size() < 0) {
                System.out.println("Test Search fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (CollegeBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getAddress());
                System.out.println(bean.getState());
                System.out.println(bean.getCity());
                System.out.println(bean.getPhoneNo());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getCreatedDatetime());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getModifiedDatetime());
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

   
    public static void testList() {

        try {
            CollegeBean bean = new CollegeBean();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (CollegeBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getAddress());
                System.out.println(bean.getState());
                System.out.println(bean.getCity());
                System.out.println(bean.getPhoneNo());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getCreatedDatetime());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getModifiedDatetime());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }


}
