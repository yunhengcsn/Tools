package tools.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * src加配置c3p0-config.xml文件
 * @author csn
 */
public class JdbcUtils {

	// 数据库连接池（饿汉式单例模式）
	private static DataSource ds = new ComboPooledDataSource();

	//事务用Connection
    // 开启事务时：从连接池获取事务并set
    // 开启事务后：同一个事务的方法共享该conn
    // 结束事务后：null
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    /**
     * 获取数据库连接池
     */
	public static DataSource getDataSource() {
		return ds;
	}

    /**
     * 供dao使用以获取连接
     */
	public static Connection getConnection() throws SQLException {
        //获取当前线程的事务连接
		Connection con = tl.get();
        //con不为null-->有事务，则返回当前事务的con
		if(con != null) return con;
        //con为null-->没有事务，则通过连接池返回新连接
		return ds.getConnection();
	}
	
	/**
	 * 开启事务
	 */
	public static void beginTransaction() throws SQLException {
        //获取当前线程的事务连接
		Connection con = tl.get();
		if(con != null) throw new SQLException("已经开启了事务，不能重复开启！");
		//给con赋值，取消自动提交，开启事务
		con = ds.getConnection();
		con.setAutoCommit(false);
        //把当前事务连接放到tl中
		tl.set(con);
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction() throws SQLException {
        //获取当前线程的事务连接
		Connection con = tl.get();
		if(con == null) throw new SQLException("没有事务不能提交！");
		con.commit();//提交事务
		con.close();//关闭连接
		tl.remove();
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction() throws SQLException {
        //获取当前线程的事务连接
		Connection con = tl.get();
		if(con == null) throw new SQLException("没有事务不能回滚！");
		con.rollback();//回滚事务
		con.close();//关闭连接
		tl.remove();
	}
	
	/**
	 * 释放Connection
	 */
	public static void releaseConnection(Connection connection) throws SQLException {
		Connection con = tl.get();//获取当前线程的事务连接
        //参数连接与当前事务连接不同，则关闭
		if(connection != con) {
		    //参数连接不为null且未被关闭，则关闭
			if(connection != null &&!connection.isClosed()) {
				connection.close();
			}
		}
	}
}

