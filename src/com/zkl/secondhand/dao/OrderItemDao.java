package com.zkl.secondhand.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.zkl.secondhand.model.OrderItem;
import com.zkl.secondhand.utils.ManagerThreadLocal;
import com.zkl.secondhand.utils.c3p0Utils;

public class OrderItemDao {
	/*
	 * 添加订单详情
	 * 
	 */
	
	public void addOrderItems(List<OrderItem> items) throws SQLException {
		String sql="insert into orderitem(order_id,product_id,buynum) values(?,?,?)";
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		for(OrderItem item:items) {
			qr.update(ManagerThreadLocal.getConnection(),sql, item.getOrder().getId(),item.getProduct().getId(),item.getBuyNum());
		}
		
	}
}
