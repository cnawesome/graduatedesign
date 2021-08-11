package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductService;

@WebServlet("/productinfo")
public class ProductInfoServlet extends HttpServlet{
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//1.获取id
				String id=request.getParameter("id");
				
				//2.通过id获取数据库商品
				ProductService ps=new ProductService();
				Product product=ps.findProduct(id);
				//3.把商品存入request，跳转product——info.jsp
				request.setAttribute("product", product);
				
				//request.getSession().setAttribute("product", product);
				
				request.getRequestDispatcher("/product_info.jsp").forward(request, response);
				
		}
}
