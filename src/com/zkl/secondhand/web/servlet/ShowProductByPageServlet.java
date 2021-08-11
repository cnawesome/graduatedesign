package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zkl.secondhand.model.PageResult;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductService;

@WebServlet("/showProductByPage")
public class ShowProductByPageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String category=request.getParameter("category");
		String pageStr=request.getParameter("page");
		int page=1;
		if(pageStr!=null && !"".equals(pageStr)) {
			//把字符串转化为int
			page=Integer.parseInt(pageStr);
		}
		
		//2.调用service
		ProductService ps=new ProductService();
		PageResult<Product> pageResult=ps.findProducts(category, page);
		
		//3.存入request
		
		request.setAttribute("pageResult", pageResult);
		//request.getSession().setAttribute("pageResult", pageResult);
		
		request.setAttribute("category", category);
		//request.getSession().setAttribute("pageResult", pageResult);
		
		//4.跳转页面
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}
	
}
