package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Order;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.OrderService;

@WebServlet("/findOrderByUserId")
public class FindOrderByUserIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户id
		User user=(User) request.getSession().getAttribute("user");
		if(user==null) {
			response.getWriter().write("非法访问...");
			return;
		}
		//2.调用service
		OrderService os=new OrderService();
		List<Order> orders= os.findOrdersByUserId(user.getId()+"");
		
		//3.存数据在request
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
	}
	
	
	
	
}
