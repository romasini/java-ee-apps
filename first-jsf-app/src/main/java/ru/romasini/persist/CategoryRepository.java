package ru.romasini.persist;

import ru.romasini.persist.meta.Category_;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

@ApplicationScoped
@Named
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        if(count()==0) {
            try {
                userTransaction.begin();
                save(new Category(null, "Category 1"));
                save(new Category(null, "Category 2"));
                save(new Category(null, "Category 3"));
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
    public void save(Category category){
        if(category.getId() == null){
            entityManager.persist(category);
        }

        entityManager.merge(category);
    }

    @Transactional
    public void delete(Long id){
        entityManager.createNamedQuery("deleteCategoryById")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Category findById(Long id){
        return entityManager.find(Category.class, id);
    }

    public List<Category> findAll(){
        return entityManager.createNamedQuery("findAllCategory", Category.class).
                getResultList();
    }

    public List<Category> findAllWithFilter(String filterName){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> c = query.from(Category.class);
        ParameterExpression<String> fn = cb.parameter(String.class);
        Predicate condition = cb.like(c.get(Category_.name), fn);
        query.select(c).where(condition);
        TypedQuery<Category> q = entityManager.createQuery(query);
        q.setParameter(fn, filterName);
        return q.getResultList();
    }

    public long count(){
        return entityManager.createNamedQuery("countCategory", Long.class).getSingleResult();
    }
}
