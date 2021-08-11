package com.zkl.secondhand.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.zkl.secondhand.Exception.UserException;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.UserService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("UTF-8");
		
		//1.获取请求参数
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		//2.调用service
		UserService us=new UserService();
		try {
			
			User user=us.login(username, password);
			//登陆成功，回首页
			//把user保存到session
			request.getSession().setAttribute("user", user);
			if("管理员".equals(user.getRole())) {
				response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
				
			}else {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				//request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//登录失败，
			request.setAttribute("login_msg", e.getMessage());
				//解决重复提交表单问题
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
			//response.sendRedirect(request.getContextPath()+"/login.jsp");
	
			/*
			 * request.getRequestDispatcher()是请求转发，前后页面共享一个request ; 
			response.sendRedirect()是重新定向，前后页面不是一个request。
			 */
		}
		

	}


}		