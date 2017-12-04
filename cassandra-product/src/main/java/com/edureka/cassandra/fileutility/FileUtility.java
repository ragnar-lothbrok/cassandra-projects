package com.edureka.cassandra.fileutility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.cassandra.dto.Product;

public class FileUtility {

	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	public static List<Product> readFile(String filePath) {
		List<Product> productList = new ArrayList<Product>();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String sCurrentLine;
			sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String split[] = sCurrentLine.split(",");
				Product product = new Product(split[0], split[0], split[2], Integer.parseInt(split[3]),
						Float.parseFloat(split[4]), Float.parseFloat(split[5]), split[6], split[7], split[8],
						Long.parseLong(split[9]));
				productList.add(product);
			}
		} catch (IOException e) {
			logger.error("Exception occured while reading file = {} ", e);
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {

				logger.error("Exception occured while releasing file resources = {} ", ex);

			}
		}
		return productList;
	}
}
