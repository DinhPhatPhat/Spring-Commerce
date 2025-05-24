package space.dinhphatphat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.CategoryRepository;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }
}

