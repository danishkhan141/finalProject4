package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JDBCDataSource;

/**
 * JDBC Implementation of Subject Model
 *
 * @author Danish
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class SubjectModel {

	private static Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * Find next PK of Subject
	 *
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		System.out.println("nextPK method");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a SUBJECT
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */
	public long add(SubjectBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		SubjectBean existbean = findByPK(bean.getId());
		System.out.println(existbean + "SubjectModel add method" + bean.getId());
		if (existbean != null) {
			System.out.println("Model Add if loop");
			throw new DuplicateRecordException("Login Id already exists");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk + "inModelJDBC");
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
			System.out.println("Model Add 1");
			System.out.println("Subject Model add Line No 79.." +pstmt);
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getSubjectName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setLong(4, bean.getCourseId());
			pstmt.setString(5, new CourseModel().findByPK(bean.getCourseId()).getName());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			System.out.println("Model Add 1.5");
			pstmt.executeUpdate();
			conn.commit(); // End Transaction
			pstmt.close();
			System.out.println("Model Add 2");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exceptioon: add rollback exception" + ex.getMessage());
			}
			// throw new ApplicationException("Exception: Exception in add Role");
		} finally {
			System.out.println("Model Add 3");
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		System.out.println("Model Add 4");
		return pk;
	}

	/**
	 * Find Subject by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public SubjectBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		SubjectBean bean = null;
		Connection conn = null;
		System.out.println("find by pk1");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
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
	 * Delete a Subject
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
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
	 * Update a Subject
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		SubjectBean beanExist = findByPK(bean.getId());
		// Check if updated LoginId already Exists
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("LoginId already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET SUBJECT_NAME=?,DESCRIPTION=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getSubjectName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, new CourseModel().findByPK(bean.getCourseId()).getName());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search Subject
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search User with pagination
	 *
	 * @return list : List of Subject
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
			sql.append(" AND id= " + bean.getId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getCourseId() >0) {
				sql.append(" AND COURSE_ID = " +bean.getCourseId());
			}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
			System.out.println("page size" + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		System.out.println("list" + list);
		Connection conn = null;
		try {
			System.out.println("try block");
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search End");
		return list;
	}

	/**
	 * Get List of Subject
	 *
	 * @return list : List of Subject
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Subject with pagination
	 *
	 * @return list : List of Subject
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
