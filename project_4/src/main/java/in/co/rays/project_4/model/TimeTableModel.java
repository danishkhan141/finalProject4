package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.JDBCDataSource;

/**
 * JDBC Implementation of TimeTableModel
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class TimeTableModel {
	private static Logger log = Logger.getLogger(TimeTableModel.class);

	/**
	 * Find next PK of TimeTable
	 *
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception: Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a TimeTable
	 *
	 * @param bean
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 * @throws DatabaseException
	 *
	 */
	public long add(TimeTableBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CourseModel cmodel=null;
		CourseBean cbean=null;
		SubjectModel smodel=null;
		SubjectBean sbean=null;
		TimeTableBean existbean = findByPK(bean.getId());
		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}
		
		if(bean.getCourseId()>0) {
			cmodel=new CourseModel();
			cbean=cmodel.findByPK(bean.getCourseId());
		}
		
		if(bean.getSubjectId()>0) {
			smodel=new SubjectModel();
			sbean=smodel.findByPK(bean.getSubjectId());
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCourseId());

			pstmt.setString(3, cbean.getName());
			pstmt.setLong(4, bean.getSubjectId());
			pstmt.setString(5, sbean.getSubjectName());
			pstmt.setString(6, bean.getSemester());
			pstmt.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(8, bean.getExamTime());
			pstmt.setString(9, bean.getDescription());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());

			pstmt.executeUpdate();
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
			// throw new ApplicationException("Exception: Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Find TimeTable by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
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
	 * Delete a TimeTable
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
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
			throw new ApplicationException("Exception: Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Update a TimeTable
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		TimeTableBean beanExist = findByPK(bean.getId());
		// Check if updated LoginId already Exists
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("LoginId already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getCourseId());
			pstmt.setString(2, new CourseModel().findByPK(bean.getCourseId()).getName());
			pstmt.setLong(3, bean.getSubjectId());
			pstmt.setString(4, new SubjectModel().findByPK(bean.getSubjectId()).getSubjectName());
			pstmt.setString(5, bean.getSemester());
			pstmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(7, bean.getExamTime());
			pstmt.setString(8, bean.getDescription());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.setLong(13, bean.getId());
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
	 * Search TimeTable
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(TimeTableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search TimeTable with pagination
	 *
	 * @return list : List of TimeTable
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("AND id=" + bean.getId());
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID=" + bean.getSubjectId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID=" + bean.getCourseId());
			}
			System.out.println("TimeTableModel Search before exam time");
			if (bean.getExamDate() != null && bean.getExamDate().getTime()>0 ){
				System.out.println("TimeTableModel Search wwwww exam time");

				Date d= new Date(bean.getExamDate().getTime());
				String s = DataUtility.getDateStringSearch(d);
				sql.append(" AND EXAM_DATE = '" + s + "' ");
				//sql.append(" AND EXAM_DATE = " + s);
				System.out.println("TimeTableModel Search after exam time");
			}
//			if (bean.getSubjectId() > 0) {
//				sql.append("AND SUBJECT_ID='" + bean.getSubjectId());
//			}
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
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				System.out.println("TimetableModel.search() courseId :" +bean.getCourseId());
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting User by id");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search End");
		return list;
	}

	/**
	 * Get List of TimeTable
	 *
	 * @return list : List of TimeTable
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of TimeTable with pagination
	 *
	 * @return list : List of TimeTables
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
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
				TimeTableBean bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
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
