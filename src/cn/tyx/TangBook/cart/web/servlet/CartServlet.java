package cn.tyx.TangBook.cart.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.tyx.TangBook.book.domain.Book;
import cn.tyx.TangBook.book.service.BookService;
import cn.tyx.TangBook.cart.domain.Cart;
import cn.tyx.TangBook.cart.domain.CartItem;

public class CartServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
	//	System.out.println(cart);
		String bid=request.getParameter("bid");
		Book book=new BookService().load(bid);
		
		int count=Integer.parseInt(request.getParameter("count"));
		 CartItem cartItem=new CartItem();
		 cartItem.setCount(count);
		 cartItem.setBook(book);
		 
		 cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		String bid=request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
	
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
}
