package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.model.Order;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.OrderService;
import com.zkl.secondhand.service.ProductServiceImpl;

@WebServlet("/SearchOrderServlet")
public class SearchOrderServlet2 extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//设置请求的编码类型来解决post请求的乱码问题
		request.setCharacterEncoding("utf-8");
		
		//接收参数
		String orderid = request.getParameter("id");
		String name = request.getParameter("receiverName");
		
		
		//调用service 【根据条件查询书】
		OrderService orderService= new OrderService();
		List<Order> orders = orderService.findOrdersByAdmin(name);
		
		//返回结果给list.jsp
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/admin/orders/list.jsp").forward(request, response);
		
	}
}
