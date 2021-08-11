package com.zkl.secondhand.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.ManagedBean;

import com.zkl.secondhand.dao.OrderDao;
import com.zkl.secondhand.dao.OrderItemDao;
import com.zkl.secondhand.dao.ProductDao;
import com.zkl.secondhand.model.Order;
import com.zkl.secondhand.model.OrderItem;
import com.zkl.secondhand.utils.ManagerThreadLocal;

public class OrderService {
/*	public void createOrder(Order order,List<OrderItem> items) {
		
	}
*/

	/*
	 * 添加订单业务方法
	 */
	private OrderDao orderDao=new OrderDao();
	private OrderItemDao orderItemDao=new OrderItemDao();
	private ProductDao productDao=new ProductDao();
	public void createOrder(Order order) {
		
		try {
	//开启事务
			//1.插入订单表
			ManagerThreadLocal.beginTransaction();
			orderDao.add(order);
			
		//2.插入订单详情表
			orderItemDao.addOrderItems(order.getItems());
			
		//3.减库存
			for(OrderItem item:order.getItems()) {
				productDao.updatePnum(item.getProduct().getId(),item.getBuyNum());
			}
			
	//结束事务		
			ManagerThreadLocal.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//事务回滚
		ManagerThreadLocal.rollbackTransaction();
	}
		
	public List<Order> findOrdersByUserId(String userid){
		try {
			return orderDao.findOderByUserId(userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<Order> findOrdersByAdmin(String name ){
		try {
			return orderDao.findOrderByAdmin(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public List<Order> findOrdersByAdmin() {
		// TODO Auto-generated method stub
		return orderDao.findOrderByAdmin();
	}
	
}
