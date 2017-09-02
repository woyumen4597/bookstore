package dao;

import org.apache.solr.client.solrj.SolrQuery;

import domain.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery query) throws Exception;
}
