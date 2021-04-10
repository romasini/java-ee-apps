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
import java.util.List;

@ApplicationScoped
@Named
public class CustomerRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        if(count()==0) {
            try {
                userTransaction.begin();
                save(new Customer(null, "Petrov John", "petrov@mail.com", "85961234596","New-Vasuky, 123"));
                save(new Customer(null, "Vodkin John", "vodkin@mail.com", "85961564596","New-Vasuky, 456"));
                save(new Customer(null, "Sidorov John", "sidorov@mail.com", "85981234596","New-Vasuky, 758"));
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
    public void save(Customer customer){
        if(customer.getId() == null){
            entityManager.persist(customer);
        }

        entityManager.merge(customer);
    }

    @Transactional
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
