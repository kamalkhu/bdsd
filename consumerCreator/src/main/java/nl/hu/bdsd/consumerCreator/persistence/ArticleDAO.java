package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ArticleDAO extends BaseDAO {

	DB db = super.connection();

	public void getCollections() {
		Set<String> tables = db.getCollectionNames();

		for (String coll : tables) {
			System.out.println(coll);
		}
	}

	public void insertArticle(Document document) {
		DBCollection collection = db.getCollection("article");
		BasicDBObject dbObject = new BasicDBObject("_id", document.get_id())
			.append("title", document.getTitle())
			.append("description", document.getText())
			.append("location", document.getLocation())
			.append("date", document.getDate())
			.append("source", document.getSource())
			.append("key_words", document.getTopKeywords(10))
			.append("parties", document.getParties());
		collection.insert(dbObject);
	}
}
