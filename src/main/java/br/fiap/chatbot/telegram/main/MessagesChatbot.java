package br.fiap.chatbot.telegram.main;

import br.fiap.chatbot.telegram.model.Servico;
import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.model.request.ChatAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MessagesChatbot {
    private  Map<String, Servico> servicoList;

    public String onUpdateReceived(String message, Usuario usuario) {
         message = message.toLowerCase();
         String responseMessage = "";
        
        if(message.contains("burro")){
            responseMessage ="Burro é vc seu trouxa";
        }
        else if(message.startsWith("1")){
            responseMessage = "Informe a matéria que deseja consultar Apostilas";
            usuario.setOpcao("1");
        }
        else if(message.startsWith("2")){
            responseMessage = "Tá preocupado com as notas né.";
            usuario.setOpcao("2");
        }
        else if(message.startsWith("3")){
            responseMessage = "Váaarias reposições!";
            usuario.setOpcao("3");
        }
        else if(message.startsWith("4")){
            responseMessage = "Vixi tem trabalho pacas pra fazer!";
            usuario.setOpcao("4");
        }
        else{
            responseMessage = "Aguardando escolha.";
            usuario.setOpcao("");
        }
        return responseMessage ;
    }

    public  void load(){
        servicoList = new HashMap<>();
        Servico svc = new Servico("1", "Apostilas", "Informe a matéria que deseja consultar Apostilas:\n");
        svc.addItem("1", new Servico("1", "Design Thinking","Download...."));
        svc.addItem("2", new Servico("2", "Java","", "Donwload", "BQADAQADiwAD332YRnxAr2rGB0SdFgQ"));
        svc.addItem("3", new Servico("3", "Persistence","Download...."));
        svc.addItem("4", new Servico("4", "UX Design","Download...."));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim","Tá preocupado com as notas né.");
        servicoList.put("2", svc);

        svc = new Servico("3", "Calendário de Aulas", "Váaarias reposições!");
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos","Vixi tem trabalho pacas pra fazer!");
        servicoList.put("4", svc);
    }

    public  String getOpcoes(String opcao, Usuario usuario){
        AtomicReference<String> opcoes = new AtomicReference<>("");

        opcoes.set("Aguardando escolha.");
        usuario.setOpcao(opcao);

        if (opcao == null || opcao == ""){
            opcoes.set("");
            servicoList.forEach((key, value) -> {
                opcoes.set(opcoes.get() + '\n' + value.getDescricao());
            });
        }
        else{
            Servico svc = usuario.getServico();
            if (svc == null)
                    svc = servicoList.get(opcao);
            else
                    svc = svc.get(opcao);

            if (svc != null) {
                usuario.setServico(svc);
                opcoes.set(svc.getDescricaoOpcoes());
                String comando = svc.getComando();

                if (comando != null && comando != " ")
                {
                    executaComando(usuario, comando, svc.getParametroComando());
                }
                else {
                    Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                    Map<String, Servico> opt = svc.getOpcoes();
                    if (opt != null) {
                        opt.forEach((key, value) -> {
                            opcoes.set(opcoes.get() + '\n' + value.getDescricao());
                        });
                    }
                }
            }
        }
        return opcoes.get();
    }

    public  void getMainMenu(Usuario usuario){
        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
        Bot.sendMessage(usuario.getChatId(), "Informe o serviço que deseja consultar:\n " + getOpcoes("", usuario));
    }
    private void executaComando(Usuario usuario, String comando, String parametro){

        switch (comando){
            case "Donwload":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.upload_document.name());
                Bot.sendDocument(usuario.getChatId(), parametro);
                usuario.setServico(null);
                usuario.setOpcao("");
                break;

            default:
                break;
        }
        //getMainMenu(usuario);
    }}



