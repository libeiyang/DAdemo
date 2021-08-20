package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RedirectFilter", urlPatterns = "/hub.xhtml")

public class loginFilter implements Filter {
    public loginFilter(){

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String url = request.getRequestURI();
//        boolean login_request_correct = url.equals(request.getContextPath() + "/login.xhtml") || url.equals(request.getContextPath() + "/register.xhtml");
        boolean login_request_correct = url.equals( "/DAdemo8/login.xhtml");

//        filterChain.doFilter(request,response);

        if(session.getAttribute("user_id")!=null){
            filterChain.doFilter(request,response);
        }
        else {
            response.sendRedirect( "/DAdemo8/login.xhtml");
        }
    }

    @Override
    public void destroy() {

    }

}
