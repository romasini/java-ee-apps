package ru.romasini.persist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    public void save(Customer customer){
        if(customer.getId() == null){
            entityManager.persist(customer);
        }

        entityManager.merge(customer);
    }

    public void delete(Long id){
        entityManager.createNamedQuery("deleteCustomerById")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Customer findById(Long id){
        return entityManager.find(Customer.class, id);
    }

    public List<Customer> findAll(){
        return entityManager.createNamedQuery("findAllCustomer", Customer.class).
                getResultList();
    }

    public long count(){
        return entityManager.createNamedQuery("countCustomer", Long.class).getSingleResult();
    }
}
