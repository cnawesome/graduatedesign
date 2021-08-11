package com.zkl.secondhand.service;

import java.sql.SQLException;
import java.util.List;

import com.zkl.secondhand.dao.ProductDao;
import com.zkl.secondhand.model.PageResult;
import com.zkl.secondhand.model.Product;

public class ProductService {
	
		ProductDao productDao=new ProductDao();
		
	public PageResult<Product>  findProducts(String category,int page){

		try {		
			//创建模型
			PageResult<Product> pr=new PageResult<>();
		
			//设置总记录数
			long totalCount;
			
			totalCount = productDao.count(category);
			pr.setTotalCount(totalCount);
			
			//设置总页数
			  int totalPage=(int) Math.ceil(totalCount*1.0/pr.getPageSize());
			  pr.setTotalPage(totalPage);
			
			//设置当前页数
			pr.setCurrentPage(page);
			
			//设置数据list
			List<Product> list=productDao.findProducts(category, page, pr.getPageSize());
			pr.setList(list);
			
			return pr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public Product findProduct(String id) {
		
		try {
			
			return  productDao.findProducts(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}


}
