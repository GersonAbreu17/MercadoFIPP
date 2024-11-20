package com.example.MercadoFIPP.restcontroller;
import com.example.MercadoFIPP.db.entity.Category;
import com.example.MercadoFIPP.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/admin/categorias")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="get-many")
    public ResponseEntity<Object> getMany(String filtro){
        try{
            if(filtro == null)
                return ResponseEntity.ok(categoryService.getMany(""));
            else
                return ResponseEntity.ok(categoryService.getMany(filtro));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("erro na requisicao do filtro");
        }

    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="get-one")
    public ResponseEntity<Object> getOne(Long id)
    {
        Category category = categoryService.getOne(id);
        try {
            if(category != null)
                return ResponseEntity.ok(category);
            else
                return ResponseEntity.status(404).body("Category não encontrada!");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("erro");
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping(value="add")
    public ResponseEntity<Object> add(@RequestBody Category category) {
        if(categoryService.add(category) != null)
            return ResponseEntity.ok(category);
        else
            return ResponseEntity.badRequest().body("erro");
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="delete")
    public ResponseEntity<Object> delete(Long id)
    {
        if(categoryService.delete(id))
            return ResponseEntity.ok("ok");
        else
            return ResponseEntity.badRequest().body("erro");
    }
}
