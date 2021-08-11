package com.zkl.secondhand.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.User;

@WebServlet("/settleAccount")
public class SettleAccountServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断当前浏览器是否登陆
		User user=(User) request.getSession().getAttribute("user");
		
		//如果登陆，进行订单处理页面
		if(user!=null){
			response.sendRedirect(request.getContextPath()+"/order.jsp");
		}
		//如果没有登录，先登录
		else {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			
		}
	}
	
}
