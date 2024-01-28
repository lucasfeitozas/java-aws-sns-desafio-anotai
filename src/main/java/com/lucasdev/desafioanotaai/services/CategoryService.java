package com.lucasdev.desafioanotaai.services;

import com.lucasdev.desafioanotaai.domain.category.Category;
import com.lucasdev.desafioanotaai.domain.category.CategoryDTO;
import com.lucasdev.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.lucasdev.desafioanotaai.repositories.CategoryRepository;
import com.lucasdev.desafioanotaai.services.aws.AwsSnsService;
import com.lucasdev.desafioanotaai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository repository, AwsSnsService snsService) {
        this.repository = repository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData) {
        Category newCategory = new Category(categoryData);

        newCategory = this.repository.save(newCategory);

        this.snsService.publish(new MessageDTO(newCategory.toString()));

        return newCategory;
    }

    public Category update(String id, CategoryDTO categoryData) {
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if (!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if (!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.snsService.publish(new MessageDTO(category.toString()));

        this.repository.save(category);

        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Optional<Category> getById(String id) {
        return this.repository.findById(id);
    }
}