package ru.romasini.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@Named
public class ProductRepository {

    private Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        save(new Product(null, "Product 1", "Description 1", new BigDecimal(100)));
        save(new Product(null, "Product 2", "Description 2", new BigDecimal(200)));
        save(new Product(null, "Product 3", "Description 3", new BigDecimal(300)));
        save(new Product(null, "Продукт 4", "Description 4", new BigDecimal(300)));
    }

    public void save(Product product){
        if(product.getId() == null){
            product.setId(identity.incrementAndGet());
        }

        productMap.put(product.getId(), product);
    }

    public void delete(Long id){
        productMap.remove(id);
    }

    public Product findById(Long id){
        return productMap.get(id);
    }

    public List<Product> findAll(){
        return new ArrayList<>(productMap.values());
    }
}
