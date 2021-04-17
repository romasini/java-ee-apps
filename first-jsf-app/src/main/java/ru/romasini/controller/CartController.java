package ru.romasini.controller;

import ru.romasini.service.CartService;
import ru.romasini.service.dto.ProductDto;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CartController implements Serializable {

    @EJB
    private CartService cartService;

    public void add(ProductDto productDto){
        cartService.add(productDto);
    }

    public List<ProductDto> findAll(){
        return cartService.findAll();
    }

    public void delete(ProductDto productDto){
        cartService.remove(productDto.getId());
    }


}
