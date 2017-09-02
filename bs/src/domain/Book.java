package domain;

public class Book implements Comparable<Book>{
	/*
	 * create table book
	   (
	  		id varchar(40) primary key,
	  		name varchar(100) not null unique,
	  		author varchar(100) not null,
	  		price double not null,
	 		image varchar(40),
	  		description varchar(255),
	  		category_id varchar(40),
	  		sales int,
	  		stock int,
	  		constraint category_id_FK foreign key(category_id) references category(id)
	   );
	 */

	private String id;
	private String name;
	private String author;
	private double price;
	private String image;
	private String category_id;
	private int sales;
	private int stock;
	private BookDetail detail;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public int compareTo(Book o) {
		//先按销量排序，若销量相同就用名称排序
		if(this.sales>o.getSales()){
			return 1;
		}
		if(this.sales < o.getSales()){
			return -1;
		}
		if(this.name.compareTo(o.getName())>0){
			return 1 ;
		}
		if(this.name.compareTo(o.getName())<0){
			return -1;
		}
		return 0;
	}
	public BookDetail getDetail() {
		return detail;
	}
	public void setDetail(BookDetail detail) {
		this.detail = detail;
	}
}
