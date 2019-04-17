import java.sql.*;

public class DBCon {
	private Connection Con;
	private Statement Stmt;
	private ResultSet rs;
	DBCon() {
		String JDriver = "com.hxtt.sql.access.AccessDriver";
		String ConUrl = "jdbc:Access:///ChatroomDB.accdb";
		try {
			Class.forName(JDriver);
			Con = DriverManager.getConnection(ConUrl);
			Stmt = Con.createStatement();
			System.out.println("new "+Stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement getSt() {
		return Stmt;
	}
	
	public void getUpdate() {
		
	}
	
	public String getSelect(Statement Stmt, String temp) {
		try {
			rs = Stmt.executeQuery(temp);
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
		}
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
			Stmt.close();
			Con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
