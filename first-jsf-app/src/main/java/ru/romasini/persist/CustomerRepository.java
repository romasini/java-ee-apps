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
public class CustomerRepository {

    private Map<Long, Customer> customerMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        save(new Customer(null, "Petrov John", "petrov@mail.com", "85961234596","New-Vasuky, 123"));
        save(new Customer(null, "Vodkin John", "vodkin@mail.com", "85961564596","New-Vasuky, 456"));
        save(new Customer(null, "Sidorov John", "sidorov@mail.com", "85981234596","New-Vasuky, 758"));
    }


    public void save(Customer customer){
        if(customer.getId() == null){
            customer.setId(identity.incrementAndGet());
        }

        customerMap.put(customer.getId(), customer);
    }

    public void delete(Long id){
        customerMap.remove(id);
    }

    public Customer findById(Long id){
        return customerMap.get(id);
    }

    public List<Customer> findAll(){
        return new ArrayList<>(customerMap.values());
    }
}
