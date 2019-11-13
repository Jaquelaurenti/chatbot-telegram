package br.fiap.chatbot.telegram.model;

public class Usuario {

    private long chatId;
    private String Nome;
    private String Sobrenome;
    private String UltimoComando;
    private Servico UltimoServico;

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

    public Servico getUltimoServico() {
        return UltimoServico;
    }

    public void setUltimoServico(Servico ultimoServico) {
        UltimoServico = ultimoServico;
    }
}
