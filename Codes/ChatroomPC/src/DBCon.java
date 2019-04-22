import java.sql.*;

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
			//System.out.println("new "+stmt);
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
	
	public void getUpdate() {
		
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
