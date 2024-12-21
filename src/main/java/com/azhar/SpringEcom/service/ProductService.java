package com.azhar.SpringEcom.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azhar.SpringEcom.model.Product;
import com.azhar.SpringEcom.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public List<Product> getAllProducts() {

		List<Product> all = this.productRepo.findAll();
		return all;
	}

	public Product getProductById(int id) {

		Optional<Product> byId = this.productRepo.findById(id);
		Product product = byId.get();
		return product;
	}

	public Product addProduct(Product product, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		product.setImageName(file.getOriginalFilename());
		product.setImageType(file.getContentType());
		product.setImageDate(file.getBytes());
		
		Product save = this.productRepo.save(product);
		return save;
	}
	
	public Product updateProduct(Product product, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		product.setImageName(file.getOriginalFilename());
		product.setImageType(file.getContentType());
		product.setImageDate(file.getBytes());
		
		Product save = this.productRepo.save(product);
		return save;
	}

	public void deleteProduct(int id) {
		
		this.productRepo.deleteById(id);
		
	}

	public List<Product> searchProduct(String keyword) {
		
		List<Product> searchProducts = this.productRepo.searchProducts(keyword);
		return searchProducts;
	}
	
	

}
