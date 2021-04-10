package ru.romasini.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
@Named
public class ProductRepository {

    @PersistenceContext(unitName = "ds") //name в persistence.xml
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        if(count()==0) {
            try {
                userTransaction.begin();
                save(new Product(null, "Product 1", "Descr 1", new BigDecimal(100)));
                save(new Product(null, "Product 2", "Descr 2", new BigDecimal(200)));
                save(new Product(null, "Product 3", "Descr 3", new BigDecimal(300)));
                userTransaction.commit();
            } catch (Exception e) {
                try {
                    userTransaction.rollback();
                } catch (SystemException systemException) {
                    throw new RuntimeException(systemException);
                }
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    public void save(Product product){
        if(product.getId() == null){
            entityManager.persist(product);
        }

        entityManager.merge(product);
    }

    @Transactional
    public void delete(Long id){
        entityManager.createNamedQuery("deleteProductById")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Product findById(Long id){
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll(){
        return entityManager.createNamedQuery("findAllProduct", Product.class).
                getResultList();
    }

    public long count(){
        return entityManager.createNamedQuery("countProduct", Long.class).getSingleResult();
    }
}
