package com.example.MercadoFIPP.restcontroller;
import com.example.MercadoFIPP.db.entity.User;
import com.example.MercadoFIPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/admin/usuarios")
public class UserRestController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="get-many")
    public ResponseEntity<Object> getMany(String filtro){
        try{
            return ResponseEntity.ok(userService.getMany(filtro));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("erro na requisicao do filtro");
        }

    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping(value = "get-login")
    public ResponseEntity<Object> getLogin(@RequestBody User userBody) {
        try {
            User user = userService.getUsuario(userBody);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="get-one")
    public ResponseEntity<Object> getOne(Long id)
    {
        User user = userService.getOne(id);
        try {
            if(user != null)
                return ResponseEntity.ok(user);
            else
                return ResponseEntity.status(404).body("User não encontrada!");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("erro");
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping(value="add")
    public ResponseEntity<Object> add(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.add(user));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body("erro");
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="delete")
    public ResponseEntity<Object> delete(@RequestParam Long id)
    {
        if(userService.delete(id))
            return ResponseEntity.ok("ok");
        else
            return ResponseEntity.badRequest().body("erro");
    }
}
