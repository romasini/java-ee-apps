package ru.romasini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.Product;
import ru.romasini.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);
    private ProductRepository productRepository;
    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() == null || req.getPathInfo().isEmpty() || req.getPathInfo().equals("/")){
            resp.getWriter().println("<table>");

            resp.getWriter().println("<thead>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>ID</td>");
            resp.getWriter().println("<td>Name</td>");
            resp.getWriter().println("<td>Description</td>");
            resp.getWriter().println("<td>Price</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</thead>");

            resp.getWriter().println("<tbody>");
            for (Product p:productRepository.findAll() ) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td><a href='"+req.getContextPath()+req.getServletPath()+"/"+p.getId()+"'>"+p.getId()+"</a></td>");
                resp.getWriter().println("<td>"+p.getName()+"</td>");
                resp.getWriter().println("<td>"+p.getDescription()+"</td>");
                resp.getWriter().println("<td>"+p.getPrice()+"</td>");
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</tbody>");

            resp.getWriter().println("</table>");
        }else {
            Matcher matcher = pathParam.matcher(req.getPathInfo());
            if(matcher.matches()){
                long id = 0;
                try {
                    id = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException e) {
                    logger.info(e.getMessage());
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Product product = productRepository.findById(id);
                if (product == null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("<h1>Product not found");
                    return;
                }
                resp.getWriter().println("<h1>Product info:</h1>");
                resp.getWriter().println("<p>Id: " + product.getId() + "</p>");
                resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
                resp.getWriter().println("<p>Description: " + product.getDescription() + "</p>");
                resp.getWriter().println("<p>Price: " + product.getPrice() + "</p>");
            }else{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }
        }

    }
}
