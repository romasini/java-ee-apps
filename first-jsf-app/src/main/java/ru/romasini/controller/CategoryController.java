package ru.romasini.controller;

import ru.romasini.persist.Category;
import ru.romasini.persist.CategoryRepository;
import ru.romasini.persist.Product;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;
    private Category category;
    private List<Category> categoryList;
    private String filterName;

    public void preloadData(ComponentSystemEvent componentSystemEvent){
        this.categoryList = categoryRepository.findAll();
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> findAll() {
        return categoryList;
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category_form.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category.getId());
    }

    public String saveCategory() {
        categoryRepository.save(category);
        return "/category.xhtml?faces-redirect=true";
    }

    public String addCategory() {
        this.category = new Category();
        return "/category_form.xhtml?faces-redirect=true";
    }
}
