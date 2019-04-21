
public class User {
	private String uname;
	private String pwd;
	private String sign;
	private String identity;
	private String ip;
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
		uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		pwd = pwd;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		sign = sign;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		identity = identity;
	}
	public String getIP() {
		return ip;
	}
	public void setIP(String iP) {
		ip = iP;
	}
}
