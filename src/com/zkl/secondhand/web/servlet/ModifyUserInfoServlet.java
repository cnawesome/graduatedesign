package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.UserService;

@WebServlet("/modifyUserInfo")
public class ModifyUserInfoServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//1.获取表单参数
		User user =new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			System.out.println(user);
		//2.修改
			UserService us=new UserService();
			us.modifyUserInfo(user);
			
		//3.跳转
			response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
		
	}
}
