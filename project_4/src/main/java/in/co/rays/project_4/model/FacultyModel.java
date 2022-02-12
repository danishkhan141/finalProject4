package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.FacultyBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JDBCDataSource;

/**
 * JDBC Implementation of Faculty Model
 *
 * @author Danish
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class FacultyModel {
	
	private static Logger log = Logger.getLogger(FacultyModel.class);
	
	Connection conn = null;
	long pk = 0;

	/**
	 * Find next PK of Faculty
	 *
	 * @throws DatabaseException
	 */
	public Long nextPK() throws DatabaseException {
		log.debug("Faculty Model Next PK Started....");
		System.out.println("nextPK started");
		try {
			System.out.println("try....");
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new DatabaseException("Exception in Getting nextPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model NextPK Ended....");
		return pk + 1;
	}

	/**
	 * Add a Faculty
	 *
	 * @param bean
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 * @throws DatabaseException
	 *
	 */
	public long add(FacultyBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model add Started");
		FacultyBean existbean = findByPK(bean.getId());
		if (existbean != null) {
			 throw new DuplicateRecordException("Login Id already exists");
		}
		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			
			System.out.println(pk + "inModelJDBC");
			
			conn.setAutoCommit(false); // Begin Transaction
			System.out.println("test add....try");
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("test add....pstmt");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getEmailId());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, new CollegeModel().findByPK(bean.getCollegeId()).getName());
			pstmt.setLong(9, bean.getCourseId());
			pstmt.setString(10, new CourseModel().findByPK(bean.getCourseId()).getName());
			System.out.println("test add..before date");
			pstmt.setDate(11, new java.sql.Date(bean.getDob().getTime()));
			System.out.println("test add..after date");

			pstmt.setLong(12, bean.getSubjectId());
			pstmt.setString(13, new SubjectModel().findByPK(bean.getSubjectId()).getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			System.out.println("execute update....");
			conn.commit(); // End Transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exceptioon: add rollback exception" + ex.getMessage());
			}
			// throw new ApplicationException("Exception: Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/*
	 * public FacultyBean findByLogin(String login) throws ApplicationException {
	 * log.debug("Model findByLogin Started"); StringBuffer sql = new
	 * StringBuffer("SELECT * FROM ST_FACULTY WHERE LOGIN=?");
	 * System.out.println("sql " + sql); try { conn =
	 * JDBCDataSource.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql.toString()); pstmt.setString(1, login); ResultSet
	 * rs = pstmt.executeQuery(); while (rs.next()) { bean = new FacultyBean();
	 * bean.setId(rs.getLong(1)); bean.setFirstName(rs.getString(2));
	 * bean.setLastName(rs.getString(3)); bean.setLogin(rs.getString(4));
	 * bean.setPassword(rs.getString(5)); bean.setDob(rs.getDate(6));
	 * bean.setMobileNo(rs.getString(7)); bean.setRoleId(rs.getLong(8));
	 * bean.setUnSuccessfulLogin(rs.getInt(9)); bean.setGender(rs.getString(10));
	 * bean.setLastLogin(rs.getTimestamp(11)); bean.setLock(rs.getString(12));
	 * bean.setRegisteredIP(rs.getString(13));
	 * bean.setLastLoginIP(rs.getString(14)); bean.setCreatedBy(rs.getString(15));
	 * bean.setModifiedBy(rs.getString(16));
	 * bean.setCreatedDatetime(rs.getTimestamp(17));
	 * bean.setModifiedDatetime(rs.getTimestamp(18)); } rs.close(); } catch
	 * (Exception e) { e.printStackTrace(); log.error("Database Exception..", e);
	 * throw new
	 * ApplicationException("Exception: Exception in getting User by login"); }
	 * finally { JDBCDataSource.closeConnection(conn); }
	 * log.debug("Model findByLogin End"); return bean; }
	 */
	/**
	 * Find Faculty by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public FacultyBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Delete a Faculty
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End Transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback exception" + ex.getMessage());
			}
			// throw new ApplicationException("Exception: Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Update a Faculty
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		FacultyBean beanExist = findByPK(bean.getId());
		System.out.println("model.....update line 234");
		// Check if updated LoginId already Exists
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("LoginId already exists");
		}
		try {
			System.out.println("model.....update line 240");
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET FIRST_NAME=?,LAST_NAME=?,GENDER=?,EMAIL_ID=?,MOBILE_NUMBER=?,COLLEGE_ID=?,COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?,DOB=?,SUBJECT_ID=?,SUBJECT_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getGender());
			pstmt.setString(4, bean.getEmailId());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setLong(6, bean.getCollegeId());
			pstmt.setString(7, new CollegeModel().findByPK(bean.getCollegeId()).getName());
			pstmt.setLong(8, bean.getCourseId());
			pstmt.setString(9, new CourseModel().findByPK(bean.getCourseId()).getName());
			pstmt.setDate(10, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(11, bean.getSubjectId());
			pstmt.setString(12, new SubjectModel().findByPK(bean.getSubjectId()).getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());
			pstmt.executeUpdate();
			System.out.println("model.....update line 263");
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception: Update rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search Faculty
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Started");
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("AND id=" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID=" + bean.getCourseId() );
			}
			
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB like " + bean.getGender() + "%");
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
			}
			
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql);
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search End");
		return list;
	}

	/**
	 * Get List of Faculty
	 *
	 * @return list : List of Faculty
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmailId(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting Faculty list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
