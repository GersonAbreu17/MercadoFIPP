package com.example.MercadoFIPP.db.repository;

import com.example.MercadoFIPP.db.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface AdRepository extends JpaRepository<Ad,Long> {
    @Query(value="SELECT * FROM anuncio WHERE lower(anu_title) LIKE %:filter% or lower(anu_desc) LIKE %:filter%",nativeQuery=true)
    List<Ad> findWithFilter(@Param("filter")String filter);

    @Query(value = "SELECT * FROM PUBLIC.ANUNCIO ORDER BY ANU_DATE DESC LIMIT 5",nativeQuery = true)
    List<Ad> getUltimosAnuncio();

    @Query(value = "SELECT * FROM PUBLIC.ANUNCIO A WHERE A.ANU_ID = :id",nativeQuery = true)
    List<Ad> getAdsByUserId(@Param("id") Long userId);

    @Query(value = "SELECT * FROM PUBLIC.ANUNCIO A WHERE A.USR_ID = :id",nativeQuery = true)
    List<Ad> getAnunciosVendedor(@Param("id") long id);

    @Query(value = "SELECT * FROM PUBLIC.ANUNCIO A WHERE A.CAT_ID = :id",nativeQuery = true)
    List<Ad> getAnuncioCategoria(@Param("id") Long id);

    @Query(value = "SELECT * FROM PUBLIC.ANUNCIO A WHERE A.ANU_ID = :id",nativeQuery = true)
    List<Ad> getUm(@Param("id") Long id);
}
