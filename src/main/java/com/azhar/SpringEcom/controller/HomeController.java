package com.azhar.SpringEcom.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.azhar.SpringEcom.model.Product;
import com.azhar.SpringEcom.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin // this will allow for any kind of port
public class HomeController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {

		ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(this.productService.getAllProducts(),
				HttpStatus.ACCEPTED);
		return responseEntity;
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id) {

		Product product = this.productService.getProductById(id);

		if (product.getId() > 0) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(product, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// setting the images
	@GetMapping("product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {

		Product product = this.productService.getProductById(productId);
		return new ResponseEntity<>(product.getImageDate(), HttpStatus.OK);

	}

	// creating a new product
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {

		Product saveProduct;
		try {
			saveProduct = this.productService.addProduct(product, imageFile);
			return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
			@RequestPart MultipartFile imageFile) {

		Product updateProduct;
		try {
			this.productService.updateProduct(product, imageFile);
			return new ResponseEntity<>("Updated", HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/prodcut/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {

		Product product = this.productService.getProductById(id);
		if (product != null) {
			this.productService.deleteProduct(id);
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {

		List<Product> products = this.productService.searchProduct(keyword);
		System.out.println("searching " + keyword);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
