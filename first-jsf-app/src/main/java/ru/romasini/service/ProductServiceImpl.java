package ru.romasini.service;

import ru.romasini.ProductServiceRemote;
import ru.romasini.persist.Category;
import ru.romasini.persist.CategoryRepository;
import ru.romasini.persist.Product;
import ru.romasini.persist.ProductRepository;
import ru.romasini.service.dto.ProductDto;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote(ProductServiceRemote.class)
public class ProductServiceImpl implements ProductService, ProductServiceRemote{

    @EJB
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @TransactionAttribute
    public void save(ProductDto product) {
        Category category = categoryRepository.getReference(product.getCategoryId());
        Product product1 = new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), category);
        productRepository.save(product1);
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto findById(Long id) {
        return  createProductDtoWithCategory(productRepository.findById(id));
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductServiceImpl::createProductDtoWithCategory)
                .collect(Collectors.toList());
    }


    private static ProductDto createProductDtoWithCategory(Product product) {
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null);
    }

    private static ProductDto createProductDto(Product product) {
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                null,
                null);
    }

    @Override
    public List<ProductDto> findAllRemote() {
        return findAllWithCategoryFetch();
    }

    @Override
    public List<ProductDto> findAllWithCategoryFetch() {
        return productRepository.findAllWithCategoryFetch().stream()
                .map(ProductServiceImpl::createProductDtoWithCategory)
                .collect(Collectors.toList());
    }
}
