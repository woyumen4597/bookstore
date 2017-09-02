package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import utils.JdbcUtils;
import dao.BookDao;
import domain.Book;
import domain.BookDetail;
import domain.Comment;
import domain.SearchBook;

public class BookDaoImpl implements BookDao {

	public void add(Book book){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into book(id,name,author,price,image,stock,category_id) values(?,?,?,?,?,?,?)";
			Object params[] = {book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getImage(),book.getStock(),book.getCategory_id()};
			runner.update(sql, params);
			//增加描述
			BookDetail detail = book.getDetail();
			String sql2 = "insert into book_detail(id,`desc`,publisher,publish_time) values(?,?,?,?)";
			Object[] params2 = {detail.getId(),detail.getDesc(),detail.getPublisher(),detail.getPublish_time()};
			runner.update(sql2, params2);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public Book find(String id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book where id=?";
			return (Book)runner.query(sql, new BeanHandler<Book>(Book.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Book> getPageData(int startindex, int pagesize){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "SELECT book.id,`name`,author,price,image,stock,sales,`desc`,publisher FROM "
					+ "book LEFT JOIN book_detail ON book.id=book_detail.id LIMIT ?,?;";
			Object params[] = {startindex, pagesize};
			List<Book> list = runner.query(sql, new BeanListHandler<Book>(Book.class), params);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getTotalRecord(){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from book";
			long totalrecord = runner.query(sql, new ScalarHandler<Long>());
			return (int)totalrecord;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//获得排序后的所有数据总数
	@Override
	public int getSortedTotalRecord(String category_id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from book where category_id=?";
			Object params = category_id;
			int totalrecord = runner.query(sql,new ScalarHandler<Integer>(), params);
			return (int)totalrecord;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//获得搜索后的数据总量
	@Override
	public int getSearchTotalRecord(String keyword) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from book where name like ?";
			Object params = "%"+keyword+"%";
			int totalrecord = runner.query(sql,new ScalarHandler<Integer>(), params);
			return totalrecord;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Book> getPageData(int startindex, int pagesize, String category_id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book where category_id=? limit ?,?";
			Object params[] = {category_id, startindex, pagesize};
			return (List<Book>)runner.query(sql, new BeanListHandler<Book>(Book.class), params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//获得排序后的Page带分类
	@Override
	public List<Book> getSortedPageData(int startindex, int pagesize,String category_id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book where category_id = ? order by sales desc limit ?,?";
			Object params[] = {category_id,startindex, pagesize};
			List<Book> books = runner.query(sql, new BeanListHandler<Book>(Book.class), params);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//获得排序后的Page
	@Override
	public List<Book> getSortedPageData(int startindex, int pagesize) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book order by sales desc limit ?,?";
			Object params[] = {startindex, pagesize};
			List<Book> books = runner.query(sql, new BeanListHandler<Book>(Book.class), params);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//获得搜索之后的Page
	@Override
	public List<Book> getSearchPageData(int startindex, int pagesize, String keyword) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book where name like ? limit ?,?";
			Object params[] = {"%"+keyword+"%",startindex, pagesize};
			List<Book> books =  runner.query(sql, new BeanListHandler<Book>(Book.class), params);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getTotalRecord(String category_id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from book where category_id=?";
			long totalrecord = runner.query(sql, new ScalarHandler<Long>(), category_id);
			return (int)totalrecord;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void modify(String bookid, String stock) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update book set stock = ? where id = ?";
			Object[] params = new Object[]{Integer.parseInt(stock),bookid};
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//获得某本书的库存
	@Override
	public int getStock(String bookid) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select stock from book where id = ?";
			long stock = runner.query(sql, new ScalarHandler<Long>(), bookid);
			return (int)stock;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookDetail findBookInfo(String bookid) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from book_detail where id = ?";
			BookDetail detail = runner.query(sql, new BeanHandler<BookDetail>(BookDetail.class), bookid);
			return detail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//增加评论
	@Override
	public void addComment(Comment comment) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into comment(id,book_id,user_name,comment,create_time) values(?,?,?,?,?)";
			Object[] params = {comment.getId(),comment.getBook_id(),
					comment.getUser_name(),comment.getComment(),comment.getCreate_time()};
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//获取评论
	@Override
	public List<Comment> getCommentsById(String bookid) {
		List<Comment> list = new ArrayList<Comment>();
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from `comment` where book_id = ?";
			list = runner.query(sql, new BeanListHandler<Comment>(Comment.class), bookid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<SearchBook> getBookList() {
		List<SearchBook> list = new ArrayList<>();
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select book.id,book.name,book.author,book.price,book.image"
					+ ",book.sales,category.name category_name from book,category"
					+ " where book.category_id = category.id";
			list = runner.query(sql, new BeanListHandler<SearchBook>(SearchBook.class));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public SearchBook findSearchBook(String id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select book.id,book.name,book.author,book.price,book.image"
					+ ",book.sales,category.name category_name from book,category"
					+ " where book.category_id = category.id and book.id = ?";
			SearchBook searchBook = runner.query(sql, new BeanHandler<SearchBook>(SearchBook.class), id);
			return searchBook;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}










}
