package ru.romasini.controller;

import ru.romasini.persist.Category;
import ru.romasini.persist.CategoryRepository;
import ru.romasini.service.ProductService;
import ru.romasini.service.dto.ProductDto;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ProductController implements Serializable {

    @EJB
    private ProductService productService;

    @EJB
    private CategoryRepository categoryRepository;

    private ProductDto product;
    private List<ProductDto> productList;
    private List<Category> categoryList;

    public void preloadData(ComponentSystemEvent componentSystemEvent){
        this.productList = productService.findAll();
        this.categoryList = categoryRepository.findAll();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public List<ProductDto> findAll() {
        return productList;
    }

    public String editProduct(ProductDto product) {
        this.product = product;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductDto product) {
        productService.delete(product.getId());
    }

    public String saveProduct() {
        productService.save(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.product = new ProductDto();
        return "/product_form.xhtml?faces-redirect=true";
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }
}
