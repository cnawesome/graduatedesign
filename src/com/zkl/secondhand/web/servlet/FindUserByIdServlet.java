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

@WebServlet("/findUserById")
public class FindUserByIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//1.获取参数
			String userid=request.getParameter("id");
			//2.从数据库查询
			UserService us=new UserService();
			try {
			User user=us.findUserById(userid);
				/*
				 * el表达式取数据顺序：page,request,session,application
				 */
			//3.放在request
				request.setAttribute("u", user);
				
			//4.回到modifiyuserinfo.jsp,显示数据
				request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
				
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write(e.getMessage());
			}
	}
}
