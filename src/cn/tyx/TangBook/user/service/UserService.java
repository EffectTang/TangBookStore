package cn.tyx.TangBook.user.service;

import cn.tyx.TangBook.user.dao.UserDao;
import cn.tyx.TangBook.user.domain.User;

public class UserService {
	private UserDao userdao=new UserDao();
	
	public void regist(User form) throws UserException
	{
		User user=userdao.findByUsername(form.getUsername());
		if(user!=null)
			throw new UserException("用户名已经被注册");
	
		user=userdao.findByUsername(form.getEmail());
		if(user!=null)
			throw new UserException("邮箱已经被注册");
		
		userdao.addUser(form);
	}

	public void active(String code) {
		System.out.println("激活");
		
	}
	
	public User login(User form) throws UserException
	{
		User user =userdao.findByUsername(form.getUsername());
		if(user==null) throw new UserException("对不起 没找到此账号");
		if(!user.getPassword().equals(form.getPassword())) 
			throw new UserException("对不起 你的密码 有错");
		return user;
	}
}
