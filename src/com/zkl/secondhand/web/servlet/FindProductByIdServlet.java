package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductServiceImpl;

@WebServlet("/FindProductByIdServlet")
public class FindProductByIdServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取id
		String id = request.getParameter("id");
		
		//2.查找书
		ProductServiceImpl productService= new ProductServiceImpl();
		Product product = productService.findProductById(id);
		
		//3.放在请求对象
		request.setAttribute("product", product);
		request.getRequestDispatcher("/admin/products/edit.jsp").forward(request, response);
	}
}
