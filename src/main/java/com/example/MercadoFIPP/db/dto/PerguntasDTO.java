package com.example.MercadoFIPP.db.dto;

public class PerguntasDTO {
    private String pergunta;
    private String resposta;
    private long idUser;
    private long idAnuncio;
    private long idPergunta;

    public PerguntasDTO(String pergunta, String resposta, long idUser, long idAnuncio) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.idUser = idUser;
        this.idAnuncio = idAnuncio;
    }

    public PerguntasDTO()
    {
    }

    public PerguntasDTO(String pergunta, long idAnuncio) {
        this.pergunta = pergunta;
        this.idAnuncio = idAnuncio;
    }

    public long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(long idAnuncio) {
        this.idAnuncio = idAnuncio;
    }
}
