package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductServiceImpl;

@WebServlet("/SearchProductServlet")
public class SearchProductServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//设置请求的编码类型来解决post请求的乱码问题
		request.setCharacterEncoding("utf-8");
		
		//接收参数
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		
		//调用service 【根据条件查询书】
		ProductServiceImpl productServiceImplService = new ProductServiceImpl();
		List<Product> products = productServiceImplService.findBook(id,name,category,minprice,maxprice);
		
		//返回结果给list.jsp
		request.setAttribute("products", products);
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		
	}
}
