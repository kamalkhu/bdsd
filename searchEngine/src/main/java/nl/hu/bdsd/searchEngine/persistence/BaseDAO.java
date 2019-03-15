package nl.hu.bdsd.searchEngine.persistence;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class BaseDAO {

	@SuppressWarnings({ "deprecation", "resource" })
	public DB connection() {
		DB db = null;
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			db = mongo.getDB("bdsd");
		} catch (Exception e) {
			System.out.println(e);
		}
		return db;
	}
}