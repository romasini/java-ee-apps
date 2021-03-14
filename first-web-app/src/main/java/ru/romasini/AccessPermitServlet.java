package ru.romasini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/*")
public class AccessPermitServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AccessPermitServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Access permit servlet");

        resp.getWriter().println("<h1>Access permit</h1>");
        
    }
}
