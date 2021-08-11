package com.zkl.secondhand.web.servlet;

import java.io.IOException;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.service.ProductService;

@WebServlet("/addCart")
public class AddCartServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//1.获取id
			String id=request.getParameter("id");
		
			//2.通过id查询数据库商品
		ProductService ps=new ProductService();
		Product p=ps.findProduct(id);
			
			//3.把购买商品放到购物车里
		Map<Product,Integer> cart=(Map<Product, Integer>)request.getSession().getAttribute("cart");
				//3.1先从session获取购物车数据cart
		if(cart==null) {
			cart=new HashMap<Product, Integer>();
			cart.put(p, 1);			
		}
		else {
				//3.2如果没有购物数据，船建一个map对象
			if(cart.containsKey(p)) {
				cart.put(p,cart.get(p)+1 );
			}
			else {
					cart.put(p, 1);
				}
				
			}
		//4.打印购物车数据
		for(Entry<Product, Integer>entry:cart.entrySet()) {
			System.out.println(entry.getKey()+"数量"+entry.getValue());				
		}
		//5.存入session
		request.getSession().setAttribute("cart", cart);
			
		//6.响应客户端面
		//继续购物，擦好看购物车
		String a1="<a href=\"" +request.getContextPath()+"/showProductByPage\">继续购物</a>";
		String a2="&nbsp;<a href=\"" +request.getContextPath()+"/cart.jsp\">查看购物车</a>";
		response.getWriter().write(a1);
		response.getWriter().write(a2);
		}
		
	}
	

