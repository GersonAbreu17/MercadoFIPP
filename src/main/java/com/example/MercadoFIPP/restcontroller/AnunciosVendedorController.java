package com.example.MercadoFIPP.restcontroller;

import com.example.MercadoFIPP.db.dto.AdDTO;
import com.example.MercadoFIPP.db.dto.AnuncioDTO;
import com.example.MercadoFIPP.db.dto.PerguntasDTO;
import com.example.MercadoFIPP.db.entity.*;
import com.example.MercadoFIPP.restcontroller.security.JWTTokenProvider;
import com.example.MercadoFIPP.service.*;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="api/vendedor/meusanuncios")
public class AnunciosVendedorController {
    @Autowired
    private AdService adService;
    @Autowired
    private UserService userService;
    @Autowired
    private FotoService fotoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PerguntaService perguntaService;
    @Value("${upload.dir}")
    private String uploadDir;
    
    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "anuncios")
    public ResponseEntity<Object> getAnuncios(String token) {
        try {
            Claims claim = JWTTokenProvider.getAllClaimsFromToken(token);
            User user = userService.getUsuarioAnuncio(claim.getSubject());
            List<Ad> listaAnuncios = adService.getAnunciosVendedor(user.getId());

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
    @GetMapping(value = "anunciosCategoria")
    public ResponseEntity<Object> getAnunciosCategoria(String catId, String titulo) {
        try {
            List<Ad> listaAnuncios = adService.getAnuncioCategoria(titulo, categoryService.getCategory(catId));

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
    @PostMapping(value = "criarAnuncio",consumes = {"multipart/form-data"})
    public ResponseEntity<Object> criarAnuncio(@RequestPart("anuncio") AnuncioDTO anuncioDTO, @RequestPart("fotos") MultipartFile[] fotos) {
        try{
            Claims claim = JWTTokenProvider.getAllClaimsFromToken(anuncioDTO.getUsuario());
            String usuario = claim.getSubject();
            if (fotos.length > 3) {
                return ResponseEntity.badRequest().body("Você pode enviar no máximo 3 fotos.");
            }

            List<String> caminhosDasFotos = new ArrayList<>();

            try {
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                for (MultipartFile foto : fotos) {
                    String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                    Path caminhoFoto = uploadPath.resolve(fileName);

                    Files.write(caminhoFoto, foto.getBytes());

                    caminhosDasFotos.add(fileName);
                }
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Erro ao salvar as fotos: " + e.getMessage());
            }

            Ad anuncio = new Ad();
            anuncio.setUser(userService.getUsuarioAnuncio(usuario));
            anuncio.setTitle(anuncioDTO.getTitulo());
            anuncio.setDate(anuncioDTO.getData());
            anuncio.setPrice(anuncioDTO.getPreco());
            anuncio.setDescr(anuncioDTO.getDescricao());
            anuncio.setCategory(categoryService.getOne(anuncioDTO.getCategoria()));
            anuncio = adService.addAd(anuncio);
            fotoService.saveFotos(caminhosDasFotos,anuncio);

            return ResponseEntity.ok("Anúncio criado com sucesso!");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Erro na requisição");
        }
    }

    @Transactional
    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value="delete")
    public ResponseEntity<Object> delete(Long id)
    {
        try
        {
            perguntaService.delete(id) ;
            fotoService.delete(id);
            fotoService.delete(id);
            adService.delAd(id);
            return ResponseEntity.ok().body("Anuncio deletado");
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body("erro");
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PutMapping(value = "update-anuncio")
    public ResponseEntity<Object> update(@RequestBody AdDTO anuncio) {
        try {
            Ad existingAd = adService.getAd(anuncio.getId());
            if (existingAd == null) {
                return ResponseEntity.badRequest().body("Anúncio não encontrado.");
            }

            existingAd.setTitle(anuncio.getTitle());
            existingAd.setDescr(anuncio.getDescr());
            existingAd.setPrice(anuncio.getPrice());
            existingAd.setCategory(new Category(anuncio.getId()));

            adService.updateAd(existingAd);

            return ResponseEntity.ok(existingAd);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar anúncio: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping(value = "get-user-ads")
    public ResponseEntity<Object> getUserAd(@RequestHeader("Authorization") String token) {
        try {
            long id = userService.pegarIdToken(token);
            List<Ad> userAds = adService.getAdsByUserId(id);
            return ResponseEntity.ok(userAds);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar anúncios: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @GetMapping("/uploads/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            Path file = Paths.get("uploads/").resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping(value = "addResposta")
    public ResponseEntity<Object> addResposta(@RequestBody PerguntasDTO perguntasDTO)
    {
        try{
            Pergunta perg = perguntaService.getPergunta(perguntasDTO.getIdPergunta());
            perg.setResp(perguntasDTO.getResposta());
            perg = perguntaService.add(perg);
            return ResponseEntity.ok(perg);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("erro");
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

    @CrossOrigin(origins = {"http://127.0.0.1:5500/", "http://localhost:5500"})
    @GetMapping(value = "getPerguntasVendedor")
    public ResponseEntity<Object> getPerguntasVendedor(Long id)
    {
        try{
            List<Pergunta> perguntas =  perguntaService.getPerguntas(id);
            return ResponseEntity.ok(perguntas);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Erro");
        }
    }
}
