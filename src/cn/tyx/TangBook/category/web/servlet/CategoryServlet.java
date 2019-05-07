package cn.tyx.TangBook.category.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.tyx.TangBook.category.service.CategoryService;

public class CategoryServlet extends BaseServlet {
	CategoryService categoryService =new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("categorylist", categoryService.findAll());
		return "f:/jsps/left.jsp";
	}
		
}
