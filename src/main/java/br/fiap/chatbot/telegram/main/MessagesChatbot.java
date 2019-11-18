package br.fiap.chatbot.telegram.main;

import br.fiap.chatbot.telegram.constants.ConfigBot;
import br.fiap.chatbot.telegram.model.Servico;
import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.model.request.ChatAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MessagesChatbot {
    private Map<String, Servico> servicoList;

    public String onUpdateReceived(String message, Usuario usuario) {
        message = message.toLowerCase();
        String responseMessage = "";

        if (message.contains("burro")) {
            responseMessage = "Burro é vc seu trouxa";
        } else if (message.startsWith("1")) {
            responseMessage = "Informe a matéria que deseja consultar Apostilas";
            usuario.setOpcao("1");
        } else if (message.startsWith("2")) {
            responseMessage = "Tá preocupado com as notas né.";
            usuario.setOpcao("2");
        } else if (message.startsWith("3")) {
            responseMessage = "Váaarias reposições!";
            usuario.setOpcao("3");
        } else if (message.startsWith("4")) {
            responseMessage = "Vixi tem trabalho pacas pra fazer!";
            usuario.setOpcao("4");
        } else {
            responseMessage = "Aguardando escolha.";
            usuario.setOpcao("");
        }
        return responseMessage;
    }

    public void load() {
        servicoList = new HashMap<>();
        Servico svc = new Servico("1", "Apostilas", "Informe a matéria que deseja consultar Apostilas:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Donwload", "BQADAQADtQAD88CZRnOGwoZqMZa_FgQ"));
        svc.addItem("2", new Servico("2", "Java", "", "Donwload", "BQADAQADiwAD332YRnxAr2rGB0SdFgQ"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Donwload", "BQADAQADtgAD88CZRvNwcaOC1XSRFgQ"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Donwload", "BQADAQADtwAD88CZRgwDK_lQo-NmFgQ"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim", "Informe a matéria que deseja consultar a nota:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Nota", "DT"));
        svc.addItem("2", new Servico("2", "Java", "", "Nota", "JV"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Nota", "PS"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Nota", "UD"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("2", svc);

        svc = new Servico("3", "Calendário de Aulas", "Informe o Mês:");
        svc.addItem("1", new Servico("1", "Novembro/2019", "", "Data", "Novembro"));
        svc.addItem("2", new Servico("2", "Dezembro/2019", "", "Data", "Dezembro"));
        svc.addItem("3", new Servico("3", "Janeiro/2019", "", "Data", "Janeiro"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos", "Vixi tem trabalho pacas pra fazer!");
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        //svc.addItem("", new Servico("","","","",""));
        servicoList.put("4", svc);
    }

    public String getOpcoes(String opcao, Usuario usuario) {
        String opcoes = "";

        opcao = opcao == null ? "" : opcao;
        opcoes = "Aguardando escolha.";

        switch (opcao) {
            case "/start":
                usuario.setServico(null);
                opcao = "";
                getMainMenu(usuario);
                opcoes = "";
                break;
            case "oi":
                opcao = usuario.getOpcao();
                opcoes = "";
                helloMessage(usuario);
                break;
            case "menu":
                opcao = usuario.getOpcao();
                opcoes = getOpcoes(opcao, usuario);
                /*
                if (usuario.getServico() == null) {
                    opcoes = "";
                    getMainMenu(usuario);
                }
                else
                    opcoes = getCurrentMenu(usuario);
                    */
                break;
            case "":
                opcoes = "";
                AtomicReference<String> opcoesLst = new AtomicReference<>("");
                servicoList.forEach((key, value) -> {
                    opcoesLst.set(opcoesLst.get() + '\n' + value.getDescricao());
                });
                opcoes = opcoesLst.get();
                break;
            default:
                Servico svc = usuario.getServico();
                if (svc == null)
                    svc = servicoList.get(opcao);
                else
                    svc = svc.get(opcao);

                if (svc != null) {
                    usuario.setServico(svc);
                    opcoes = svc.getDescricaoOpcoes();
                    String comando = svc.getComando();

                    if (comando != null && comando != " ") {
                        executaComando(usuario, comando, svc.getParametroComando());
                    } else {
                        opcoes += getCurrentMenu(usuario);
                        /*
                        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                        Map<String, Servico> opt = svc.getOpcoes();
                        if (opt != null) {
                            opt.forEach((key, value) -> {
                                opcoes.set(opcoes.get() + '\n' + value.getDescricao());
                            });
                        }

                         */
                    }
                    break;
                }
        }

        usuario.setOpcao(opcao);
        return opcoes;
    }

    public void getMainMenu(Usuario usuario) {
        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
        Bot.sendMessage(usuario.getChatId(), "Informe o serviço que deseja consultar:\n " + getOpcoes("", usuario));
    }

    private String getCurrentMenu(Usuario usuario){
        AtomicReference<String> opcoes = new AtomicReference<>("");
        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
        Servico svc = usuario.getServico();
        Map<String, Servico> opt = svc.getOpcoes();
        if (opt != null) {
            opt.forEach((key, value) -> {
                opcoes.set(opcoes.get() + '\n' + value.getDescricao());
            });
        }
        return opcoes.get();
    }

    public void helloMessage(Usuario usuario) {
        Bot.sendMessage(usuario.getChatId(), "Olá " + usuario.getNomeCompleto());
        Bot.sendMessage(usuario.getChatId(), "Bem vindo ao Chatbot " + ConfigBot.BOT_NOME);
    }

    private void executaComando(Usuario usuario, String comando, String parametro) {
        String message = "";
        switch (comando) {
            case "Donwload":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.upload_document.name());
                Bot.sendDocument(usuario.getChatId(), parametro);
                break;

            case "Nota":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                switch (parametro) {
                    case "DT":
                        message = "7.0\n\nAprovado!";
                        break;
                    default:
                        message = "Nota não divulgada.";
                        break;
                }
                Bot.sendMessage(usuario.getChatId(), message);
                break;
            case "Data":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                switch (parametro) {
                    case "Novembro":
                        message += "19/11/2019 - Persistence - Rafael\n";
                        message += "21/11/2019 - UX Design - Igor\n";
                        message += "26/11/2019 - Persistence - Rafael\n";
                        message += "28/11/2019 - UX Design - Igor\n";
                        break;
                    case "Dezembro":
                        message += "03/12/2019 - Persistence - Rafael\n";
                        message += "25/12/2019 - UX Design - Igor\n";
                        message += "10/12/2019 - Mobile Development - Heider\n";
                        message += "12/12/2019 - Mobile Development - Heider\n";
                        message += "14/12/2019 - Mobile Development (Manhã) - Heider\n";
                        message += "14/12/2019 - Mobile Development (Tarde) - Heider\n";
                        message += "17/12/2019 - Mobile Development - Heider\n";
                        break;
                    case "Janeiro":
                        message += "21/01/2020 - Modern Web - Danilo\n";
                        message += "23/01/2020 - Spring - Fabio\n";
                        message += "28/01/2020 - Modern Web - Danilo\n";
                        message += "30/01/2020 - Spring - Fabio\n";
                        break;
                }
                Bot.sendMessage(usuario.getChatId(), message);
                break;
            case "Sair":
            default:
                break;
        }
        usuario.setServico(null);
        usuario.setOpcao("");
        getMainMenu(usuario);
    }
}



