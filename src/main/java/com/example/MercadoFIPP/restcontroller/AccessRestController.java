package com.example.MercadoFIPP.restcontroller;

import com.example.MercadoFIPP.db.dto.LoginDTO;
import com.example.MercadoFIPP.db.entity.User;
import com.example.MercadoFIPP.db.repository.UserRepository;
import com.example.MercadoFIPP.restcontroller.security.JWTTokenProvider;
import com.example.MercadoFIPP.service.UserService;
import io.jsonwebtoken.Claims;
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

    @Autowired
    UserService userService;

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

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping("/verificacao")
    public ResponseEntity<Object> verificacao(@RequestParam("token") String token) {
        try {
            // Extrai os dados do token JWT
            Claims claim = JWTTokenProvider.getAllClaimsFromToken(token);
            if (claim == null || claim.getSubject() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
            }

            // Busca o usuário pelo e-mail (subject no token)
            User user = userService.getUsuarioAnuncio(claim.getSubject());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            // Retorna os dados do usuário
            user.setPass("");
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            // Lida com possíveis exceções
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a solicitação: " + e.getMessage());
        }
    }


}
