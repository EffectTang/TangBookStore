package cn.tyx.TangBook.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.tyx.TangBook.user.domain.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    public LoginFilter() {
    	
    }
	public void destroy() {
	
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpreqRequest=(HttpServletRequest) request;
		User user=(User) httpreqRequest.getSession().getAttribute("session_user");
		if(user!=null)
		chain.doFilter(request, response);
		else {
			httpreqRequest.setAttribute("msg", "对不起 你还没有登陆 没权利访问");
			
			httpreqRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
