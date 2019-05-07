package cn.tyx.TangBook.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;
import cn.tyx.TangBook.order.dao.OrderDao;
import cn.tyx.TangBook.order.domain.Order;

public class OrderService {
	private OrderDao orderdao=new OrderDao();
	
	public void addOrder(Order order){
		try {
			JdbcUtils.beginTransaction();
			orderdao.addOrder(order);
			orderdao.addOrderItemList(order.getOrderItemList());
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			}
			throw new RuntimeException(e  );
		}
	}

	public List<Order> myOrder(String uid) {
		
		return orderdao.findByUid(uid);
	}

	public Order load(String oid) {
		return orderdao.load(oid);
	}

	public void confirm(String oid) throws OrderException {
			int  state=orderdao.getStateByOid(oid);
			if(state!=3) throw new OrderException("订单确认失败，您不是什么好东西！");
			orderdao.updateState(oid,4);
	}
}
