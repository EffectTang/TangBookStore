package cn.tyx.TangBook.book.web.servlet.admin;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.tyx.TangBook.book.domain.Book;
import cn.tyx.TangBook.book.service.BookService;
import cn.tyx.TangBook.category.domain.Category;
import cn.tyx.TangBook.category.service.CategoryService;

public class AdminBookServlet extends BaseServlet {
	
	private BookService bookService=new BookService();
	private CategoryService categoryService=new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("bookList", bookService.findAll());
		//System.out.println(bookService.findAll());
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	public String findByBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		request.setAttribute("book", bookService.load(bid));
		request.setAttribute("bookList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("category", categoryService.findAll());
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		bookService.delete(bid);
		return findAll(request, response);
	}
	
	
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装数据
		Book book=CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category=CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		bookService.edit(book);
		return findAll(request, response);
	}
}
