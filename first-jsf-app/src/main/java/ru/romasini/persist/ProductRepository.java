package ru.romasini.persist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductRepository {

    @PersistenceContext(unitName = "ds") //name в persistence.xml
    private EntityManager entityManager;

    public void save(Product product){
        if(product.getId() == null){
            entityManager.persist(product);
        }

        entityManager.merge(product);
    }

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

    public List<Product> findByName(String name){
        return entityManager.createNamedQuery("findByName", Product.class).
                setParameter("name", name + "%").
                getResultList();
    }

    public List<Product> findByCategoryId(Long id){
        return entityManager.createNamedQuery("findByCategoryId", Product.class).
                setParameter("id", id).
                getResultList();
    }

    public long count(){
        return entityManager.createNamedQuery("countProduct", Long.class).getSingleResult();
    }

    public List<Product> findAllWithCategoryFetch() {
        return entityManager.createNamedQuery("findAllWithCategoryFetch", Product.class)
                .getResultList();
    }
}
