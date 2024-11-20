package com.example.MercadoFIPP.service;

import com.example.MercadoFIPP.db.entity.User;
import com.example.MercadoFIPP.db.repository.UserRepository;
import com.example.MercadoFIPP.restcontroller.security.JWTTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getMany(String filtro){
        return userRepository.findWithFilter(filtro.toLowerCase());
    }

    public User getUsuario(User user)
    {
        return userRepository.getUsuario(user.getName(), user.getPass());
    }

    public User getUsuarioAnuncio(String filtro)
    {
        return userRepository.getUsuarioFiltro(filtro);
    }

    public User getOne(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        return null;
    }

    public User add(User user){
        try{
            user = userRepository.save(user);
        }
        catch (Exception e) {
            user = null;
        }
        return user;
    }

    public boolean delete(Long id){
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long pegarIdToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return -1;
        }

        String jwt = token.substring(7);

        if (!JWTTokenProvider.verifyToken(jwt)) {
            return -1;
        }

        Claims claims = JWTTokenProvider.getAllClaimsFromToken(jwt);
        Long userId = claims.get("userId", Long.class);

        if (userId == null) {
            return -1;
        }

        return userId;
    }
}
