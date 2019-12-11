package br.fiap.chatbot.telegram.model;

import br.fiap.chatbot.telegram.errors.ErrorsChatbot;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private long chatId;
    private String Nome;
    private String Sobrenome;
    private String Opcao;
    private Servico Servico;
    private boolean AguardandoUpload;
    private Map<String, EntregaTrabalhos> Trabalhos;

    public Usuario (long chatId, String nome, String sobrenome){
        this.chatId = chatId;
        this.Nome = nome;
        this.Sobrenome = sobrenome;
        this.Trabalhos = new HashMap<>();
    }

    public long getChatId() {
        return chatId;
    }

    public String getNome() {
        return Nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public String getNomeCompleto(){
        return Nome + ' ' + Sobrenome;
    }

    public String getOpcao() {
        return Opcao;
    }

    public void setOpcao(String opcao) {
        Opcao = opcao;
    }

    public Servico getServico() {
        return Servico;
    }

    public void setServico(Servico servico) {
        Servico = servico;
    }

    public boolean isAguardandoUpload() {
        return AguardandoUpload;
    }

    public void setAguardandoUpload(boolean aguardandoUpload) {
        AguardandoUpload = aguardandoUpload;
    }

    public boolean hasTrabalho(String idMateria){
        return Trabalhos.containsKey(idMateria);
    }

    public void addTrabalho(String idMateria, String nomeMaterial, String fileId)  {
        Trabalhos.put(idMateria, new EntregaTrabalhos(idMateria, nomeMaterial, fileId));
    }
}
