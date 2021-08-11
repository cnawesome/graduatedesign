package com.zkl.secondhand.service;

import java.sql.SQLException;

import java.util.List;

import com.zkl.secondhand.dao.PorductsDaoImpl;
import com.zkl.secondhand.dao.ProductDao;
import com.zkl.secondhand.model.Product;
import com.zkl.secondhand.model.PageResult;

public class ProductServiceImpl {
	PorductsDaoImpl productsDao = new PorductsDaoImpl();
	public List<Product> findAllProducts(){
		
		//1.创建dao对象
		//BookDaoImpl bookDao = new BookDaoImpl();
		
		//2.调用findAllBook的方法
		try {
			return  productsDao.findAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void addProduct(Product pro){
		
		try {
			productsDao.addProduct(pro);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  Product findProductById(String id){
		try {
			return productsDao.findProductById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void updateProduct(Product product){
		try {
			productsDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	public void deleteProductById(String id) {
		
		try {
			productsDao.deleteProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void deleteProductByIds(String ids) {
		
		try {
			productsDao.deleteProductByIds(ids);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<Product> findBook(String id, String name, String category, String minprice, String maxprice) {
		try {
			return productsDao.findProduct(id, name, category, minprice, maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return null;
	}


	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public PageResult<Product> findProductsByPage(int page){
		try {
			return productsDao.findProductsByPage(page);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
