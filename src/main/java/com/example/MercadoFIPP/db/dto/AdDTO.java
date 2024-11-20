package com.example.MercadoFIPP.db.dto;



import com.example.MercadoFIPP.db.entity.Category;
import com.example.MercadoFIPP.db.entity.Foto;
import com.example.MercadoFIPP.db.entity.Pergunta;
import com.example.MercadoFIPP.db.entity.User;

import java.time.LocalDate;
import java.util.List;

public class AdDTO {
    private Long Id;
    private String title;
    private LocalDate date;
    private String descr;
    private double price;
    private Category category;
    private User user;
    private List<Foto> fotos;
    private List<Pergunta> perguntas;

    public AdDTO() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }
}
