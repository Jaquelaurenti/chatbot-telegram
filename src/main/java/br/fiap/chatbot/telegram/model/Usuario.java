package br.fiap.chatbot.telegram.model;

public class Usuario {

    private long chatId;
    private String Nome;
    private String Sobrenome;
    private String UltimoComando;

    public Usuario (long chatId, String nome, String sobrenome){
        this.chatId = chatId;
        this.Nome = nome;
        this.Sobrenome = sobrenome;
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

    public String getUltimoComando() {
        return UltimoComando;
    }

    public void setUltimoComando(String ultimoComando) {
        UltimoComando = ultimoComando;
    }
}
