package com.example.MercadoFIPP.restcontroller;

import com.example.MercadoFIPP.db.dto.LoginDTO;
import com.example.MercadoFIPP.db.entity.User;
import com.example.MercadoFIPP.db.repository.UserRepository;
import com.example.MercadoFIPP.restcontroller.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="access/")
public class AccessRestController {
    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        User user = userRepository.getUsuario(loginDTO.getName(), loginDTO.getPass());
        if (user != null) {
            String token = JWTTokenProvider.getToken(user.getName(), "" + user.getLevel());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("nivel", user.getLevel());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}
