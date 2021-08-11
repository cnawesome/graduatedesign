package com.zkl.secondhand.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.utils.ManagerThreadLocal;
import com.zkl.secondhand.utils.c3p0Utils;

public class ProductDao {
	/*
	 * 计算总记录数
	 * @param category
	 * @return
	 */
	public long count(String category) throws SQLException {
		//1.定义记录数
		long count=0;
		
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		
		String sql="select count(*) from products where 1=1";
		if(category !=null && !"".equals(category)) {
			sql+=" and category = ?";
			count=(long)qr.query(sql, new ScalarHandler(),category);
		}
		else {
			count=(long)qr.query(sql, new ScalarHandler());
		}
		return count;
		
	}
	
	/*
	 * @param category 类型
	 * @param page 当前页
	 * @return
	 */
	public List<Product> findProducts(String category,int page,int pageSize) throws SQLException{
		
		//拼接sql和参数
		String sql="select * from products where 1=1";
		
		List<Object> params=new ArrayList<Object>();
		if(category !=null && !"".equals(category)) {
			sql+=" and category = ?";
			params.add(category);
		}
		int start=(page-1)*pageSize;
		int length=pageSize;
		
		sql+=" limit ?,?";
		params.add(start);
		params.add(length);
		
		//执行
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class),params.toArray());
					
		
	}
	
	/*
	 * 通过id查找商品
	 */
	public Product findProducts(String id) throws SQLException{
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql="select * from products where id=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class),id);
	}
	//更新库存
	
	//productid 商品id
	
	public void updatePnum(int productid,int num) throws SQLException {
	//	QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql="update products set pnum=pnum-? where id =?";
	//	qr.update(sql,num,productid);
		QueryRunner qr=new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(), sql,num,productid);
		
	}  
	
	
	public Product findProducts() throws SQLException{
		QueryRunner qr=new QueryRunner(c3p0Utils.getDataSource());
		String sql="select * from products ";
		return qr.query(sql, new BeanHandler<Product>(Product.class));
	}
/*
 *测试 

	public static void main(String[] args) throws SQLException {
		ProductDao dao=new ProductDao();
		String category="计算机";
		long count=dao.count(category);
		List<Product> product=dao.findProducts(category, 2, 4);
		for(Product b: product) {		
			System.out.println(b);
		}
		System.out.println(count);
		
	}
	
*/


}
