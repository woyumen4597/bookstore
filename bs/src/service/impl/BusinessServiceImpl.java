package service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import utils.DaoFactory;
import utils.MailUtils;
import utils.WebUtils;
import dao.BookDao;
import dao.CategoryDao;
import dao.OrderDao;
import dao.SearchDao;
import dao.UserDao;
import dao.impl.SearchDaoImpl;
import domain.Book;
import domain.BookDetail;
import domain.Cart;
import domain.CartItem;
import domain.Category;
import domain.Comment;
import domain.Order;
import domain.OrderItem;
import domain.Page;
import domain.SearchBook;
import domain.SearchResult;
import domain.User;

public class BusinessServiceImpl implements BusinessService {

	private CategoryDao categoryDao = DaoFactory.getInstance().createDao("dao.impl.CategoryDaoImpl", CategoryDao.class);
	private BookDao bookDao = DaoFactory.getInstance().createDao("dao.impl.BookDaoImpl", BookDao.class);
	private UserDao userDao = DaoFactory.getInstance().createDao("dao.impl.UserDaoImpl", UserDao.class);
	private OrderDao orderDao = DaoFactory.getInstance().createDao("dao.impl.OrderDaoImpl", OrderDao.class);
	private SearchDao searchDao = DaoFactory.getInstance().createDao("dao.impl.SearchDaoImpl", SearchDao.class);
	private static String SolrUrl;
	static {
		Properties properties = new Properties();
		InputStream inputStream = SearchDaoImpl.class.getClassLoader().getResourceAsStream("resource.properties");
		try {
			properties.load(inputStream);
			SolrUrl = properties.getProperty("SOLR_SERVER_URL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCategory(Category category) {
		categoryDao.add(category);
	}

	@Override
	public Category findCategory(String id) {
		return categoryDao.find(id);
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryDao.getAll();
	}

	// 添加书
	@Override
	public void addBook(Book book) {
		bookDao.add(book);
	}

	// 修改图书的库存
	@Override
	public void modifyBook(String bookid, String stock) {
		bookDao.modify(bookid, stock);

	}

	// 获得书
	@Override
	public Book findBook(String id) {
		return bookDao.find(id);
	}

	// 获得分页数据
	@Override
	public Page getBookPageData(String pagenum) {
		int totalrecord = bookDao.getTotalRecord();
		Page page = null;
		if (pagenum == null) {
			page = new Page(1, totalrecord);
		} else {
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getPageData(page.getStartindex(), page.getPagesize());
		page.setList(list);
		return page;
	}

	@Override
	public Page getBookPageData(String pagenum, String category_id) {
		int totalrecord = bookDao.getTotalRecord(category_id);
		Page page = null;
		if (pagenum == null) {
			page = new Page(1, totalrecord);
		} else {
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getPageData(page.getStartindex(), page.getPagesize(), category_id);
		page.setList(list);
		return page;
	}

	// 获得按销量排序后的数据按分类
	@Override
	public Page getSortedBookPageData(String pagenum, String category_id) {
		int totalrecord = bookDao.getSortedTotalRecord(category_id);
		Page page = null;
		if (pagenum == null) {
			page = new Page(1, totalrecord);
		} else {
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getSortedPageData(page.getStartindex(), page.getPagesize(), category_id);
		page.setList(list);
		return page;
	}

	// 获得按销量排序后的数据
	@Override
	public Page getSortedBookPageData(String pagenum) {
		int totalrecord = bookDao.getTotalRecord();
		Page page = null;
		if (pagenum == null) {
			page = new Page(1, totalrecord);
		} else {
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getSortedPageData(page.getStartindex(), page.getPagesize());
		page.setList(list);
		return page;
	}

	// 获得搜索的数据
	@Override
	public Page getSearchPageData(String pagenum, String keyword) {
		int totalrecord = bookDao.getSearchTotalRecord(keyword);
		Page page = null;
		if (pagenum == null) {
			page = new Page(1, totalrecord);
		} else {
			page = new Page(Integer.parseInt(pagenum), totalrecord);
		}
		List<Book> list = bookDao.getSearchPageData(page.getStartindex(), page.getPagesize(), keyword);
		page.setList(list);
		return page;
	}

	@Override
	public void buyBook(Cart cart, Book book) {
		cart.add(book);
	}

	// 注册用户
	@Override
	public void registerUser(User user) {
		userDao.add(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User findUser(String id) {
		return userDao.find(id);
	}

	@Override
	public User userLogin(String username, String password) {
		return userDao.find(username, password);
	}

	// 生成订单
	@Override
	public void createOrder(Cart cart, User user) {
		if (cart == null) {
			throw new RuntimeException("对不起，您还没有购买任何商品");
		}
		Order order = new Order();
		order.setId(WebUtils.makeID());
		order.setOrdertime(new Date());
		order.setPrice(cart.getPrice());
		order.setState(Order.UNFILLED);
		order.setUser(user);
		for (Map.Entry<String, CartItem> me : cart.getMap().entrySet()) {
			// 得到一个购物项就生成一个订单项
			CartItem citem = me.getValue();
			OrderItem oitem = new OrderItem();
			oitem.setBook(citem.getBook());
			oitem.setPrice(citem.getPrice());
			oitem.setId(WebUtils.makeID());
			int stock = bookDao.getStock(citem.getBook().getId());
			System.out.println(stock);
			if (citem.getQuantity() > stock) {
				System.out.println("库存不足");
				MailUtils.StockOutInform(citem.getBook().getName());
				throw new RuntimeException("对不起，你所购买的图书" + citem.getBook().getName() + "库存不足!");
			} else {
				oitem.setQuantity(citem.getQuantity());
				order.getOrderitems().add(oitem);
			}
		}
		orderDao.add(order);
	}

	// 创建只有一个货物的订单
	@Override
	public void createSimpleOrder(Cart cart, User user, String bookid, String address) {
		if (cart == null) {
			throw new RuntimeException("对不起，您还没有购买任何商品");
		}
		Order order = new Order();
		order.setId(WebUtils.makeID());
		order.setOrdertime(new Date());
		order.setPrice(cart.getPrice());
		order.setState(Order.UNFILLED);
		order.setUser(user);
		OrderItem oitem = new OrderItem();
		for (Map.Entry<String, CartItem> me : cart.getMap().entrySet()) {
			CartItem citem = me.getValue();
			if (citem.getBook().getId().equals(bookid)) {
				oitem.setBook(citem.getBook());
				oitem.setPrice(citem.getPrice());
				oitem.setId(WebUtils.makeID());
				oitem.setAddress(address);
				int stock = bookDao.getStock(citem.getBook().getId());
				if (citem.getQuantity() > stock) {
					System.out.println("库存不足");
					MailUtils.StockOutInform(citem.getBook().getName());
					throw new RuntimeException("对不起，你所购买的图书" + citem.getBook().getName() + "库存不足!");
				} else {
					oitem.setQuantity(citem.getQuantity());
					order.getOrderitems().add(oitem);
				}
				cart.getMap().remove(bookid); // 删除记录
				break;
			}

		}
		orderDao.add(order);

	}

	// 后台获取相应订单信息
	@Override
	public List<Order> listOrder(String state) {
		return orderDao.getAll(Integer.parseInt(state));
	}

	// 后台获取所有订单信息
	@Override
	public List<Order> listOrder() {
		return orderDao.getAll();
	}

	// 删除订单
	@Override
	public void deleteOrder(String orderid) {
		orderDao.delete(orderid);
	}

	// 列出订单明细
	@Override
	public Order findOrder(String orderid) {
		return orderDao.find(orderid);
	}

	// 把订单置为发货状态
	@Override
	public void confirmOrder(String orderid) {
		Order order = orderDao.find(orderid);
		order.setState(Order.FILLED);
		orderDao.update(order);
	}

	// 把订单置为取消状态,并恢复库存
	@Override
	public void cancelOrder(String orderid) {
		Order order = orderDao.find(orderid);
		order.setState(Order.Cancelled);
		orderDao.update(order);
		// 恢复库存
		orderDao.cancel(order);
	}

	// 把订单置为已收货状态
	@Override
	public void confirmReceived(String orderid) {
		Order order = orderDao.find(orderid);
		order.setState(Order.FINISHED);
		orderDao.update(order);
	}

	// 获得某个用户的订单信息
	@Override
	public List<Order> listOrder(String state, String userid) {
		return orderDao.getAll(Integer.parseInt(state), userid);
	}

	// 获取某个用户的订单信息
	@Override
	public List<Order> clientListOrder(String userid) {
		return orderDao.getAllOrder(userid);
	}

	// 获取书的细节
	@Override
	public BookDetail findBookInfo(String book_id) {
		return bookDao.findBookInfo(book_id);
	}

	@Override
	public void addComment(Comment comment) {
		bookDao.addComment(comment);
	}

	@Override
	public List<Comment> getCommentsById(String bookid) {
		return bookDao.getCommentsById(bookid);
	}

	// solr搜索服务
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置分页
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", "book_keywords");
		// 设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("book_name");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 执行查询
		SearchResult searchResult = searchDao.search(query);
		// 计算查询结果总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		return searchResult;
	}

	@Override
	public void importAllBooks() {
		try {
			SolrServer solr = new HttpSolrServer(SolrUrl);
			List<SearchBook> list = bookDao.getBookList();
			for (SearchBook searchBook : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", searchBook.getId());
				document.setField("book_name", searchBook.getName());
				document.setField("book_author", searchBook.getAuthor());
				document.setField("book_price", searchBook.getPrice());
				document.setField("book_image", searchBook.getImage());
				document.setField("book_category_name", searchBook.getCategory_name());
				document.setField("book_sales", searchBook.getSales());
				solr.add(document);
			}
			solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void importBook(String id) {
		try {
			SolrServer solr = new HttpSolrServer(SolrUrl);
			SearchBook searchBook = bookDao.findSearchBook(id);
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", searchBook.getId());
			document.setField("book_name", searchBook.getName());
			document.setField("book_author", searchBook.getAuthor());
			document.setField("book_price", searchBook.getPrice());
			document.setField("book_image", searchBook.getImage());
			document.setField("book_category_name", searchBook.getCategory_name());
			document.setField("book_sales", searchBook.getSales());
			solr.add(document);
			solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public SearchResult sort(String key,String category_id,String method, int page, int rows) throws Exception {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		if(category_id==null||category_id.equals("")){
			query.setQuery("book_name:*");
		}else{
			Category category = categoryDao.find(category_id);
			query.setQuery("book_category_name:"+category.getName());
		}
		// 设置分页
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		if(method.equalsIgnoreCase("desc")){
			query.setSort(key, ORDER.desc);
		}else{
			query.setSort(key,ORDER.asc);
		}
		// 设置默认搜索域
		query.set("df", "book_keywords");
		// 设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("book_name");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 执行查询
		SearchResult searchResult = searchDao.search(query);
		// 计算查询结果总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		return searchResult;
	}

	@Override
	public boolean findUserByName(String username) {
		boolean b = userDao.findUser(username);
		return b;
	}

}
