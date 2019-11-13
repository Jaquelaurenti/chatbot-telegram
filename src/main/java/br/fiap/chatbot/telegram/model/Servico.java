package br.fiap.chatbot.telegram.model;

import java.util.ArrayList;
import java.util.List;

public class Servico {
    private String Id;
    private String Descricao;
    private String DescricaoOpcoes;
    private List<Servico> Opcoes;

    public Servico(String id, String descricao, String descricaoOpcoes){
        this.Id = id;
        this.Descricao = descricao;
        this.DescricaoOpcoes = descricaoOpcoes;
    }

    public void addItem(Servico svc){
        if( Opcoes == null){
            Opcoes = new ArrayList<>();
        }
        Opcoes.add((svc));
    }

    public List<Servico> getOpcoes(){
        return Opcoes;
    }

    public String getDescricao() {
        return Id + " - " + Descricao;
    }

    public String getDescricaoOpcoes(){
        return DescricaoOpcoes;
    }
}
