package cn.tyx.TangBook.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.tyx.TangBook.book.domain.Book;
import cn.tyx.TangBook.order.domain.Order;
import cn.tyx.TangBook.order.domain.OrderItem;

public class OrderDao {
	QueryRunner qr=new TxQueryRunner();
	
	public void addOrder(Order order){
		try {
		String sql="insert into orders value(?,?,?,?,?,?)";
		//处理util Date转换成 sql Date 
		Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
		Object[] params={order.getOid(),timestamp,order.getTotal(),
				order.getState(),order.getOwner().getUid(),order.getAddress()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void addOrderItemList(List<OrderItem> orderItems)
	{
		try {
			String sql="insert into orderitem value(?,?,?,?,?)";
			Object[][] params=new Object[orderItems.size()][];
			for (int i = 0; i <orderItems.size(); i++) {
				OrderItem item=orderItems.get(i);
				params[i]=new Object[]{item.getIid(),item.getCount(),
					item.getSubtotal(),item.getOrder().getOid(),
					item.getBook().getBid()};
			}
			qr.batch(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException();
			}
	}

	public List<Order> findByUid(String uid) {
		/*
		 * 1. 通过uid查询出当前用户的所有List<Order>
		 * 2. 循环遍历每个Order，为其加载他的所有OrderItem
		 */
		try {
			/*
			 * 1. 得到当前用户的所有订单
			 */
			String sql = "select * from orders where uid=?";
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			
			/*
			 * 2. 循环遍历每个Order，为其加载它自己所有的订单条目
			 */
			for(Order order : orderList) {
				loadOrderItems(order);//为order对象添加它的所有订单条目
			}
			
			/*
			 * 3. 返回订单列表
			 */
			return orderList;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadOrderItems(Order order) throws SQLException {
		/*
		 * 查询两张表：orderitem、book
		 */
		String sql = "select * from orderitem i, book b where i.bid=b.bid and oid=?";
		/*
		 * 因为一行结果集对应的不再是一个javabean，所以不能再使用BeanListHandler，而是MapListHandler
		 */
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		/*
		 * mapList是多个map，每个map对应一行结果集
		 * 一行：
		 * {iid=C7AD5492F27D492189105FB50E55CBB6, count=2, subtotal=60.0, oid=1AE8A70354C947F8B81B80ADA6783155, bid=7, bname=精通Hibernate,price=30.0, author=张卫琴, image=book_img/8991366-1_l.jpg, cid=2}
		 * ...
		 * 
		 * 我们需要使用一个Map生成两个对象：OrderItem、Book，然后再建立两者的关系（把Book设置给OrderItem）
		 */
		/*
		 * 循环遍历每个Map，使用map生成两个对象，然后建立关系（最终结果一个OrderItem），把OrderItem保存起来
		 */
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
	}
	
	/**
	 * 把mapList中每个Map转换成两个对象，并建立关系
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem item = toOrderItem(map);
			orderItemList.add(item);
		}
		return orderItemList;
	}
	
	/**
	 * 把一个Map转换成一个OrderItem对象
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		//System.out.println(orderItem);
		return orderItem;
	}

	public Order load(String oid) {
		try {
			String sql = "select * from orders where oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
				loadOrderItems(order);
			return order;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getStateByOid(String oid) {
		try {
			String sql="select state from orders where oid=?";
			return (Integer) qr.query(sql, new ScalarHandler(),oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void updateState(String oid, int i) {
		try {
			String sql="update orders set state=? where oid=?";
			qr.update(sql, i,oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
