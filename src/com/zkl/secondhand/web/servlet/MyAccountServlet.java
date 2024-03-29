package com.zkl.secondhand.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.User;

@WebServlet("/myaccount")
public class MyAccountServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			User user=(User) request.getSession().getAttribute("user");
			if(user!=null) {
				//如果登录进入myaccount.jsp
				response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
				
			}
			else {
				//如果未登录，进入login.jsp
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				
				
			}
	}
	
}
