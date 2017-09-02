package test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	@Test
	public void addDocument() throws Exception{
		SolrServer solr = new HttpSolrServer("http://server:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "jrc");
		document.addField("book_name", "数据结构");
		document.addField("book_price", 20.0);
		solr.add(document);
		solr.commit();
	}
}
