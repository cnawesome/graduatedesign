package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductService;

@WebServlet("/changeNum")
public class ChangeNumServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//1.获取参数
			String id=request.getParameter("id");
			String num=request.getParameter("num");
			
			//2.更新购物车数据
				//2.1通过id查找商品
			ProductService ps=new ProductService();
			Product p=ps.findProduct(id);
				//2.2 通过商品更新session数据
			@SuppressWarnings("unchecked")
			Map<Product, Integer> cart=(Map<Product, Integer>) request.getSession().getAttribute("cart");
			
			//
			if(cart.containsKey(p)) {
				if("0".equals(num)) {
					cart.remove(p);
				}
				else {
					cart.put(p, Integer.parseInt(num));
					
				}
			}
			
			//重新保存到session
			request.getSession().setAttribute("cart", cart);
			
			//回到购物车页面
			response.sendRedirect(request.getContextPath()+"/cart.jsp" );
		}
}
