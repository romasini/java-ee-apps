package ru.romasini;

import ru.romasini.service.dto.ProductDto;

import java.util.List;

public interface ProductServiceRemote {

    List<ProductDto> findAllRemote();

}
