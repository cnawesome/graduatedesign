package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductServiceImpl;



@WebServlet("/ProductListServlet2")
public class ProductkListServlet2 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//调用业务方法
		request.setCharacterEncoding("utf-8");
		
		ProductServiceImpl productService = new ProductServiceImpl();
		List<Product> products = productService.findAllProducts();
		
		//2.把数据放在请求对象中
		request.setAttribute("products", products);
		
		//3.进入admin/products/list.jsp
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		
	}

	

}
