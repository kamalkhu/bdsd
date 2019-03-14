package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;

import com.google.gson.JsonObject;
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
	
	public boolean insertArticle(Document article) {
		//inserten in de collection 'artikel'
		DBCollection table = db.getCollection("artikel");
		//kies hier je eigen key en value
		BasicDBObject newRow = new BasicDBObject();
		JsonObject articleJson = article.toJson();
		newRow.put("title", articleJson.get("title"));
		newRow.put("source", articleJson.get("source"));
		newRow.put("text", articleJson.get("text"));
		newRow.put("location", articleJson.get("location"));
		newRow.put("date", articleJson.get("date"));
		newRow.put("date", articleJson.get("keywords"));
		try{
			table.insert(newRow);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
