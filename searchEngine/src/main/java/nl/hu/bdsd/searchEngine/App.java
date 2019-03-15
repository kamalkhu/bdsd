package nl.hu.bdsd.searchEngine;

import nl.hu.bdsd.searchEngine.persistence.ArticleDAO;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ArticleDAO articleDAO = new ArticleDAO();
		articleDAO.selectArticle("vragen");
	}
}
