package ru.romasini.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.Product;
import ru.romasini.persist.ProductRepository;

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

        sce.getServletContext().setAttribute("productRepository", productRepository);
    }
}
