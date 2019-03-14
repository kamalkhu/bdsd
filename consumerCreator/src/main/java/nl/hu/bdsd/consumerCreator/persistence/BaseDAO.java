package nl.hu.bdsd.consumerCreator.persistence;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@SuppressWarnings("deprecation")
public class BaseDAO {

	@SuppressWarnings("resource")
	public static DB connection() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("bdsd");
		return db;
	}
}
