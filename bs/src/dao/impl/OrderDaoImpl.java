package dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.JdbcUtils;
import dao.OrderDao;
import domain.Book;
import domain.Order;
import domain.OrderItem;
import domain.User;
/*
 	create table orders
	(
		id varchar(40) primary key,
		ordertime datetime not null,
		price double not null,
		state boolean,
		user_id varchar(40),
		constraint user_id_FK foreign key(user_id) references user(id)
	);

	create table orderitem
	(
		id varchar(40) primary key,
		quantity int,
		price double,
		order_id varchar(40),
		book_id varchar(40),
		constraint order_id_FK foreign key(order_id) references orders(id),
		constraint book_id_FK foreign key(book_id) references book(id)
	);
 */
public class OrderDaoImpl implements OrderDao {

	/* (non-Javadoc)
	 * @see dao.impl.OrderDao#add(domain.Order)
	 */
	public void add(Order order){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			//1. 把order的基本信息保存到order表
			String sql = "insert into orders(id,ordertime,price,state,user_id) values(?,?,?,?,?)";
			Object params[] = {order.getId(), order.getOrdertime(), order.getPrice(), order.getState(), order.getUser().getId()};
			runner.update(sql, params);
			//2. 把order中的订单项保存到orderitem表中
			Set<OrderItem> set = order.getOrderitems();
			for(OrderItem item : set){
				sql = "insert into orderitem(id,quantity,price,order_id,book_id,address) values(?,?,?,?,?,?)";
				params = new Object[]{item.getId(), item.getQuantity(), item.getPrice(), order.getId(), item.getBook().getId(),item.getAddress()};
				runner.update(sql, params);
				//库存减少
				sql = "update book set stock =stock-? where id = ?";
				params = new Object[]{item.getQuantity(),item.getBook().getId()};
				runner.update(sql,params);
			}
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see dao.impl.OrderDao#find(java.lang.String)
	 */
	public Order find(String id){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			//1.找出订单的基本信息
			String sql = "select * from orders where id=?";
			Order order = runner.query(sql, new BeanHandler<Order>(Order.class), id);
			//2.找出订单中所有的订单项
			sql = "select * from orderitem where order_id=?";
			List<OrderItem> list = runner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), id);
			for(OrderItem item : list){
				sql = "select book.* from orderitem,book where orderitem.id=? and orderitem.book_id=book.id";
				Book book = runner.query(sql, new BeanHandler<Book>(Book.class), item.getId());
				item.setBook(book);
			}
			//把找出的订单项放进order
			order.getOrderitems().addAll(list);
			//3.找出订单属于哪个用户
			sql = "select * from orders,user where orders.id=? and orders.user_id=user.id";
			User user = runner.query(sql, new BeanHandler<User>(User.class), order.getId());
			order.setUser(user);
			return order;

		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//后台获取所有订单
	public List<Order> getAll(int state){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where state=?";
			List<Order> list = runner.query(sql,new BeanListHandler<Order>(Order.class),state);
			for(Order order : list){
				//找出当前订单属于哪个用户
				sql = "select user.* from orders,user where orders.id=? and orders.user_id=user.id";
				User user =  runner.query(sql, new BeanHandler<User>(User.class), order.getId());
				order.setUser(user);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//后台获取所有订单
	@Override
	public List<Order> getAll() {
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders";
			List<Order> list = (List<Order>) runner.query(sql,new BeanListHandler<Order>(Order.class));
			for(Order order : list){
				//找出当前订单属于哪个用户
				sql = "select user.* from orders,user where orders.id=? and orders.user_id=user.id";
				User user = (User) runner.query(sql, new BeanHandler<User>(User.class), order.getId());
				order.setUser(user);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//前端页面中获取某个用户的所有订单
	public List<Order> getAll(int state, String userid){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where state=? and orders.user_id=?";
			Object params[] = {state, userid};
			List<Order> list = (List<Order>) runner.query(sql, new BeanListHandler<Order>(Order.class), params);
			//将所有该user加到list中
			for(Order order : list){
				sql = "select * from user where user.id=?";
				User user = (User) runner.query(sql, new BeanHandler<User>(User.class), userid);
				order.setUser(user);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public List<Order> getAllOrder(String userid){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where user_id=?";
			List<Order> list = (List<Order>) runner.query(sql, new BeanListHandler<Order>(Order.class), userid);
			//将所有该user加到List中去
			for(Order order : list){
				sql = "select * from user where id=?";
				User user = (User) runner.query(sql, new BeanHandler<User>(User.class), userid);
				order.setUser(user);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see dao.impl.OrderDao#update(domain.Order)
	 */
	public void update(Order order){//这里只改变发货状态，实际中还可以改变购买数量等其他信息，可以再完善
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update orders set state=? where id=?";
			Object params[] = new Object[]{order.getState(), order.getId()};
			runner.update(sql, params);
			//相应的书的销量增加
			Set<OrderItem> set = order.getOrderitems();
			for(OrderItem oitem:set){
				sql = "update book set sales = sales+? where id = ?";
				params = new Object[]{oitem.getQuantity(),oitem.getBook().getId()};
				runner.update(sql,params);
			}

		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//删除订单
	@Override
	public void delete(String orderid) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "delete from orders where id = ?";
			Object params[] = {orderid};
			runner.update(sql, params);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//取消顶单的时候恢复库存
	@Override
	public void cancel(Order order) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update book set stock=stock+? where id = ?";
			Set<OrderItem> set = order.getOrderitems();
			for(OrderItem oitem:set){
				Object params[] = {oitem.getQuantity(),oitem.getBook().getId()};
				runner.update(sql, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}



}
