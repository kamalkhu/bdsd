package nl.hu.bdsd.searchEngine.persistence;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;

public class ArticleDAO extends BaseDAO {

	DB db = super.connection();

	public DBCursor selectArticle(String keyword) {
		BasicDBObject fields = new BasicDBObject().append("title", 1);
		BasicDBObject query = new BasicDBObject().append("description", 1);
		DBCursor results = db.getCollection("article").find(query, fields);
		return results;
	}
}