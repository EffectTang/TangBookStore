package cn.tyx.TangBook.order.web.servlet;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.tyx.TangBook.cart.domain.Cart;
import cn.tyx.TangBook.cart.domain.CartItem;
import cn.tyx.TangBook.order.domain.Order;
import cn.tyx.TangBook.order.domain.OrderItem;
import cn.tyx.TangBook.order.service.OrderException;
import cn.tyx.TangBook.order.service.OrderService;
import cn.tyx.TangBook.user.domain.User;

public class OrderServlet extends BaseServlet {
		OrderService  orderservice=new OrderService();

		public String addOrder(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			Cart cart=(Cart) request.getSession().getAttribute("cart");
			
			Order order=new Order();
			order.setOid(CommonUtils.uuid());
			order.setOrdertime(new Date());
			order.setState(1);
			User user=(User) request.getSession().getAttribute("session_user");
			order.setOwner(user);
			order.setTotal(cart.getTotal());
			
			List<OrderItem> orderitemList=new ArrayList<OrderItem>();
			for(CartItem carItem:cart.getCartItems())
			{
				OrderItem oi=new OrderItem();
				
				oi.setIid(CommonUtils.uuid());
				oi.setCount(carItem.getCount());
				oi.setSubtotal(carItem.getSubtotal());
				oi.setBook(carItem.getBook());
				oi.setOrder(order);
				 
				orderitemList.add(oi);
			}
			order.setOrderItemList(orderitemList);
			//清空购物车
			cart.clear();
			orderservice.addOrder(order);
			request.setAttribute("order", order);
			
			return "f:/jsps/order/desc.jsp";
		}

		public String myOrder(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			User user=(User) request.getSession().getAttribute("session_user");
			List<Order> orderList=orderservice.myOrder(user.getUid());
			request.setAttribute("orderList", orderList);
			return "f:/jsps/order/list.jsp";
		}
		
		public String load(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			String oid=(String) request.getParameter("oid");
//			System.out.println("servlet ");
//			System.out.println(oid);
			Order order=orderservice.load(oid);
			request.setAttribute("order", order);
			return "f:/jsps/order/desc.jsp";
		}

		
		public String confirm(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			
			String oid = request.getParameter("oid");
			try {
				orderservice.confirm(oid);
				request.setAttribute("msg", "恭喜，交易成功！");
			} catch (OrderException e) {
				request.setAttribute("msg", e.getMessage());
			}
			return "f:/jsps/msg.jsp";
		}
		
		
		
}
