package ru.romasini.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class SecurityFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //учебный пример
        if(servletRequest.getParameter("username") != null){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            //servletResponse.getWriter().println("<h1>Access denied</h1>");
            filterConfig.getServletContext().getRequestDispatcher("/access_denied").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
