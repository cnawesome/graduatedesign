package com.zkl.secondhand.dao;

import java.sql.SQLException;


import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zkl.secondhand.model.Product;
import com.mchange.v2.sql.filter.SynchronizedFilterCallableStatement;
import com.zkl.secondhand.model.PageResult;
import com.zkl.secondhand.utils.c3p0Utils;


public class PorductsDaoImpl {
	int max=1000000;
	public List<Product> findAllProducts() throws SQLException{
		//1.创建queryrunner
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		return qr.query("select * from products", new BeanListHandler<Product>(Product.class));
	}
	
	public void addProduct(Product product)throws SQLException{
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		String sql = "insert into products(id,name,price,pnum,category,description) values (?,?,?,?,?,?)";
	
		Random r = new Random(1);
		int ran1 = r.nextInt(100);
		//定义一个数组参数
		Object[] params = new Object[6];
		//id必须是惟一，UUID-字符串根据当前电脑或者应用的一些数据生成一个惟一字符串

		params[0] =ran1+"";
		params[1] = product.getName();
		params[2] = product.getPrice();
		params[3] = product.getPnum();
		params[4] = product.getCategory();
		params[5] = product.getDescription();
		max=max-1;
		qr.update(sql,params);
	//	qr.update(sql,UUID.randomUUID().toString(), product.getName(),product.getPrice(),product.getPnum(),product.getCategory(),product.getDescription());
	}
	
	/**
	 * 通过id找到书商品
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Product findProductById(String id) throws SQLException{
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		String sql = "select * from products where id=?";
		
		return qr.query(sql, new BeanHandler<Product>(Product.class),id);
		
	}
	
	/**
	 * 更新产品的数据
	 * @throws SQLException 
	 */
	public void updateProduct(Product pro) throws SQLException{
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		String sql = "update products set name = ?, price = ? , pnum = ? , category = ? , description = ? where id = ?";
		
		
		
		qr.update(sql, pro.getName(),pro.getPrice(),pro.getPnum(),pro.getCategory(),pro.getDescription(),pro.getId());
	}

	public void deleteProductById(String id) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		String sql = "delete from products where id = ?";
		
		qr.update(sql,id); 
	}
	
	/**
	 * 使用批处理删除
	 * @param ids
	 * @throws SQLException
	 */
	public void deleteProductByIds(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		//1.拆分ids = 1001,1002,10003
		String[] idArr = ids.split(",");
		
		
		//2.写个sql 
		String sql = "delete from products where id = ?";
	
		//3.封装参数
		//假有3个id
		/**
		 * [[1001,aa],[1002,bb],[1003,cc]]
		 * [[1001],[1002],[1003]]
		 * [1001,1002,1003]
		 */
		Object[][] params = new Object[idArr.length][];
		for(int i = 0;i<idArr.length;i++){
			params[i] = new Object[]{idArr[i]};
		}
		
		//4.执行批处理
		qr.batch(sql, params);
	}
	
	/**
	 * 这种方式执行多次sql语句
	 */
	public void deleteProductByIds1(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		//1.拆分ids = 1001,1002,10003
		String[] idArr = ids.split(",");
		
		/**
		 * 这种方式执行多次sql语句
		 */
		//2.写个sql 
		String sql = "delete from products where id = ?";
		for(String id : idArr){
			qr.update(sql,id);
		}
	}

	public List<Product> findProduct(String id, String name, String category, String minprice, String maxprice) throws SQLException {
		
		//查询
		//1.拼接sql语句
		String sql = "select * from products where 1=1";
		
		if(!"".equals(id)){
			sql += " and id = '" + id +"'";
		}
		
		if(!"".equals(name)){
			sql += " and name like '%" + name +"%'";
		}
		
		if(!"".equals(category)){
			sql += " and category = '" + category +"'";
		}
		
		
		//价格
		if(!"".equals(minprice)){
			sql += " and price >= " + minprice;
		}
		
		if(!"".equals(maxprice)){
			sql += " and price <= " + maxprice;
		}
		
		System.out.println(sql);
		
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}
	
	
	/**
	 * 根据当前页返回数据
	 * @param page 查询的页码
	 * @return
	 * @throws SQLException 
	 */
	public PageResult<Product> findProductsByPage(int page) throws SQLException{
		QueryRunner qr = new QueryRunner(c3p0Utils.getDataSource());
		
		//1.创建PageResult对象
		PageResult<Product> pr = new PageResult<Product>();
		
		//2.设置totalCount【总记录数】
		Long totalCount = (Long) qr.query("select count(*) from products", new ScalarHandler());
		pr.setTotalCount(totalCount);
		
		//3.设置总页数 10/5
		int totalPage = (int)(totalCount % pr.getTotalPage() == 0 ? totalCount / pr.getTotalPage() : totalCount / pr.getTotalPage() + 1);
		pr.setTotalPage(totalPage);
		
		//4.设置 当前页
		pr.setCurrentPage(page);
		
		//5.设置pageresult里的list数据【库存排序】
		String sql = "select * from products order by pnum limit ?,?";
		int start = (page - 1) * pr.getTotalPage();
		List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),start,pr.getTotalPage());
		pr.setList(products);
		
		return pr;
	}



}

