package service.impl;

import java.util.List;

import domain.Book;
import domain.BookDetail;
import domain.Cart;
import domain.Category;
import domain.Comment;
import domain.Order;
import domain.Page;
import domain.SearchResult;
import domain.User;

public interface BusinessService {


	void addCategory(Category category);

	Category findCategory(String id);

	List<Category> getAllCategory();

	Page getBookPageData(String pagenum);

	Page getBookPageData(String pagenum, String category_id);

	Page getSortedBookPageData(String pagenum, String category_id);

	Page getSortedBookPageData(String pagenum);

	Page getSearchPageData(String pagenum, String keyword);

	void buyBook(Cart cart, Book book);

	void registerUser(User user);

	void updateUser(User user);

	User findUser(String id);

	User userLogin(String username, String password);

	void createOrder(Cart cart, User user);

	List<Order> listOrder(String state);

	List<Order> listOrder();

	void deleteOrder(String orderid);

	Order findOrder(String orderid);

	void confirmOrder(String orderid);

	void cancelOrder(String orderid);

	void confirmReceived(String orderid);

	List<Order> listOrder(String state, String userid);

	List<Order> clientListOrder(String userid);

	Book findBook(String id);

	void modifyBook(String bookid, String stock);

	void addBook(Book book);

	BookDetail findBookInfo(String book_id);

	void addComment(Comment comment);

	List<Comment> getCommentsById(String bookid);

	void createSimpleOrder(Cart cart, User user, String bookid, String address);

	SearchResult search(String queryString,int page,int rows) throws Exception;

	void importAllBooks();

	void importBook(String id);

	SearchResult sort(String key,String category_id,String method,int page,int rows) throws Exception;

	boolean findUserByName(String username);

}