package com.example.MercadoFIPP.service;

import com.example.MercadoFIPP.db.dto.AdDTO;
import com.example.MercadoFIPP.db.entity.Ad;
import com.example.MercadoFIPP.db.entity.Category;
import com.example.MercadoFIPP.db.entity.Foto;
import com.example.MercadoFIPP.db.entity.Pergunta;
import com.example.MercadoFIPP.db.repository.AdRepository;
import com.example.MercadoFIPP.db.repository.FotoRepository;
import com.example.MercadoFIPP.db.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private PerguntaRepository perguntaRepository;
    @Autowired
    private FotoRepository fotoRepository;

    public Ad getAd(Long id){
        List <Ad> ad =  adRepository.getUm(id);
        return ad.isEmpty() ? null :  ad.getFirst();
    }
    public List<Ad> getAll(String filtro)
    {
        if(filtro.isEmpty())
            return adRepository.findAll();
        else
            return adRepository.findWithFilter(filtro);
    }

    public List<Ad> getAnunciosVendedor(long id){
        return adRepository.getAnunciosVendedor(id);
    }
    public Ad addAd(Ad ad)
    {
        try{
            ad = adRepository.save(ad);
            if(!ad.getPerguntas().isEmpty())
                for(Pergunta pergunta: ad.getPerguntas())
                {
                    pergunta.setAd(ad);
                    perguntaRepository.save(pergunta);
                }

            if(!ad.getFotos().isEmpty())
                for(Foto foto: ad.getFotos())
                {
                    foto.setAd(ad);
                    fotoRepository.save(foto);
                }
        }
        catch (Exception e) {
            ad = null;
        }
        return ad;
    }

    public AdDTO addAdDTO(AdDTO adDTO)
    {
        try{
            Ad ad = new Ad();
            ad.setTitle(adDTO.getTitle());
            ad.setDate(adDTO.getDate());
            ad.setDescr(adDTO.getDescr());
            ad.setPrice(adDTO.getPrice());
            ad.setUser(adDTO.getUser());
            ad.setCategory(adDTO.getCategory());
            ad = adRepository.save(ad);
        }
        catch (Exception e) {
            adDTO = null;
        }
        return adDTO;
    }

    public Exception delAd(Long id){
        try{
            adRepository.deleteById(id);
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    public List<Ad> getUltimosAnuncio() {
        return adRepository.getUltimosAnuncio();
    }

    public void updateAd(Ad existingAd) {
        adRepository.save(existingAd);
    }

    public List<Ad> getAdsByUserId(Long userId) {
        return adRepository.getAdsByUserId(userId);
    }

    public List<Ad> getAnuncioCategoria(String filtro, Category category) {
        if(category == null && filtro == null)
            return adRepository.findAll();
        if(filtro == null)
            return adRepository.getAnuncioCategoria(category.getId());
        if(category == null)
            return adRepository.findWithFilter(filtro);
        return adRepository.getAnuncioCategoriaEFiltro(filtro ,category.getId());
    }
}
