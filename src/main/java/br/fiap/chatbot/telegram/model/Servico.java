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
    private String Comando;
    private String ParametroComando;

    public Servico(String id, String descricao, String descricaoOpcoes){
        this(id, descricao, descricaoOpcoes, null, null);
    }

    public Servico(String id, String descricao, String descricaoOpcoes, String comando, String parametroComando){
        this.Id = id;
        this.Descricao = descricao;
        this.DescricaoOpcoes = descricaoOpcoes;
        this.Comando = comando;
        this.ParametroComando = parametroComando;
    }


    public void addItem(String id,  Servico svc){
        if( Opcoes == null){
            Opcoes = new HashMap<>();
        }
        Opcoes.put(id, svc);
    }

    public Map<String, Servico> getOpcoes(){
        return Opcoes;
    }

    public String getDescricao() {
        return Id + " - " + Descricao;
    }

    public String getDescricaoMateria(){
        return Descricao;
    }

    public String getDescricaoOpcoes(){
        return DescricaoOpcoes;
    }

    public Servico get(String id){
        if (Opcoes != null) {
            return Opcoes.get((id));
        } else {
            return null;
        }
    }

    public String getComando() {
        return Comando;
    }

    public void setComando(String comando) {
        Comando = comando;
    }

    public String getParametroComando() {
        return ParametroComando;
    }

    public void setParametroComando(String parametroComando) {
        ParametroComando = parametroComando;
    }
}
