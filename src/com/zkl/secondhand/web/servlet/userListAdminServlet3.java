package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.Exception.UserException;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.ProductServiceImpl;
import com.zkl.secondhand.service.UserService;



@WebServlet("/userListAdminServlet3")
public class userListAdminServlet3 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//调用业务方法
		request.setCharacterEncoding("utf-8");
		
		UserService us=new UserService();
		List<User>	users = us.findUserAll();
			//2.把数据放在请求对象中
			request.setAttribute("users", users);
		
		//3.进入admin/products/list.jsp
		request.getRequestDispatcher("/admin/products/userlist2.jsp").forward(request, response);
		
	}

	

}
