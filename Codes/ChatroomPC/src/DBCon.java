import java.sql.*;

public class DBCon {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	DBCon() {
		String JDriver = "com.hxtt.sql.access.AccessDriver";
		String conUrl = "jdbc:Access:///ChatroomDB.accdb";
		try {
			Class.forName(JDriver);
			con = DriverManager.getConnection(conUrl);
			stmt = con.createStatement();
			System.out.println("new "+stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement getSt() {
		return stmt;
	}
	
	public void getUpdate() {
		
	}
	
	public String getSelect(Statement stmt, String temp) {
		try {
			rs = stmt.executeQuery(temp);
			String s = new String();
			String s1 ;//= new String()(���Ӵ˾䣬�Ҳ����丳ֵ���򱨴�����Ϊδ��ʼ��)
			s1 = "";	//Ϊ�μ�����һ��ſ�����return���б��¼ʧ�ܣ�
			while (rs.next()) {
				for (int i=1; i<3; i++) {
					s = rs.getString(i) + "\t";
					s1 += s;
				}
				s = rs.getString(3) + "\n";
				s1 += s;
			}
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
	
	public int searchId(String name) {	//���ã�����ʵ�������û����ܹ�ʵʱ��ʾͷ��
		int i = 0;
		
		return i;
	}
	
	public void closeSt() {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
