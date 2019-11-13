package br.fiap.chatbot.telegram.main;

import br.fiap.chatbot.telegram.model.Servico;
import br.fiap.chatbot.telegram.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MessagesChatbot {
    private static Map<String, Servico> servicoList;

    public String onUpdateReceived(String message, Usuario usuario) {
         message = message.toLowerCase();
         String responseMessage = "";
        
        if(message.contains("burro")){
            responseMessage ="Burro é vc seu trouxa";
        }
        else if(message.startsWith("1")){
            responseMessage = "Informe a matéria que deseja consultar Apostilas";
            usuario.setUltimoComando("1");
        }
        else if(message.startsWith("2")){
            responseMessage = "Tá preocupado com as notas né.";
            usuario.setUltimoComando("2");
        }
        else if(message.startsWith("3")){
            responseMessage = "Váaarias reposições!";
            usuario.setUltimoComando("3");
        }
        else if(message.startsWith("4")){
            responseMessage = "Vixi tem trabalho pacas pra fazer!";
            usuario.setUltimoComando("4");
        }
        else{
            responseMessage = "Aguardando escolha.";
            usuario.setUltimoComando("");
        }
        return responseMessage ;
    }

    public static void load(){
        servicoList = new HashMap<>();
        Servico svc = new Servico("1", "Apostilas", "Informe a matéria que deseja consultar Apostilas:");
        svc.addItem(new Servico("1", "Design Thinking",""));
        svc.addItem(new Servico("2", "Java",""));
        svc.addItem(new Servico("3", "Persistence",""));
        svc.addItem(new Servico("4", "UX Design",""));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim","Tá preocupado com as notas né.");
        servicoList.put("2", svc);

        svc = new Servico("3", "Calendário de Aulas", "Váaarias reposições!");
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos","Vixi tem trabalho pacas pra fazer!");
        servicoList.put("4", svc);
    }

    public static String getOpcoes(String opcao, Usuario usuario){
        AtomicReference<String> opcoes = new AtomicReference<>("");

        if (opcao == null || opcao == ""){
            servicoList.forEach((key, value) -> {
                opcoes.set(opcoes.get() + '\n' + value.getDescricao());
            });
        }
        else{
            Servico svc = usuario.getUltimoServico();

            if (svc == null)
                    svc = servicoList.get(opcao);

            if (svc != null) {
                usuario.setUltimoComando(opcao);
                usuario.setUltimoServico(svc);
                opcoes.set(svc.getDescricaoOpcoes());
                List<Servico> opt = svc.getOpcoes();
                if (opt != null) {
                    for (Servico servico : opt) {
                        opcoes.set(opcoes.get() + '\n' + servico.getDescricao());
                    }
                }
            }
            else
            {
                opcoes.set("Aguardando escolha.");
            }
        }
        return opcoes.get();
    }
}


