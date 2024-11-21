package com.example.MercadoFIPP.db.entity;

import jakarta.persistence.*;

@Table(name="pergunta_anuncio")
@Entity
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="per_id")
    private Long id;
    @Column(name="per_text")
    private String text;
    @Column(name="per_resp")
    private String resp;

    //@OneToOne(fetch=FetchType.LAZY)
    @OneToOne
    @JoinColumn(name = "anu_id")
    //@JsonIgnore
    private Ad ad;

    @OneToOne
    @JoinColumn(name="usr_id")
    private User user;

    public Pergunta(Long id, String text, String resp,User user) {
        this.id = id;
        this.text = text;
        this.resp = resp;
        this.user= user;
    }

    public Pergunta(String pergunta, Ad ad, User user) {
        this.text = pergunta;
        this.ad = ad;
        this.user = user;
    }

    public Ad getAd() {
        return ad;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pergunta() {
        this(0L,"","",null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

//    public Ad getAd() {
//        return ad;
//    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
