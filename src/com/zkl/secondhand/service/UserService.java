package com.zkl.secondhand.service;

import java.sql.SQLException;
import java.util.List;

import com.zkl.secondhand.dao.UserDao;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.utils.SendJMail;
import com.zkl.secondhand.Exception.UserException;

public class UserService {
		//
		UserDao userDao=new UserDao();
		public void regiser(User user)throws UserException {
			try {
				userDao.addUser(user);
				/*
				 * 项目发布时，连接改为域名www.xxx.com
				 */
				String link="http://localhost:8080/se/active?activecode="+user.getActiveCode();
				String html="<a href=\" "+link+ "\">欢迎您注册闲乐享二手跳蚤市场网站，请点击激活</a>";
				SendJMail.sendMail("z3505523939@163", html);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				UserException ue=new UserException("注册失败");
				throw ue;
			}
		}
		
		/*
		 * 激活用户
		 */
		public void activeUser(String activeCode) throws UserException {
			try {
				//1.查询激活
				User user=userDao.findUserByActiveCode(activeCode);
				if(user==null) {
					throw new UserException("非法激活，用户不存在");
				}
				if(user!=null&&user.getState()==1) {
					throw new UserException("用户已激活");
					
				}
				//2.激活用户
				userDao.updateState(activeCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	
		public  User login(String username,String password) throws UserException{

				try {
					//1.查询
					User user = userDao.findUserByUsernameAndPassword(username, password);
					//2.判断
				
					if(user==null) {
						 throw new UserException("用户名或者密码不正确");
						
					}
					
					
					
					return user;	
					
					} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException("登录失败");
		}
					
	}
		
		public  User findUserById(String id) throws UserException{
			//1.查询
		
				try {
			User user = userDao.findUserById(id);
		
			//2.判断
			if(user==null) {
				throw new UserException("用户名不存在");
				
			}
			return user;
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new UserException("未知错误");
				}

		}
		
	public  void modifyUserInfo(User user){
					try {
						userDao.updateUser(user);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			
}

		

	public List<User> findUserAll(){
		return userDao.findUserAll();
		}


}
		
		
			

		
		



