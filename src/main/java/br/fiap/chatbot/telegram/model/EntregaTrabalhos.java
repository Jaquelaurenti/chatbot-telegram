package br.fiap.chatbot.telegram.model;

public class EntregaTrabalhos {
    private String IdMateria;
    private String NomeMateria;
    private  String FileId;

    public EntregaTrabalhos(String idMateria, String nomeMateria, String fileId){
        this.IdMateria = idMateria;
        this.NomeMateria = nomeMateria;
        this.FileId = fileId;
    }

    public String getNomeMateria() {
        return NomeMateria;
    }
}
