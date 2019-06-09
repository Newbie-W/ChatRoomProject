package chat;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
	List<User> userlist;
	
	public UserManager(){
		userlist = new LinkedList<User>();
		userlist.add(new User("test","abc123"));
		userlist.add(new User("guest","abcde"));
		userlist.add(new User("sa","sa"));
	}
	
	public int login(String username,String password){
		Iterator<User> iter = userlist.iterator();//µÃµ½Ã¶¾ÙÆ÷
		while (iter.hasNext()){
			User user = iter.next();
			if (user.username.equals(username)){
				if (user.password.equals(password))return 0;
				else return 1;
			}
		}
		return 2;
	}
}
