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

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        String idPath = req.getPathInfo();

        if (idPath != null) {
            idPath = idPath.substring(1);//отбросим /
            logger.info(idPath);
            try {
                id = Long.parseLong(idPath);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                resp.getWriter().println("<h1>Some trouble</h1>");
                return;
            }
        }

        if(id == null){

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
            Product product = productRepository.findById(id);
            if (product != null) {
                resp.getWriter().println("<h1>Product info:</h1>");
                resp.getWriter().println("<p>Id: " + product.getId() + "</p>");
                resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
                resp.getWriter().println("<p>Description: " + product.getDescription() + "</p>");
                resp.getWriter().println("<p>Price: " + product.getPrice() + "</p>");
            } else {
                resp.getWriter().println("<h1>Product not found</h1>");
            }
        }
    }
}
