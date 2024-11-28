package com.example.MercadoFIPP.service;

import com.example.MercadoFIPP.db.entity.Ad;
import com.example.MercadoFIPP.db.entity.Foto;
import com.example.MercadoFIPP.db.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepository;

    public List<Foto> getMany(){
        return fotoRepository.findAll();
    }

    public Foto getOne(Long id){
        Optional <Foto> foto = fotoRepository.findById(id);
        if(foto.isPresent())
            return foto.get();
        return null;
    }

    public Foto add(Foto foto){
        try{
            foto = fotoRepository.save(foto);
        }
        catch (Exception e) {
            foto = null;
        }
        return foto;
    }

    public Exception delete(Long id){
        try{
            List<Foto> listaFotos = fotoRepository.findByIdAnuncio(id);
            for(Foto foto : listaFotos)
                fotoRepository.deleteById(foto.getId());
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    public List<Foto> saveFotos(List<String> caminhosDasFotos, Ad anuncio) {
        List<Foto> fotos = new ArrayList<>();
        for(String caminho : caminhosDasFotos)
            fotos.add(fotoRepository.save(new Foto(caminho,anuncio)));
        return fotos;
    }

    public List<Foto> getFotos(Long id) {
        return fotoRepository.findByIdAnuncio(id);
    }

    public List<Foto> updateFotos(List<String> caminhosDasFotos, Ad anuncio) {
        List<Foto> fotos = new ArrayList<>();
        List<Foto> fotos2 = fotoRepository.findByIdAnuncio(anuncio.getId());

        for(Foto foto : fotos2)
        {
            if(!caminhosDasFotos.contains(foto.getFilename()))
            {
                fotoRepository.delete(foto);
            }
        }
        for(String filename : caminhosDasFotos)
        {
            fotos.add(fotoRepository.save(new Foto(filename, anuncio)));
        }
        return fotos;
    }
}
