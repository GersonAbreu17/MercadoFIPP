package com.example.MercadoFIPP.db.dto;



import com.example.MercadoFIPP.db.entity.Ad;
import com.example.MercadoFIPP.db.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnuncioDTO {
    private String titulo;
    private LocalDate data;
    private Double preco;
    private long categoria;
    private String descricao;
    private String usuario;
    private User ObjUsuario;
    private long Id;
    private String NomeCategoria;
    private List<String> fotos;
    private List<String> perguntas;

    public AnuncioDTO(Ad anuncio, List<String> fotos, List<String> perguntas, String categoria) {
        this.Id = anuncio.getId();
        this.titulo = anuncio.getTitle();
        this.descricao = anuncio.getDescr();
        this.preco = anuncio.getPrice();
        this.NomeCategoria = categoria;
        this.fotos = fotos;
        this.perguntas = perguntas;
        this.data = anuncio.getDate();
        this.usuario = anuncio.getUser().getName();
    }

    public AnuncioDTO(){

    }

    // Getters e Setters
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNomeCategoria() {
        return NomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        NomeCategoria = nomeCategoria;
    }

    public List<String> getFotos() {
        if(this.fotos == null)
            fotos = new ArrayList<>();
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        if(this.fotos == null)
            fotos = new ArrayList<>();
        this.fotos = fotos;
    }

    public List<String> getPerguntas() {
        if(this.perguntas == null)
            perguntas = new ArrayList<>();
        return perguntas;
    }

    public void setPerguntas(List<String> perguntas) {
        if(this.perguntas == null)
            perguntas = new ArrayList<>();
        this.perguntas = perguntas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public long getCategoria() {
        return categoria;
    }

    public void setCategoria(long categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public User getObjUsuario() {
        return ObjUsuario;
    }

    public void setObjUsuario(User objUsuario) {
        ObjUsuario = objUsuario;
    }
}
