package com.zkl.secondhand.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.utils.c3p0Utils;

public class UserDao {

	/*
	 * 
	 */
	public void addUser(User user) throws SQLException {
		//1.QueryRunner
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		//2.执行sql语句
		String sql="insert into user";
		sql+="(username,PASSWORD,gender,email,telephone,introduce,activeCode,state,role,registTime)";
		sql+="value(?,?,?,?,?,?,?,?,?,?)";
		//3.参数赋值
		List<Object> list=new ArrayList<Object>();
		list.add(user.getUsername());
		list.add(user.getPassword());
		list.add(user.getGender());
		list.add(user.getEmail());
		list.add(user.getTelephone());
		list.add(user.getIntroduce());
		list.add(user.getActiveCode());
		list.add(user.getState());
		list.add(user.getRole());
		list.add(user.getRegistTime());
		//4.执行sql
		qr.update(sql, list.toArray());
		System.out.println(user);
	}
	
	public User findUserByActiveCode(String activeCode) throws SQLException {
		//1.QueryRunner
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		
		//2.sql
		String sql="select *from user where activeCode=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),activeCode);
		
	}
	
	public void updateState(String activeCode) throws SQLException{
		//1.QueryRunner
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		
		//2.sql
		String sql="update user set state=1 where activeCode=?";
		
		qr.update(sql, activeCode);
		
		
	}
	
	public User findUserByUsernameAndPassword(String username,String password) throws SQLException {
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql="select * from user where username=? and PASSWORD=?";
		return qr.query(sql, new BeanHandler<User>(User.class),username,password);
		
		
		
	}
	public User findUserById(String id) throws SQLException {
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql="select * from user where id=?";
		return qr.query(sql, new BeanHandler<User>(User.class),id);
		
		
		
	}
	/*
	 * 更改用户信息
	 */
	public void updateUser(User user) throws SQLException{
		//1.QueryRunner
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		
		//2.sql
		String sql="update user set password=?,gender=?,telephone=? where id=?";
		
		qr.update(sql, user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
		
		
	}

	public List<User> findUserAll() {
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		try {
			return  qr.query("select * from user", new BeanListHandler<User>(User.class) );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}