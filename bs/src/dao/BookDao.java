package dao;

import java.util.List;

import domain.Book;
import domain.BookDetail;
import domain.Comment;
import domain.SearchBook;

public interface BookDao {

	void add(Book book);

	Book find(String id);

	public List<Book> getPageData(int startindex, int pagesize);

	public int getTotalRecord();

	public int getSortedTotalRecord(String category_id);

	public List<Book> getPageData(int startindex, int pagesize, String category_id);

	public int getTotalRecord(String category_id);

	void modify(String bookid, String stock);

	public List<Book> getSortedPageData(int startindex, int pagesize, String category_id);

	public List<Book> getSortedPageData(int startindex, int pagesize);

	public List<Book> getSearchPageData(int startindex, int pagesize, String keyword);

	public int getSearchTotalRecord(String keyword);

	public int getStock(String bookid);

	public BookDetail findBookInfo(String bookid);

	public void addComment(Comment comment);

	public List<Comment> getCommentsById(String bookid);

	public List<SearchBook> getBookList();

	public SearchBook findSearchBook(String id);

}