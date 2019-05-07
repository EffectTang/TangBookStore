package cn.tyx.TangBook.book.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.tyx.TangBook.book.service.BookService;

public class BookServlet extends BaseServlet {
		BookService bookservice= new BookService();

		public String findAll(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			request.setAttribute("booklist", bookservice.findAll());
			return "f:/jsps/book/list.jsp";
		}
		
		public String findByCategory(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			String cid= request.getParameter("cid");
			//System.out.println(cid);
			request.setAttribute("booklist", bookservice.findByCategory(cid));
			return "f:/jsps/book/list.jsp";
		}
		
		public String load(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,IOException {
			String bid=request.getParameter("bid");
			//System.out.println(bid);
			request.setAttribute("book", bookservice.load(bid));
			return "f:/jsps/book/desc.jsp";
		
		}
}
