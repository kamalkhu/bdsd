package nl.hu.bdsd.consumerCreator.persistence;

import java.util.Set;

public class ArtikelDAO extends BaseDAO {

	public void main(String[] args) {
		try {
			Set<String> tables = super.connection().getCollectionNames();
			for (String coll : tables) {
				System.out.println(coll);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
