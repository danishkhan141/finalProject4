package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JDBCDataSource;

public class RoleModel {

	private static Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * Find next PK of Role
	 *
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		System.out.println("RoleModel.nextPK() line 27.....");
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");
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
	 * Add a Role
	 *
	 * @param bean
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 * @throws DatabaseException
	 *
	 */
	public long add(RoleBean bean) throws DuplicateRecordException, ApplicationException {
		System.out.println("RoleModel.add(RoleBean) line 58.....");
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		RoleBean duplicataRole = findByName(bean.getName());
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk + "inModelJDBC");
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
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
			throw new ApplicationException("Exception: Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Role
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void delete(RoleBean bean) throws ApplicationException {
		System.out.println("RoleModel.delete(RoleBean) line 105.....");
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin Transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
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
		log.debug("Model add Started");
	}
	/**
	 * Find User by Role
	 *
	 * @param name : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public RoleBean findByName(String name) throws ApplicationException {
		System.out.println("RoleModel.findByName(name) line 138.....");
		log.debug("Model findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE NAME=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting user by emailId ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy EmailId End");
		return bean;
	}
	/**
	 * Find Role by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public RoleBean findByPK(long pk) throws ApplicationException {
		System.out.println("RoleModel.findByPK(pk) line 177.....");
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception.., e");
			throw new ApplicationException("Eception: Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}
	/**
	 * Update a Role
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		System.out.println("RoleModel.update(RoleBean) line 216.....");
		log.debug("Model update Started");
		Connection conn = null;
		RoleBean duplicateRole = findByName(bean.getName());
		// Check if updated Role already Exists
		if (duplicateRole != null && duplicateRole.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception: Delete rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}
	/**
	 * Search Role
	 *
	 * @param bean : Search Parameters
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public List search(RoleBean bean) throws ApplicationException {
		System.out.println("RoleModel.search(RoleBean) line 261.....");
		return search(bean, 0, 0);
	}
	/**
	 * Search Role with pagination
	 *
	 * @return list : List of Roles
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		System.out.println("RoleModel.search(RoleBean, pageNo, pageSize) line 275.....");
		log.debug("Model Search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");
		if (bean != null) {
			
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}
			System.out.println("4");
		}
		// if page size is greater than zero apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
			// sql.append("limit"+pageNo+","+pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			//conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			 throw new ApplicationException("Exception: Exception in search Role "+e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search End");
		return list;
	}
	/**
	 * Get List of Role
	 *
	 * @return list : List of Role
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		System.out.println("RoleModel.list() line 335.....");
		return list(0, 0);
	}
	/**
	 * Get List of Role with pagination
	 *
	 * @return list : List of Role
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		System.out.println("RoleModel.list(pageNo, pageSize) line 348.....");
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_ROLE");
		System.out.println("RoleModel.list(pageNo, pageSize) line 352... Query:" +sql+ "pageNo:" +pageNo+ "pageSize" +pageSize);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
			System.out.println("RoleModel.list(pageNo, pageSize) line 356... Query:" +sql+ "pageNo:" +pageNo+ "pageSize" +pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(" Database Exception....", e);
			// throw new ApplicationException("Exception : Exception Geting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}
}
