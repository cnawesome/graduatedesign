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

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//设置请求的编码类型来解决post请求的乱码问题
		request.setCharacterEncoding("utf-8");
		
		//1.把表单的数据封装成模型
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());//获取网页的字段
			System.out.println(product);
			
			//2.更新数据
			ProductServiceImpl productService = new ProductServiceImpl();
			productService.updateProduct(product);
			
			//3.回到list页面
			List<Product> products = productService.findAllProducts();
			request.setAttribute("products", product);
			request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
