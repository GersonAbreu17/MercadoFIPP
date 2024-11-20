package com.example.MercadoFIPP.db.repository;
import com.example.MercadoFIPP.db.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto,Long> {
    @Query(value = "SELECT * FROM PUBLIC.FOTO_ANUNCIO FA WHERE FA.ANU_ID = :id",nativeQuery = true)
    List<Foto> findByIdAnuncio(@Param("id") Long id);
}
