package com.example.MercadoFIPP.service;

import com.example.MercadoFIPP.db.entity.Category;
import com.example.MercadoFIPP.db.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getMany(String filtro){
        return categoryRepository.findWithFilter(filtro);
    }

    public Category getCategory(String filtro)
    {
        return  categoryRepository.findByFiltro(filtro);
    }

    public Category getOne(Long id){
        if(id == null)
            return null;
        return categoryRepository.getId(id);
    }

    public Category add(Category category){
        try{
            category = categoryRepository.save(category);
        }
        catch (Exception e) {
            category = null;
        }
        return category;
    }

    public boolean delete(Long id){
        try{
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
