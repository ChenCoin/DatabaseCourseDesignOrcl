/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author coin
 */
public class DB {

    public static DBresult query(String args) {
	args = args.substring(0, args.length()-1);
	DBresult ret;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	try {
	    Class.forName("oracle.jdbc.OracleDriver");
	    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "SYSTEM", "Coin1234");
	    //Class.forName("com.mysql.jdbc.Driver");
	    //String url = "jdbc:mysql://localhost:3306/Tiger?characterEncoding=utf8&useSSL=true&useOldAliasMetadataBehavior=true";
	    //Connection conn = DriverManager.getConnection(url, "root", "123");
	    Statement stmt = conn.createStatement();

	    ResultSet rs = stmt.executeQuery(args);
	    ResultSetMetaData md = rs.getMetaData();
	    int columnCount = md.getColumnCount();
	    while (rs.next()) {
		Map<String, Object> rowData = new HashMap<String, Object>();
		for (int i = 1; i <= columnCount; i++) {
		    rowData.put(md.getColumnName(i), rs.getObject(i));
		}
		list.add(rowData);
	    }
	    ret = new DBresult(true, args, "", list);

	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (SQLException se) {
	    ret = new DBresult(false, args, se.getMessage(), null);
	} catch (ClassNotFoundException e) {
	    ret = new DBresult(false, args, "ClassNotFoundException", null);
	}
	return ret;
    }

    public static DBresult execute(String args) {
	args = args.substring(0, args.length()-1);
	DBresult ret;
	try {
	    Class.forName("oracle.jdbc.OracleDriver");
	    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "SYSTEM", "Coin1234");
	    //Class.forName("com.mysql.jdbc.Driver");
	    //String url = "jdbc:mysql://localhost:3306/Tiger?characterEncoding=utf8&useSSL=true";
	    //Connection conn = DriverManager.getConnection(url, "root", "123");
	    Statement stmt = conn.createStatement();

	    int i = stmt.executeUpdate(args);

	    /* 这里的出错信息是 i!=1 时的信息 */
	    ret = new DBresult((i == 1), args, "execute successs, but update nothing", null);

	    stmt.close();
	    conn.close();
	} catch (SQLException se) {
	    ret = new DBresult(false, args, se.getMessage(), null);
	} catch (ClassNotFoundException e) {
	    ret = new DBresult(false, args, "ClassNotFoundException", null);
	}
	return ret;
    }

}
