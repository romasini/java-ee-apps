package ru.romasini.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Startup listener");

        ProductRepository productRepository = new ProductRepository();
        productRepository.save(new Product(null, "Product 1", "Descr 1", new BigDecimal(100)));
        productRepository.save(new Product(null, "Product 2", "Descr 2", new BigDecimal(200)));
        productRepository.save(new Product(null, "Product 3", "Descr 3", new BigDecimal(300)));

        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.save(new Category(null, "Category 1"));
        categoryRepository.save(new Category(null, "Category 2"));
        categoryRepository.save(new Category(null, "Category 3"));

        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.save(new Customer(null, "Petrov John", "petrov@mail.com", "85961234596","New-Vasuky, 123"));
        customerRepository.save(new Customer(null, "Vodkin John", "vodkin@mail.com", "85961564596","New-Vasuky, 456"));
        customerRepository.save(new Customer(null, "Sidorov John", "sidorov@mail.com", "85981234596","New-Vasuky, 758"));

        sce.getServletContext().setAttribute("productRepository", productRepository);
        sce.getServletContext().setAttribute("categoryRepository", categoryRepository);
        sce.getServletContext().setAttribute("customerRepository", customerRepository);
    }
}
