package ru.romasini.service;

import ru.romasini.service.dto.ProductDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductService {
    void save(ProductDto product);

    void delete(Long id);

    ProductDto findById(Long id);

    List<ProductDto> findAll();


}
