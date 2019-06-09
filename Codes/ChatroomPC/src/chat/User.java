package chat;

public class User {
	private String uname="";
	private String pwd="";
	private String sign="";
	private String identity="";
	private String ip="";
	private boolean isOnline = false;
	User() {
		
	}
	User(String uname, String pwd) {
		this.uname = uname;
		this.pwd = pwd;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIP() {
		return ip;
	}
	public void setIP(String iP) {
		this.ip = iP;
	}
	
	public boolean getOnlineState() {
		return isOnline;
	}
	
	public void changeOnlineState() {
		isOnline = !isOnline;
	}
}
