package cn.jdbc;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: DbconnManager
 * @Description: ���Ӷ�����
 * @author XiMing.Fu
 * @date 2014-3-10 ����02:00:55
 * 
 */
public class DbconnManager {

	private static final Log LOG = LogFactory.getLog(DbconnManager.class);

	private static final String datacent = "jdbc/xzsp";

	private static DbconnManager instance = null;

	protected DbconnManager() {
	}

	public static DbconnManager getInstance() {
		if (instance == null) {
			synchronized (DbconnManager.class) {
				if (instance == null) {
					instance = new DbconnManager();
				}
			}
		}
		return instance;
	}

	/**
	 * ��ȡ������������
	 * 
	 * @return
	 * @throws SQLException
	 */

	public Connection getConnection(String dsName) throws SQLException {
		return this.getPoolManConnection(dsName);
	}

	/**
	 * ��ȡJNDI����
	 * 
	 * @param jndi
	 * @return
	 * @throws SQLException
	 */
	public Connection getJndiConnection(String jndi) throws SQLException {
		Connection conn = null;
		try {
			InitialContext context = new InitialContext();
			Context envContext = (Context) context.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup(datacent);
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			LOG.error(e);
		}
		return conn;
	}

	/**
	 * ��poolman��ȡ���Ӷ���
	 * 
	 * @return Connection
	 * @throws SQLException
	 */

	public Connection getPoolManConnection(String dsName) throws SQLException {

		Connection conn = null;
		try {
			// conn = DBUtil.getConection(dsName);
		} catch (Exception e) {
			LOG.error("�ڵ��ص㣺��poolman��ȡ���Ӷ����쳣��" + e.getMessage());
		}
		return conn;
	}

	/**
	 * ��ȡ���������ݿ�����
	 * 
	 * @return Connection
	 */
	public Connection getTransactionConnection(String dsName)
			throws SQLException {
		Connection conn = this.getPoolManConnection(dsName);
		conn.setAutoCommit(false);
		return conn;
	}

	/**
	 * �ر����������ݿ�����
	 * 
	 * @return Connection
	 */
	public static void closeTransactionConnection(Connection conn,
			boolean abortTransaction) {
		// �ж�����Դ�Ƿ����
		if (conn != null) {
			try {
				DatabaseMetaData metaData = conn.getMetaData();
				if (metaData.supportsTransactions()) {
					// �ع�/�ύ����
					if (abortTransaction) {
						conn.commit();
					} else {
						conn.rollback();
					}
					conn.setAutoCommit(true);
				}
				conn.close();
				conn = null;
			} catch (SQLException e) {
				LOG.error(e);
			}
		}
	}

	/**
	 * �رշ����������ݿ�����
	 * 
	 * @return Connection
	 */
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	/**
	 * ����ع�
	 * 
	 * @return Connection
	 */
	public static void rollback(Connection con) {
		try {
			if (con != null) {
				con.rollback();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	/**
	 * �ر��������
	 * 
	 * @return Connection
	 */
	public static void closePreparedStatement(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	/**
	 * �ر��������
	 * 
	 * @return Connection
	 */
	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	/**
	 * �رղ�ѯ�����
	 * 
	 * @return Connection
	 */
	public static void closeResultSet(ResultSet rst) {
		try {
			if (rst != null) {
				rst.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	/**
	 * �ر����ж�����ͨ�����ݿ����ӣ�
	 * 
	 * @return Connection
	 */
	public static void closeResource(Connection conn, PreparedStatement pstmt,
			ResultSet rst) {
		closeResultSet(rst);
		closePreparedStatement(pstmt);
		closeConnection(conn);
	}

	/**
	 * �ر����ж�����ͨ�����ݿ����ӣ�
	 * 
	 * @return Connection
	 */
	public static void closeResource(Connection conn, PreparedStatement pstmt) {
		closePreparedStatement(pstmt);
		closeConnection(conn);
	}

	/**
	 * �ر����ж��󣨴���������ݿ����ӣ�
	 * 
	 * @return Connection
	 */
	public static void closeResource(Connection conn, PreparedStatement pstmt,
			boolean bool) {
		closePreparedStatement(pstmt);
		closeTransactionConnection(conn, bool);
	}

	/**
	 * �ر�JTA��������
	 * 
	 * @return UserTransaction
	 */
//	public static void closeUserTransaction(UserTransaction tx, boolean bool) {
//		try {
//			if (tx != null) {
//				if (bool) {
//					tx.rollback();
//				} else {
//					tx.commit();
//				}
//			}
//		} catch (Exception e) {
//			LOG.error(e);
//		}
//	}

	/**
	 * �رմ洢�������
	 * 
	 * @param callableStatement
	 *            CallableStatement
	 */
	public static void closeCallableStatement(
			CallableStatement callableStatement) {
		try {
			if (callableStatement != null) {
				callableStatement.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	final static String sDBDriver = "oracle.jdbc.driver.OracleDriver";

	final static String sConnStr = "jdbc:oracle:thin:@172.16.168.134:1521:gwcs";

	/**
	 * ͨ��thin��ʽ���Oracle���ݿ������.
	 */
	private Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(sDBDriver);
			conn = DriverManager.getConnection(sConnStr, "xzsp", "xzsp");
		} catch (Exception e) {
			LOG.error(e);
		}
		return conn;
	}

}
