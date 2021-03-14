package ru.romasini.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {

    private Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

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
