package ru.romasini.service;

import ru.romasini.persist.Category;
import ru.romasini.persist.CategoryRepository;
import ru.romasini.rest.CategoryResource;
import ru.romasini.service.dto.CategoryDto;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryResource {

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    public void save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryServiceImpl::createCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        return createCategoryDto(categoryRepository.findById(id));
    }

    @Override
    public void insert(CategoryDto categoryDto) {
        if(categoryDto.getId() != null){
            throw new IllegalArgumentException("Not null id in the inserted Category");
        }
        save(categoryDto);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        if(categoryDto.getId() == null){
            throw new IllegalArgumentException("Null id in the updated Category");
        }
        save(categoryDto);
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    private static CategoryDto createCategoryDto(Category category) {
        return new CategoryDto(category.getId(),
                category.getName());
    }
}
