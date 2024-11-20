package com.example.MercadoFIPP.restcontroller;

import com.example.MercadoFIPP.db.dto.AnuncioDTO;
import com.example.MercadoFIPP.db.entity.Ad;
import com.example.MercadoFIPP.db.entity.Foto;
import com.example.MercadoFIPP.db.entity.Pergunta;
import com.example.MercadoFIPP.service.AdService;
import com.example.MercadoFIPP.service.CategoryService;
import com.example.MercadoFIPP.service.FotoService;
import com.example.MercadoFIPP.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="/inicio")
public class InicioRestController {
    @Autowired
    private AdService adService;
    @Autowired
    private FotoService fotoService;
    @Autowired
    private PerguntaService perguntaService;
    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "ultimosAnuncios")
    public ResponseEntity<Object> getUltimosAnunscios() {
        try {
            List<Ad> listaAnuncios = adService.getUltimosAnuncio();

            if (listaAnuncios.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<AnuncioDTO> responseDTOs = new ArrayList<>();

            for (Ad anuncio : listaAnuncios) {
                List<String> perguntas = perguntaService.getPerguntas(anuncio.getId())
                        .stream()
                        .map(Pergunta::getText)
                        .toList();

                String categoria = categoryService.getOne(anuncio.getCategory().getId()).getName();

                List<String> fotos = fotoService.getFotos(anuncio.getId())
                        .stream()
                        .map(Foto::getFilename)
                        .toList();

                responseDTOs.add(new AnuncioDTO(anuncio, fotos, perguntas, categoria));
            }
            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar anúncios: " + e.getMessage());
        }
    }
}
