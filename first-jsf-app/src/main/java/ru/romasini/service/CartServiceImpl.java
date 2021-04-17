package ru.romasini.service;

import ru.romasini.service.dto.ProductDto;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateful
public class CartServiceImpl implements CartService{

    private final Map<Long, ProductDto> productMap = new ConcurrentHashMap<Long, ProductDto>();

    @Override
    public void add(ProductDto productDto) {
        if(!productMap.containsKey(productDto.getId())){
            productMap.put(productDto.getId(), productDto);
        }
    }

    @Override
    public void remove(Long id) {
        productMap.remove(id);
    }

    @Override
    public List<ProductDto> findAll() {
        return new ArrayList<ProductDto>(productMap.values());
    }
}
