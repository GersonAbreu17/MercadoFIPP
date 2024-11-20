package com.example.MercadoFIPP.db.repository;

import com.example.MercadoFIPP.db.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta,Long> {
    @Query(value="SELECT * FROM pergunta_anuncio WHERE lower(per_text) LIKE %:filter% or lower(per_resp) LIKE %:filter%",nativeQuery=true)
    List<Pergunta> findWithFilter(@Param("filter")String filter);

    @Query(value = "SELECT * FROM PUBLIC.PERGUNTA_ANUNCIO PA WHERE PA.ANU_ID = :id",nativeQuery = true)
    List<Pergunta> findByIdAnuncio(@Param("id") Long id);

}
