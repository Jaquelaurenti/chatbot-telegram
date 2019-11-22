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

    void load() {
        //Cria HashMap com as op��es dos menus
        servicoList = new HashMap<>();
        Servico svc = new Servico("1", "Apostilas", "Informe a mat�ria que deseja consultar Apostilas:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Donwload", "BQADAQADtQAD88CZRnOGwoZqMZa_FgQ"));
        svc.addItem("2", new Servico("2", "Java", "", "Donwload", "BQADAQADiwAD332YRnxAr2rGB0SdFgQ"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Donwload", "BQADAQADtgAD88CZRvNwcaOC1XSRFgQ"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Donwload", "BQADAQADtwAD88CZRgwDK_lQo-NmFgQ"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim", "Informe a mat�ria que deseja consultar a nota:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Nota", "DT"));
        svc.addItem("2", new Servico("2", "Java", "", "Nota", "JV"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Nota", "PS"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Nota", "UD"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("2", svc);

        svc = new Servico("3", "Calend�rio de Aulas", "Informe o M�s:");
        svc.addItem("1", new Servico("1", "Novembro/2019", "", "Data", "Novembro"));
        svc.addItem("2", new Servico("2", "Dezembro/2019", "", "Data", "Dezembro"));
        svc.addItem("3", new Servico("3", "Janeiro/2019", "", "Data", "Janeiro"));
        svc.addItem("4", new Servico("4", "Fevereiro/2019", "", "Data", "Fevereiro"));
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos", "Vixi tem trabalho pacas pra fazer!");
        svc.addItem("9", new Servico("9", "Sair", "", "Sair", null));
        //svc.addItem("", new Servico("","","","",""));
        servicoList.put("4", svc);
    }

    String getOpcoes(String opcao, Usuario usuario) {
        AtomicReference<String> opcoes = new AtomicReference<>("");

        opcao = opcao == null ? "" : opcao;
        opcoes.set("Aguardando escolha.");

        switch (opcao) {
            case "/start":
                usuario.setServico(null);
                opcao = "";
                getMainMenu(usuario);
                opcoes.set("");
                break;
            case "/hello":
                opcao = usuario.getOpcao();
                opcoes.set("");
                helloMessage(usuario);
                break;
            case "":
                opcoes.set("");
                servicoList.forEach((key, value) -> {
                    opcoes.set(opcoes.get() + '\n' + value.getDescricao());
                });
                break;
            default:
                Servico svc = usuario.getServico();
                if (svc == null)
                    svc = servicoList.get(opcao);
                else
                    svc = svc.get(opcao);

                if (svc != null) {
                    usuario.setServico(svc);
                    opcoes.set(svc.getDescricaoOpcoes());
                    String comando = svc.getComando();

                    if (comando != null && comando != " ") {
                        executaComando(usuario, comando, svc.getParametroComando());
                    } else {
                        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                        Map<String, Servico> opt = svc.getOpcoes();
                        if (opt != null) {
                            opt.forEach((key, value) -> {
                                opcoes.set(opcoes.get() + '\n' + value.getDescricao());
                            });
                        }
                    }
                    break;
                }
        }

        usuario.setOpcao(opcao);
        return opcoes.get();
    }

    void getMainMenu(Usuario usuario) {
        Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
        Bot.sendMessage(usuario.getChatId(), "Informe o servi�o que deseja consultar:\n " + getOpcoes("", usuario));
    }

    void helloMessage(Usuario usuario) {
        Bot.sendMessage(usuario.getChatId(), "Ol� " + usuario.getNomeCompleto());
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
                        message = "Nota n�o divulgada.";
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
                        message += "14/12/2019 - Mobile Development (Manh�) - Heider\n";
                        message += "14/12/2019 - Mobile Development (Tarde) - Heider\n";
                        message += "17/12/2019 - Mobile Development - Heider\n";
                        break;
                    case "Janeiro":
                        message += "21/01/2020 - Modern Web - Danilo\n";
                        message += "23/01/2020 - Spring - Fabio\n";
                        message += "28/01/2020 - Modern Web - Danilo\n";
                        message += "30/01/2020 - Spring - Fabio\n";
                        break;
                    case "Fevereiro":
                        message += "04/02/2020 - Modern Web - Danilo\n";
                        message += "06/02/2020 - Spring - Fabio\n";
                        message += "11/02/2020 - Modern Web - Danilo\n";
                        message += "13/02/2020 - Modern Web - Danilo\n";
                        message += "15/02/2020 - Spring (Manh�) - Fabio\n";
                        message += "15/02/2020 - Spring (Tarde) - Fabio\n";
                        message += "18/02/2020 - Empreendorismo - Ricardo\n";
                        message += "20/02/2020 - Modern Web - Danilo\n";
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



