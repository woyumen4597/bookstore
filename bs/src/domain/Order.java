package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {
	public static int UNFILLED = 0;//未发货
	public static int FILLED = 1;//已发货
	public static int Cancelled = 2;//已取消
	public static int FINISHED = 3;//已完成
	private String id;
	private Date ordertime;
	private double price;
	private int state;
	private String express_num;
	private User user;
	private Set<OrderItem> orderitems = new HashSet<OrderItem>();

	public Set<OrderItem> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(Set<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getExpress_num() {
		return express_num;
	}
	public void setExpress_num(String express_num) {
		this.express_num = express_num;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

}
