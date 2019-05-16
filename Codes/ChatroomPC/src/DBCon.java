import java.sql.*;

import javax.swing.JOptionPane;

public class DBCon {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private boolean isStart = false;
	DBCon() {
		String JDriver = "com.hxtt.sql.access.AccessDriver";
		String conUrl = "jdbc:Access:///ChatroomDB.accdb";
		try {
			Class.forName(JDriver);
			con = DriverManager.getConnection(conUrl);
			stmt = con.createStatement();
			isStart = true;
			System.out.println("connect state:"+isStart);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement getSt() {
		return stmt;
	}
	
	public boolean isConStart() {
		return isStart;
	}
	
	public void add(String tableName, String attribute1, String attribute2, String attribute3) {
		String a1 = "'" + attribute1 + "'";
		String a2 = "'" + attribute2 + "'";
		String a3 = "'" + attribute3 + "'";
		String test = "select * from " + tableName + " where 用户名=" + a1;
		
		try {
			rs = stmt.executeQuery(test);
			while (rs.next()) {
				if (rs != null) {
					JOptionPane.showMessageDialog(null, "注册失败，已有同名用户");
					return ;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String temp = "insert into "+tableName+" values( "+a1+" , "+a2+" , "+a3+" , ' ' )";
		System.out.println(temp);
		try {
			stmt.executeUpdate(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//closeSt();
	}
	
	public void delete(String tableName, String attribute1) {
		String a1Name = "用户名 ";
		//String a2Name = "密码";
		//String a3Name = "身份";
		String a1 = a1Name + " = '" + attribute1 + "'";
		//String a1 = "'" + attribute1 + "'";
		//String a2 = "'" + attribute2 + "'";
		//String temp = "delete from "+tableName+" where 用户名 = "+a1;
		String temp = "delete from "+tableName+" where "+a1;
		try {
			stmt.executeUpdate(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//closeSt();
	}
	
	public void change(String tableName, String attribute1, String attribute2, String attribute3) {
		/*String a1 = "'" + attribute1 + "'";
		String a2 = "'" + attribute2 + "'";
		String a3 = "'" + attribute3 + "'";
		String temp = "update UserInfo where 用户名 = "+ a1;
		if (!isA2Null && !isA3Null) {
			temp += "set 密码 = "+ a2 +"and 身份 = "+ a3;
		} else if (!isA2Null && isA3Null) {
			temp += "set 密码 = "+ a2;
		} else if ( isA2Null && !isA3Null) {
			temp += "set 身份 = "+ a3;
		} else if (isA2Null && isA3Null) {
			System.out.println("信息均为空，默认不改动");
		} else {
			System.out.println("DB的错误else");
		}
		*/
		String a1Name = "用户名 ";
		String a2Name = "密码";
		String a3Name = "身份";
		String a1 = a1Name + " = '" + attribute1 + "'";
		String a2 = a2Name + " = '" + attribute2 + "'";
		String a3 = a3Name + " = '" + attribute3 + "'";
		String temp = "update UserInfo ";
		boolean isA2Null = "".equals(a2);
		boolean isA3Null = "".equals(a3);
		if (!(isA2Null) && !(isA3Null)) {
			temp += "set "+ a2 +" and "+ a3;
			
		} else if (!isA2Null && isA3Null) {
			temp += "set "+ a2;
		} else if ( isA2Null && !isA3Null) {
			temp += "set "+ a3;
		} else if (isA2Null && isA3Null) {
			System.out.println("信息均为空，默认不改动");
		}
		temp = temp + " where "+ a1;
		//System.out.println(temp+"000"+stmt);
		try {
			stmt.executeUpdate(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String search(String tableName, String attribute1, String attribute2, String attribute3) {
		String a1Name = "用户名 ";
		String a2Name = "密码";
		String a3Name = "身份";
		String a1 = a1Name + " = '" + attribute1 + "'";
		String a2 = a2Name + " = '" + attribute2 + "'";
		String a3 = a3Name + " = '" + attribute3 + "'";
		String temp = "select * from UserInfo ";
		boolean isA1Null = "".equals(attribute1);
		boolean isA2Null = "".equals(attribute2);
		boolean isA3Null = "".equals(attribute3);
		String temString = "", resultString = "";
		if ( !isA1Null ) {
			temp = temp + "where " + a1;
			if ( !isA2Null )
				temp = temp + " and " + a2;
			if ( !isA3Null )
				temp = temp + " and " + a3;
			
		}
		else {
			if ( !isA2Null ) {
				temp = temp + "where " + a1;
				if ( !isA3Null )
					temp = temp + " and " + a2;
			}
			else if ( !isA3Null )
				temp = temp + "where " + a3;
		}
		try {
			rs = stmt.executeQuery(temp);
			while (rs.next()) {
				for (int i = 1; i <= 4; i++) {
					temString = rs.getString(i) + (i==4?"\n":"\t");
					resultString += temString;
				}
			}
			return resultString;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getUpdate(Statement stmt, String temp) {
		try {
			stmt.executeUpdate(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getSelect(Statement stmt, String temp) {
		try {
			rs = stmt.executeQuery(temp);
			String s = new String();
			String s1 ;
			s1 = "";
			//System.out.println("==== " + s1 + "  ====");
			while (rs.next()) {
				//System.out.println("++++");
				for (int i=1; i<3; i++) {
					s = rs.getString(i) + "\t";
					s1 += s;
				}
				s = rs.getString(3) + "\n";
				s1 += s;
			}
			//System.out.println("----- " + s1 + " ----");
			return s1;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} /*finally {
			closeSt();
		}*/
	}
	
	public String getInfo(Statement Stmt, String temp) {
		String result;
		
		return null;
	}
	
	public int searchId(String name) {	//备用，用于实现输入用户名能够实时显示头像
		int i = 0;
		
		return i;
	}
	
	public void closeSt() {
		try {
			rs.close();
			stmt.close();
			con.close();
			isStart = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
