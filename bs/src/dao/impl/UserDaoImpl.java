package dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import utils.JdbcUtils;
import dao.UserDao;
import domain.User;

public class UserDaoImpl implements UserDao {

	public void add(User user){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into user(id,username,password,phone,cellphone,address,email) values(?,?,?,?,?,?,?)";
			Object params[] = {user.getId(), user.getUsername(), user.getPassword(), user.getPhone(), user.getCellphone(), user.getAddress(), user.getEmail()};
			runner.update(sql, params);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public User find(String id){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from user where id=?";
			return (User)runner.query(sql, new BeanHandler<User>(User.class), id);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public User find(String username, String password){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from user where username=? and password=?";
			Object params[] = {username, password};
			return (User)runner.query(sql, new BeanHandler<User>(User.class), params);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User user) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update user set username=?,password=?,phone=?,cellphone=?,address=?,email=? where id=?";
			Object params[] = {user.getUsername(),user.getPassword(),user.getPhone(),user.getCellphone(),user.getAddress(),user.getEmail(),user.getId()};
			runner.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean findUser(String username) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from user where username = ?";
			User user = runner.query(sql, new BeanHandler<User>(User.class), username);
			if(user==null){
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}
