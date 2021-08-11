package com.zkl.secondhand.web.servlet;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.model.PageResult;
import com.zkl.secondhand.service.ProductServiceImpl;



@WebServlet("/ProductListServlet")
public class ProductListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		//BookListServlet?page=3
		
		//1.获取页码
		String page = request.getParameter("page");
		//如果没有传值就显示第1页的数据
		if(page == null || "".equals(page)){
			page = "1";
		}
		
		
		
		//调用业务方法
		ProductServiceImpl productService = new ProductServiceImpl();
		PageResult<Product> pageResult = productService.findProductsByPage(Integer.parseInt(page));
		
		//2.把数据放在请求对象中
		request.setAttribute("pageResult", pageResult);
		
		//3.进入admin/products/list.jsp
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		
	}

	

}
