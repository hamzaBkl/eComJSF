package com.example.ecomjsf.dao;

import com.example.ecomjsf.model.Product;

import java.util.List;

public interface ProductDAO {
	void addProduct(Product product);
	void updateProduct(Product product);
	List<Product> listProducts();
	List<Product> selectProductByKeyword(String keyWord);
	List<Product> selectProductByCategory(long idCat);
	List<Product> searchProduct(String keyWord,long idCat);

	Product getProductById(long id);

	void removeProduct(long id);
}
