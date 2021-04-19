package ru.romasini.persist;

import ru.romasini.persist.meta.Category_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    public void save(Category category){
        if(category.getId() == null){
            entityManager.persist(category);
        }

        entityManager.merge(category);
    }

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

    public Category getReference(Long id) {
        return entityManager.getReference(Category.class, id);
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
