package br.fiap.chatbot.telegram.model;

import java.util.List;

public class Servicos {
    private int Id;
    private String Descricao;
    private List<Servicos> Opcoes;

    public Servicos(int id, String descricao){
        this.Id = id;
        this.Descricao = descricao;
    }
}
