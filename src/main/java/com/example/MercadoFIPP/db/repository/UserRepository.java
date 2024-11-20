package com.example.MercadoFIPP.db.repository;

import com.example.MercadoFIPP.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value="SELECT * FROM usuario WHERE lower(usr_name) LIKE %:filter%",nativeQuery=true)
    List<User> findWithFilter(@Param("filter")String filter);

    User findByName(String name);

    @Query(value = "SELECT * FROM usuario u WHERE u.usr_name = :name and u.usr_pass = :pass",nativeQuery = true)
    User getUsuario(@Param("name") String name,@Param("pass") String pass);

    @Query(value = "SELECT * FROM USUARIO U WHERE U.USR_NAME = :filtro",nativeQuery = true)
    User getUsuarioFiltro(@Param("filtro") String filtro);
}
