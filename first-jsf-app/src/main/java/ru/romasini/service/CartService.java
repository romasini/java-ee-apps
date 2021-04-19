package ru.romasini.service;

import ru.romasini.service.dto.ProductDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CartService {

    void add(ProductDto productDto);

    void remove(Long id);

    List<ProductDto> findAll();

}
