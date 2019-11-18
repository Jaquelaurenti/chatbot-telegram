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
            responseMessage ="Burro � vc seu trouxa";
        }
        else if(message.startsWith("1")){
            responseMessage = "Informe a mat�ria que deseja consultar Apostilas";
            usuario.setOpcao("1");
        }
        else if(message.startsWith("2")){
            responseMessage = "T� preocupado com as notas n�.";
            usuario.setOpcao("2");
        }
        else if(message.startsWith("3")){
            responseMessage = "V�aarias reposi��es!";
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
        Servico svc = new Servico("1", "Apostilas", "Informe a mat�ria que deseja consultar Apostilas:\n");
        svc.addItem("1", new Servico("1", "Design Thinking","", "Donwload", "BQADAQADtQAD88CZRnOGwoZqMZa_FgQ"));
        svc.addItem("2", new Servico("2", "Java","", "Donwload", "BQADAQADiwAD332YRnxAr2rGB0SdFgQ"));
        svc.addItem("3", new Servico("3", "Persistence","", "Donwload", "BQADAQADtgAD88CZRvNwcaOC1XSRFgQ"));
        svc.addItem("4", new Servico("4", "UX Design","", "Donwload", "BQADAQADtwAD88CZRgwDK_lQo-NmFgQ"));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim","Informe a mat�ria que deseja consultar a nota:\n");
        svc.addItem("1", new Servico("1","Design Thinking","","Nota","DT"));
        svc.addItem("2", new Servico("2","Java","","Nota","JV"));
        svc.addItem("3", new Servico("3","Persistence","","Nota","PS"));
        svc.addItem("4", new Servico("4","UX Design","","Nota","UD"));
        servicoList.put("2", svc);

        svc = new Servico("3", "Calend�rio de Aulas", "V�aarias reposi��es!");
        //svc.addItem("", new Servico("","","","",""));
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos","Vixi tem trabalho pacas pra fazer!");
        servicoList.put("4", svc);
    }

    public  String getOpcoes(String opcao, Usuario usuario){
        AtomicReference<String> opcoes = new AtomicReference<>("");

        if (opcao.equals("/start")){
            usuario.setServico(null);
            getMainMenu(usuario);
            return "";
        }

        opcoes.set("Aguardando escolha.");
        usuario.setOpcao(opcao);

        if (opcao == null || opcao == "" ){
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
        Bot.sendMessage(usuario.getChatId(), "Informe o servi�o que deseja consultar:\n " + getOpcoes("", usuario));
    }
    private void executaComando(Usuario usuario, String comando, String parametro){

        switch (comando){
            case "Donwload":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.upload_document.name());
                Bot.sendDocument(usuario.getChatId(), parametro);
                break;

            case "Nota":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                String message = "";
                switch (parametro){
                    case "DT":
                        message = "7.0\n\nAprovado!";
                        break;
                    default:
                        message = "Nota n�o divulgada.";
                        break;
                }
                Bot.sendMessage(usuario.getChatId(), message);
            default:
                break;
        }
        usuario.setServico(null);
        usuario.setOpcao("");
        getMainMenu(usuario);
    }}



