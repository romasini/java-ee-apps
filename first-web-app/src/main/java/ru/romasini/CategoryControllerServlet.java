package ru.romasini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.Category;
import ru.romasini.persist.CategoryRepository;
import ru.romasini.persist.Customer;
import ru.romasini.persist.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/category/*")
public class CategoryControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CategoryControllerServlet.class);

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    private CategoryRepository categoryRepository;

    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository) getServletContext().getAttribute("categoryRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            req.setAttribute("categories", categoryRepository.findAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/new")) {
            req.setAttribute("category", new Category());
            getServletContext().getRequestDispatcher("/WEB-INF/views/category_form.jsp").forward(req, resp);
        } else {
            try {
                long id = getIdFromPath(req.getPathInfo());
                Category category = categoryRepository.findById(id);
                if (category == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                req.setAttribute("category", category);
                getServletContext().getRequestDispatcher("/WEB-INF/views/category_form.jsp").forward(req, resp);
            } catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {

            String strId = req.getParameter("id");
            try {
                Category category = new Category(
                        strId.isEmpty() ? null : Long.parseLong(strId),
                        req.getParameter("name"));
                categoryRepository.save(category);
                resp.sendRedirect(getServletContext().getContextPath() + "/category");
            } catch (NumberFormatException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if(req.getPathInfo().startsWith("/delete")) {
            try {
                long id = getIdFromPath(req.getPathInfo().replace("/delete", ""));
                categoryRepository.delete(id);
                resp.sendRedirect(getServletContext().getContextPath() + "/category");
            } catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private long getIdFromPath(String path) {
        Matcher matcher = pathParam.matcher(path);
        if (matcher.matches()) {
            try {
                return Long.parseLong(matcher.group(1));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        throw new IllegalArgumentException();
    }
}
