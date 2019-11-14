package br.fiap.chatbot.telegram.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servico {
    private String Id;
    private String Descricao;
    private String DescricaoOpcoes;
    private Map<String, Servico> Opcoes;

    public Servico(String id, String descricao, String descricaoOpcoes){
        this.Id = id;
        this.Descricao = descricao;
        this.DescricaoOpcoes = descricaoOpcoes;
    }

    public void addItem(String Id,  Servico svc){
        if( Opcoes == null){
            Opcoes = new HashMap<>();
        }
        Opcoes.put(Id, svc);
    }

    public Map<String, Servico> getOpcoes(){
        return Opcoes;
    }

    public String getDescricao() {
        return Id + " - " + Descricao;
    }

    public String getDescricaoOpcoes(){
        return DescricaoOpcoes;
    }

    public Servico get(String Id){
        if (Opcoes != null) {
            return Opcoes.get((Id));
        } else {
            return null;
        }
    }
}
