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

@WebServlet("/active")
public class activeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("UTF-8");
		//1.获取参数
		String activecode=request.getParameter("activeCode");
		
		//2.激活
		UserService us=new UserService();
		try {
			us.activeUser(activecode);
			response.getWriter().write("激活成功");
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
	}
}
