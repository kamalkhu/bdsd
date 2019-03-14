package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ArtikelDAO extends BaseDAO {

	DB db = super.connection();

	public void getCollections() {
		//haalt alle collections op in de database
		Set<String> tables = db.getCollectionNames();

		for (String coll : tables) {
			System.out.println(coll);
		}
	}
	
	public void insertStuff() {
		//inserten in de collection 'artikel'
		DBCollection table = db.getCollection("artikel");
		//kies hier je eigen key en value
		BasicDBObject document = new BasicDBObject();
		document.put("key", "value");
		document.put("key", "value");
		document.put("key", "value");
		table.insert(document);
	}
}
