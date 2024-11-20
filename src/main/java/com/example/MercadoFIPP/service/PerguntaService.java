package com.example.MercadoFIPP.service;

import com.example.MercadoFIPP.db.entity.Pergunta;
import com.example.MercadoFIPP.db.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {
    @Autowired
    private PerguntaRepository perguntaRepository;

    public List<Pergunta> getMany(String filtro){
        return perguntaRepository.findWithFilter(filtro);
    }

    public List<Pergunta> getPerguntas(Long id){
        return perguntaRepository.findByIdAnuncio(id);
    }

    public Pergunta add(Pergunta pergunta){
        try{
            pergunta = perguntaRepository.save(pergunta);
        }
        catch (Exception e) {
            pergunta = null;
        }
        return pergunta;
    }

    public Exception delete(Long id){
        try{
            List<Pergunta> listaPerguntas = perguntaRepository.findByIdAnuncio(id);
            for(Pergunta pergunta : listaPerguntas)
                perguntaRepository.deleteById(pergunta.getId());
            return null;
        } catch (Exception e) {
            return e;
        }
    }

}
