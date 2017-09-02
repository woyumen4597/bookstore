package dao;

import java.util.List;

import domain.Order;

public interface OrderDao {

	void add(Order order);

	Order find(String id);

	List<Order> getAll(int state);

	void update(Order order);

	void cancel(Order order);

	void delete(String orderid);

	List<Order> getAll(int state, String userid);

	List<Order> getAllOrder(String userid);

	List<Order> getAll();

}