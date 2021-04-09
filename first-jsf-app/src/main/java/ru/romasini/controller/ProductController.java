package ru.romasini.controller;

import ru.romasini.persist.Product;
import ru.romasini.persist.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ProductController implements Serializable {

    @Inject
    private ProductRepository productRepository;

    private Product product;
    private List<Product> productList;

    public void preloadData(ComponentSystemEvent componentSystemEvent){
        this.productList = productRepository.findAll();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> findAll() {
        return productList;
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product.getId());
    }

    public String saveProduct() {
        productRepository.save(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.product = new Product();
        return "/product_form.xhtml?faces-redirect=true";
    }
}
