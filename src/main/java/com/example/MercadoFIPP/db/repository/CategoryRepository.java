package com.example.MercadoFIPP.db.repository;

import com.example.MercadoFIPP.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value="SELECT * FROM categoria WHERE lower(cat_name) LIKE %:filter%",nativeQuery=true)
    List<Category> findWithFilter(@Param("filter")String filter);

    @Query(value = "SELECT * FROM public.categoria c where c.cat_name = :filtro",nativeQuery = true)
    Category findByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM public.categoria c where c.cat_id = :filtro",nativeQuery = true)
    Category getId(@Param("filtro") long filtro);
}
