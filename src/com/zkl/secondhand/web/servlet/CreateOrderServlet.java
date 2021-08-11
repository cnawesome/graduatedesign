package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.model.Order;
import com.zkl.secondhand.model.OrderItem;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.OrderService;

@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	//获取session的user
	User user=(User) request.getSession().getAttribute("user");
	if(user==null) {
		response.getWriter().write("非法访问");
		return;
		
	}
	//取购物车
	
	/*
	 *etAttribute表示从request范围取得设置的属性，
	 *必须要先setAttribute设置属性，才能通过getAttribute来取得，
	 *设置与取得的为Object对象类型 。getParameter表示接收参数。
	 */
	
	Map<Product,Integer> cart=(Map<Product, Integer>)request.getSession().getAttribute("cart");
	if(cart==null||cart.size()==0) {
		response.getWriter().write("购物车没有商品");
		return;
	}
	//1.把数据封装好
	Order order=new Order();
	try {
		//1.1把请求封装成order
		BeanUtils.populate(order,request.getParameterMap());
		
		//1.2补全order数据
		order.setId(UUID.randomUUID().toString());
		order.setOrderTime(new Date());
		order.setUser(user);
		
		//1.3封装订单详情order
		List<OrderItem> items=new ArrayList<>();
		//取购物车
		double totalPrice=0;
		for(Entry<Product,Integer> entry:cart.entrySet()) {
			OrderItem item=new OrderItem();
			//设置购物数量
			item.setBuyNum(entry.getValue());
			//设置商品
			item.setProduct(entry.getKey());
			//设置订单
			item.setOrder(order);
			
			totalPrice+=entry.getKey().getPrice()*entry.getValue();
			
			items.add(item);
		}
		//设置order中items
		order.setItems(items);
		
		//1.4设置总价格
		order.setmoney(totalPrice);
		
		//print
		System.out.println("---------------");
		System.out.println("订单： ");
		System.out.println(order);
		System.out.println("订单中的商品： ");
		for(OrderItem item : items) {
		
			System.out.println("商品名称： "+item.getProduct().getName()+"数量： "+item.getBuyNum() );
		
			
		//2.插入数据库	
		OrderService os=new OrderService();
		os.createOrder(order);
		
		//3.订单成功，移除购物车已买商品
		request.getSession().removeAttribute("cart");
		
		//4.响应
		response.getWriter().write("下单成功...");
		//response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
		
		}
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}

}
