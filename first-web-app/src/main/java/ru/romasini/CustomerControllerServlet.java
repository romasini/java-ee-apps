package ru.romasini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.Category;
import ru.romasini.persist.Customer;
import ru.romasini.persist.CustomerRepository;
import ru.romasini.persist.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/customer/*")
public class CustomerControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerServlet.class);

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");

    private CustomerRepository customerRepository;

    @Override
    public void init() throws ServletException {
        customerRepository = (CustomerRepository) getServletContext().getAttribute("customerRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            req.setAttribute("customers", customerRepository.findAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/new")) {
            req.setAttribute("customer", new Customer());
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer_form.jsp").forward(req, resp);
        } else {
            try {
                long id = getIdFromPath(req.getPathInfo());
                Customer customer = customerRepository.findById(id);
                if (customer == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                req.setAttribute("customer", customer);
                getServletContext().getRequestDispatcher("/WEB-INF/views/customer_form.jsp").forward(req, resp);
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
                Customer customer = new Customer(
                        strId.isEmpty() ? null : Long.parseLong(strId),
                        req.getParameter("name"),
                        req.getParameter("email"),
                        req.getParameter("phone"),
                        req.getParameter("address")
                        );
                customerRepository.save(customer);
                resp.sendRedirect(getServletContext().getContextPath() + "/customer");
            } catch (NumberFormatException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if(req.getPathInfo().startsWith("/delete")) {
            try {
                long id = getIdFromPath(req.getPathInfo().replace("/delete", ""));
                customerRepository.delete(id);
                resp.sendRedirect(getServletContext().getContextPath() + "/customer");
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
