package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;
import com.mongodb.DB;
import com.mongodb.MongoClient;

@SuppressWarnings("deprecation")
public class BaseDAO {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("bdsd");

		Set<String> tables = db.getCollectionNames();

		for (String coll : tables) {
			System.out.println(coll);
		}
	}
}
