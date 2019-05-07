package cn.tyx.TangBook.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.tyx.TangBook.user.domain.User;

public class UserDao {
	private QueryRunner qr=new TxQueryRunner();
		
		public User findByUsername(String username){
			try {
				String sql="select * from tb_user where username=?";
				return qr.query(sql, new BeanHandler<User>(User.class),username);
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		
		public User findByEmail(String email){
			try {
				String sql="select * from tb_user where username=?";
				return qr.query(sql, new BeanHandler<User>(User.class),email);
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		
		public void addUser(User user)
		{
			try {
				String sql="insert into tb_user values(?,?,?,?,?,?)";
				Object params[] ={user.getUid(),user.getUsername(),user.getPassword(),
						user.getEmail(),user.getCode(),user.isState()};
				qr.update(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		
}
