import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import com.mysql.jdbc.ResultSetMetaData;

public class PhConnector {
	public static void main(String[] args) {



		new Thread(new Runnable() {
			public void run() {
				while(true){
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(
							"Cannot find the driver in the classpath!", e);
				}

				String url = "jdbc:mysql://localhost:3306/anlop1";
				String username = "";
				String password = "";
				Connection connection = null;
				try {
					connection = DriverManager.getConnection(url, username,
							password);
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					Statement stmt = (Statement) connection.createStatement();
				
					
					stmt.executeUpdate("DELETE FROM product WHERE name = 'videokaart' OR name = 'harde schijf'");
					stmt.executeUpdate("INSERT INTO product VALUES ('videokaart', 100)");
					stmt.executeQuery("START TRANSACTION");
					Thread.sleep(3000);
					
					stmt.executeUpdate("INSERT INTO product VALUES ('harde schijf', 80)");
					System.out.println("B: Insert harde schijf");
					connection.commit();
					int queue = (int) (Math.random() * 11);
					System.out.println(Thread.currentThread().getName() + ": Slaap " +
					queue + " sec");
					 // Slaap wachtTijd seconden
					 Thread.sleep(queue * 1000);
				} catch (SQLException e) {
					throw new RuntimeException("Cannot connect the database!",
							e);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
		}, "Thread 1").start();

		new Thread(new Runnable() {
			public void run() {
				while(true){
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(
							"Cannot find the driver in the classpath!", e);
				}

				String url = "jdbc:mysql://localhost:3306/anlop1";
				String username = "sean";
				String password = "boeing707";
				Connection connection = null;
				try {
					connection = DriverManager.getConnection(url, username,
							password);
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					Statement stmt = (Statement) connection.createStatement();
					ResultSet r = null;
					Thread.sleep(1000);
					stmt.executeQuery("START TRANSACTION");
					
					r = stmt.executeQuery("SELECT * FROM product");
					ResultSetMetaData rsmd = (ResultSetMetaData) r.getMetaData();
					int columnsNumber = rsmd.getColumnCount();
					while (r.next()) {
					    for (int i = 1; i <= columnsNumber; i++) {
					        if (i > 1) System.out.print(",  ");
					        String columnValue = r.getString(i);
					        System.out.print("A:" + columnValue + " " + rsmd.getColumnName(i));
					    }
					    System.out.println("");
					}

					
					
					Thread.sleep(6000);
					r = stmt.executeQuery("SELECT * FROM product");
					ResultSetMetaData rsmdb = (ResultSetMetaData) r.getMetaData();
					int columnsNumberb = rsmdb.getColumnCount();
					while (r.next()) {
					    for (int i = 1; i <= columnsNumberb; i++) {
					        if (i > 1) System.out.print(",  ");
					        String columnValue = r.getString(i);
					        System.out.print("A:" + columnValue + " " + rsmdb.getColumnName(i));
					    }
					    System.out.println("");
					}
					Thread.sleep(4000);
					connection.commit();
					int queue = (int) (Math.random() * 11);
					System.out.println(Thread.currentThread().getName() + ": Slaap " +
					queue + " sec");
					 // Slaap wachtTijd seconden
					 Thread.sleep(queue * 1000);
				} catch (SQLException e) {
					throw new RuntimeException("Cannot connect the database!",
							e);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
		}, "Thread 2").start();
	}
}

