package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductServiceImpl;

@WebServlet("/DeleteProductByIdServlet")
public class DeleteProductByIdServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取id
		String id = request.getParameter("id");
		
		//2.查找书
		ProductServiceImpl productService= new ProductServiceImpl();
		productService.deleteProductById(id);
		
		//3.加到list.jsp
		request.setAttribute("books", productService.findAllProducts());
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
	}
}
