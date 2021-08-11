package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductServiceImpl;

@WebServlet("/uploadProduct")
public class uploadProduct extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		Product product=new Product();
		try {
			BeanUtils.populate(product, req.getParameterMap());
			ProductServiceImpl productServiceImp=new ProductServiceImpl();
			productServiceImp.addProduct(product);
			
			List<Product> products=productServiceImp.findAllProducts();
			req.setAttribute("product", product);
			
			System.out.println(product);
			
			req.getRequestDispatcher("uploadSuccess.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
}