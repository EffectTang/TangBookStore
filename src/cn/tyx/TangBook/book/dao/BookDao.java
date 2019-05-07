package cn.tyx.TangBook.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.tyx.TangBook.book.domain.Book;
import cn.tyx.TangBook.category.domain.Category;


public class BookDao {
	private QueryRunner qr=new TxQueryRunner();
	
	public List<Book> findAll(){
		try {
			String sql="select * from book where del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Book> findByCategory(String cid) {
		try {
			String sql="select * from book where cid=?";
			//System.out.println("bookdao");
			//System.out.println(qr.query(sql, new BeanListHandler<Book>(Book.class),cid));
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		
//		try {
//			String sql = "select * from book where cid=? ？";
//			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
//		} catch(SQLException e) {
//			throw new RuntimeException(e);
//		}
	}

	public Book load(String bid) {
		try {
			//我们需要在book中保存category的信息 private的缘故  直接查询 直接映射 book查询  category为null
			String sql="select * from book where bid=?";
			Map<String, Object> map=qr.query(sql, new MapHandler(),bid);
			Category category=new CommonUtils().toBean(map, Category.class);
			Book book=new CommonUtils().toBean(map, Book.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	

	public int getCountByCid(String cid) {
		try {
			String sql=" select count(*) from book where cid=?";
			Number num= (Number) qr.query(sql, new ScalarHandler(),cid);
			return num.intValue();
		} catch (SQLException e) {
			throw new RuntimeException();
		} 
		
	}

	public void add(Book book) {
		try {
			String sql = "insert into book values(?,?,?,?,?,?)";
			Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
					book.getAuthor(), book.getImage(), book.getCategory().getCid()};
			qr.update(sql, params);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String bid) {
		try {
			String sql = "update book set del=true where bid=?";
			qr.update(sql, bid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(Book book) {
		try {
			String sql = "update book set bname=?, price=?,author=?, image=?, cid=? where bid=?";
			Object[] params = {book.getBname(), book.getPrice(),
					book.getAuthor(), book.getImage(), 
					book.getCategory().getCid(), book.getBid()};
			qr.update(sql, params);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
