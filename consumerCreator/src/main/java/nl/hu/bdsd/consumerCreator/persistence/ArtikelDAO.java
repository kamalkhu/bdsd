package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;
import com.mongodb.DB;

public class ArtikelDAO extends BaseDAO{

	public void getStuff() {
		
		DB db = super.Connection();
		Set<String> tables = db.getCollectionNames();
		
		for(String coll : tables){
			System.out.println(coll);
		}
	}
}
