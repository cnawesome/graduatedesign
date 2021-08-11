package com.zkl.secondhand.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mysql.cj.Query;
import com.zkl.secondhand.model.Order;
import com.zkl.secondhand.model.OrderItem;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.utils.ManagerThreadLocal;
import com.zkl.secondhand.utils.c3p0Utils;

public class OrderDao {
/*
 * 添加一个订单
 */
	public void add(Order order) throws SQLException {
		//1.sql
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		
		//2.parament
		List<Object> params=new ArrayList<>();
		params.add(order.getId());
		params.add(order.getmoney());
		params.add(order.getReceiverAddress());
		params.add(order.getReceiverName());
		params.add(order.getReceiverPhone());
		params.add(order.getpaystate());
		params.add(order.getordertime());
		params.add(order.getUser().getId());
		
		//3.execute
	//	QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
	//	qr.update(sql,params.toArray());
		
		QueryRunner qr=new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(),sql,params.toArray());
		
		
	}
	/*
	 * 通过用 户id查找其他的所有订单
	 */
	public List<Order> findOderByUserId(String userid) throws SQLException{
		String sql="select * from orders where user_id=?";
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class),userid);
		
	}
	
	/*
	 * 通过订单id查询order表
	 */
	public Order findOderByOrderId(String orderid) throws SQLException{
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		
		//1.查询orders表，封装到order对象
		String sql="select * from orders where id=?";
		Order order=qr.query(sql, new BeanHandler<Order>(Order.class),orderid);
		return order;
		
		//2.查询orderItem表，s数据封装到order的item属性
		
	}
	
	public Order findOrderByOrderId(String orderId) throws SQLException {
		//1.查询roder表，把数据封装到order对象
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql1="select *from orders where id=?";
		Order order=qr.query(sql1, new BeanHandler<Order>(Order.class));
		
		//2.查询orderItem表，把数据封装到Order的items属性
		String sql2="select o.*,p.name,p.price from orderitem o,products p where o.product";
		List<OrderItem> mItems=qr.query(sql2, new ResultSetHandler<List<OrderItem>>(){

			@Override
			 public List<OrderItem> handle(ResultSet rs) throws SQLException {
				// 创建集合对象
				List<OrderItem> items=new ArrayList<OrderItem>();
				//2遍历
				while(rs.next()) {
					//创建order对象
					OrderItem item=new OrderItem();
					item.setBuyNum(rs.getInt("buyNum"));;
					//创建product对象
					Product p=new Product();
					p.setId(rs.getInt("product_id"));
					p.setName(rs.getString("name"));
					p.setPrice(rs.getDouble("price"));
					
					//吧product放入item
					item.setProduct(p);
					
					//把item放入items
					items.add(item);
				}
				return items;
			}
			
		},orderId);
		
		order.setItems(mItems);
		return order;
		
		
	}
	
	public List<Order> findOrderByAdmin(String name) throws SQLException{
		String sql="select * from orders where receiverName=?";
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class),name);
		
	}
	public List<Order> findOrderByAdmin() {
			QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
			try {
				return qr.query("select * from orders ", new BeanListHandler<Order>(Order.class));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
}
