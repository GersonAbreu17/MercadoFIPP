package com.example.MercadoFIPP.restcontroller;

import com.example.MercadoFIPP.db.dto.AnuncioDTO;
import com.example.MercadoFIPP.db.dto.PerguntasDTO;
import com.example.MercadoFIPP.db.entity.Ad;
import com.example.MercadoFIPP.db.entity.Foto;
import com.example.MercadoFIPP.db.entity.Pergunta;
import com.example.MercadoFIPP.db.entity.User;
import com.example.MercadoFIPP.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="api/user/anuncios")
public class AnunciosController {
    @Autowired
    private AdService adService;
    @Autowired
    private FotoService fotoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PerguntaService perguntaService;
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "anuncios")
    public ResponseEntity<Object> getAnuncios() {
        try {
            List<Ad> listaAnuncios = adService.getAll("");

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

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="get-many-categorias")
    public ResponseEntity<Object> getManyCategorias(String filtro){
        try{
            if(filtro == null)
                return ResponseEntity.ok(categoryService.getMany(""));
            else
                return ResponseEntity.ok(categoryService.getMany(filtro));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("erro na requisicao do filtro");
        }

    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "anunciosCategoria")
    public ResponseEntity<Object> getAnunciosCategoria(String cat) {
        try {
            List<Ad> listaAnuncios = adService.getAnuncioCategoria(categoryService.getCategory(cat));

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

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping(value = "addPerguntas")
    public ResponseEntity<Object> addPergunta(PerguntasDTO perguntasDTO)
    {
        try{
            Pergunta perg = perguntaService.add(new Pergunta(perguntasDTO.getPergunta(),adService.getAd(perguntasDTO.getIdAnuncio()),userService.getOne(perguntasDTO.getIdUser())));
            return ResponseEntity.ok(perg);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("erro");
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "get-one-anuncio")
    public ResponseEntity<Object> getAnuncio(Long id)
    {
        try{
            Ad ad = adService.getAd(id);
            return ResponseEntity.ok(ad);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("erro");
        }
    }

}
