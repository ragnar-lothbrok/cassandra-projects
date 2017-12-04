package com.edureka.cassandra.java.client.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.edureka.cassandra.dto.Product;

@Service
public class ProductRepository {

	private static final String TABLE_NAME_V1 = "products_v1";
	
	
	private static final String TABLE_NAME_BRAND = "products_by_brand";
	
	

	@Value("${filepath}")
	private String filePath;

	@PostConstruct
	public void test() {
		// List<Product> productList = FileUtility.readFile(filePath);
		// for (Product product : productList) {
		// insertProduct(product);
		// }
		selectByPogId("666660653641");
		deleteProductByPogId("666660653641");
		selectByBrand("LAKHANI");
	}

	@Autowired
	private Session session;

	public ProductRepository(Session session) {
		this.session = session;
	}
	
	
	/**
	 * Select Product by Brand.
	 * 
	 * @return
	 */
	public Product selectByBrand(String brand) {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BRAND).append(" WHERE brand = '")
				.append(brand).append("';");

		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Product> products = new ArrayList<Product>();

		for (Row r : rs) {
			Product s = new Product(r.getString("productCode"), r.getString("productName"), r.getString("description"),
					r.getInt("quantity"), r.getFloat("price"), r.getFloat("mrp"), r.getString("brand"),
					r.getString("category"), r.getString("sellerCode"), r.getLong("creationTime"));
			products.add(s);
		}

		return products.size() > 0 ? products.get(0) : null;
	}


	/**
	 * Insert a row in the table products.
	 * 
	 * @param product
	 */
	public void insertProduct(Product product) {
		StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME_V1).append(
				"(productName,productCode,description,quantity,price,mrp,category,sellerCode,creationTime,brand) ")
				.append("VALUES ('").append(product.getProductName()).append("', '").append(product.getProductCode())
				.append("', '").append(product.getDescription()).append("',").append(product.getQuantity()).append(",")
				.append(product.getPrice()).append(", ").append(product.getMrp()).append(", '")
				.append(product.getCategory()).append("', '").append(product.getSellerCode()).append("',")
				.append(product.getCreationTimeStamp()).append(",'").append(product.getBrand()).append("');");

		final String query = sb.toString();
		session.execute(query);
	}

	/**
	 * Select Product by id.
	 * 
	 * @return
	 */
	public Product selectByPogId(String pogId) {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_V1).append(" WHERE productCode = '")
				.append(pogId).append("';");

		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Product> products = new ArrayList<Product>();

		for (Row r : rs) {
			Product s = new Product(r.getString("productCode"), r.getString("productName"), r.getString("description"),
					r.getInt("quantity"), r.getFloat("price"), r.getFloat("mrp"), r.getString("brand"),
					r.getString("category"), r.getString("sellerCode"), r.getLong("creationTime"));
			products.add(s);
		}

		return products.size() > 0 ? products.get(0) : null;
	}

	/**
	 * Select all products from products
	 * 
	 * @return
	 */
	public List<Product> selectAll() {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_V1);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Product> products = new ArrayList<Product>();

		for (Row r : rs) {
			Product product = new Product(r.getString("productCode"), r.getString("productName"),
					r.getString("description"), r.getInt("quantity"), r.getFloat("price"), r.getFloat("mrp"),
					r.getString("brand"), r.getString("category"), r.getString("sellerCode"),
					r.getLong("creationTime"));
			products.add(product);
		}
		return products;
	}

	/**
	 * Delete a Product by pogid.
	 */
	public void deleteProductByPogId(String pogId) {
		StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME_V1).append(" WHERE productCode = '")
				.append(pogId).append("';");

		final String query = sb.toString();
		session.execute(query);
	}

}
