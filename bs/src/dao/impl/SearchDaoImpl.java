package dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import dao.SearchDao;
import domain.SearchBook;
import domain.SearchResult;

public class SearchDaoImpl implements SearchDao {
	private static String SolrUrl;
	static{
		Properties properties = new Properties();
		InputStream inputStream = SearchDaoImpl.class.getClassLoader().getResourceAsStream("resource.properties");
		try {
			properties.load(inputStream);
			SolrUrl = properties.getProperty("SOLR_SERVER_URL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HttpSolrServer solr = new HttpSolrServer(SolrUrl);

	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		SearchResult result = new SearchResult();
		QueryResponse response = solr.query(query);
		SolrDocumentList documentList = response.getResults();
		result.setRecordCount(documentList.getNumFound());
		List<SearchBook> SearchBookList = new ArrayList<SearchBook>();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : documentList) {
			SearchBook book = new SearchBook();
			book.setId((String) solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("book_name");
			String name = "";
			if(list!=null&&list.size()>0){
				name = list.get(0);
			}else{
				name = (String) solrDocument.get("book_name");
			}
			book.setName(name);
			book.setImage((String) solrDocument.get("book_image"));
			book.setPrice((double) solrDocument.get("book_price"));
			book.setAuthor((String) solrDocument.get("book_author"));
			book.setSales((int) solrDocument.get("book_sales"));
			book.setCategory_name((String) solrDocument.get("book_category_name"));
			SearchBookList.add(book);
		}
		result.setBookList(SearchBookList);
		return result;
	}

}
