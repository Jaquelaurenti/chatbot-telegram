package br.fiap.chatbot.telegram.main;

/** Classe criada para controlar as mensagens que serao enviadas ao usuario de acordo com as opcoes escolhidas
 * Metodo load(): realiza um HashMap com as opcoes de escolha que o usuario podera fazer
 * Metodo getOpcoes(): executa as opcoes que o usuario podera escolher para retonar ao menu principal ou sair do bot caso deseje
 * Metodo getmainMenu(): executa o menu de opcoes que o usuario podera escolher
 * Metodo helloMesage(): executa a mensagem de inicio do bot para o usuario
 * Metodo finalMesage(): executa a mensagem final do bot para o usuario
 * Metodo executaComando(): executa o comando de acordo com a escolha informada pelo usuario
 * @author @sergioHenriquePedrosa @jaquelineLaurenti
 * @return nenhum
 * @since outubro de 2019
 * @version 1.0
 **/

import br.fiap.chatbot.telegram.constants.ConfigBot;
import br.fiap.chatbot.telegram.errors.ErrorsChatbot;
import br.fiap.chatbot.telegram.model.Servico;
import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MessagesChatbot {
    private Map<String, Servico> servicoList;

    void load() {
        servicoList = new HashMap<>();
        Servico svc = new Servico("1", "Apostilas", "Informe a matéria que deseja baixar as Apostilas:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Donwload", "BQADAQADtQAD88CZRnOGwoZqMZa_FgQ"));
        svc.addItem("2", new Servico("2", "Java", "", "Donwload", "BQADAQADiwAD332YRnxAr2rGB0SdFgQ"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Donwload", "BQADAQADtgAD88CZRvNwcaOC1XSRFgQ"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Donwload", "BQADAQADtwAD88CZRgwDK_lQo-NmFgQ"));
        svc.addItem("9", new Servico("9", "Ou digite 9 para sair e voltar ao Menu Principal", "", "Sair", null));
        servicoList.put("1", svc);

        svc = new Servico("2", "Boletim", "Informe a matéria que deseja consultar a nota:\n");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Nota", "DT"));
        svc.addItem("2", new Servico("2", "Java", "", "Nota", "JV"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Nota", "PS"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Nota", "UD"));
        svc.addItem("9", new Servico("9", "Ou digite 9 para sair e voltar ao Menu Principal", "", "Sair", null));
        servicoList.put("2", svc);

        svc = new Servico("3", "Calendário de Aulas", "Informe o Mês:");
        svc.addItem("1", new Servico("1", "Novembro/2019", "", "Data", "Novembro"));
        svc.addItem("2", new Servico("2", "Dezembro/2019", "", "Data", "Dezembro"));
        svc.addItem("3", new Servico("3", "Janeiro/2019", "", "Data", "Janeiro"));
        svc.addItem("4", new Servico("4", "Fevereiro/2019", "", "Data", "Fevereiro"));
        svc.addItem("9", new Servico("9", "Ou digite 9 para sair e voltar ao Menu Principal", "", "Sair", null));
        servicoList.put("3", svc);

        svc = new Servico("4", "Entrega de Trabalhos", "Informa a matéria que gostaria de fazer o Upload do trabalho.");
        svc.addItem("1", new Servico("1", "Design Thinking", "", "Trabalho", "DT"));
        svc.addItem("2", new Servico("2", "Java", "", "Trabalho", "JV"));
        svc.addItem("3", new Servico("3", "Persistence", "", "Trabalho", "PS"));
        svc.addItem("4", new Servico("4", "UX Design", "", "Trabalho", "UD"));
        svc.addItem("9", new Servico("9", "Ou digite 9 para sair e voltar ao Menu Principal", "", "Sair", null));
        //svc.addItem("", new Servico("","","","",""));
        servicoList.put("4", svc);
    }

    String getOpcoes(String opcao, Usuario usuario, Message message) throws ErrorsChatbot {
        AtomicReference<String> opcoes = new AtomicReference<>("");

        opcao = opcao == null ? "" : opcao;
        opcoes.set("Aguardando escolha... Ou digite /start para retornar ao Menu Principal.");
        Servico svc = usuario.getServico();

        switch (opcao) {
            case "/start":
                usuario.setServico(null);
                usuario.setAguardandoUpload(false);
                opcao = "";
                getMainMenu(usuario);
                opcoes.set("");
                break;
            case "/sair":
                opcao = usuario.getOpcao();
                opcoes.set("");
                finalMessage(usuario);
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
            case "upload":
                usuario.addTrabalho(svc.getParametroComando(), svc.getDescricao(), message.document().fileId());
                Bot.sendMessage(usuario.getChatId(), "Upload efetuado com sucesso. ID:" + message.document().fileId());
                usuario.setAguardandoUpload(false);
                return getOpcoes("/start", usuario, null);
            default:
                if (usuario.isAguardandoUpload()){
                    Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                    if (message.document() == null){
                        Bot.sendMessage(usuario.getChatId(), "Faça o upload do documento ou digite /start para retornar ao Menu Principal.");
                        return "";
                    }
                }
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
        try {
            Bot.sendMessage(usuario.getChatId(), "Informe o serviço que deseja consultar:\n " + getOpcoes("", usuario, null));
        } catch (ErrorsChatbot errorsChatbot) {
            errorsChatbot.printStackTrace();
        }
    }

    void helloMessage(Usuario usuario) {
        Bot.sendMessage(usuario.getChatId(), "Olá " + usuario.getNomeCompleto());
        Bot.sendMessage(usuario.getChatId(), "Bem vindo ao Chatbot " + ConfigBot.BOT_NOME);
        Bot.sendMessage(usuario.getChatId(), "ainda sou uma versão Beta, então tenha paciência comigo :)");
    }

    void finalMessage(Usuario usuario) {
        Bot.sendMessage(usuario.getChatId(), "Obrigada " + usuario.getNomeCompleto());
        Bot.sendMessage(usuario.getChatId(), "Por utilizar nosso Bot, ainda estamos na versão Beta, mas vamos melhorar muito :)");
    }

    private void executaComando(Usuario usuario, String comando, String parametro) throws ErrorsChatbot {
        String message = "";
        switch (comando) {
            case "Donwload":
                message = "Realizando o Download da Apostila...";
                Bot.sendMessage(usuario.getChatId(), message);
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.upload_document.name());
                message = "Download realizado. Escolha outra opcao ou digite /sair para finalizar.";
                Bot.sendDocument(usuario.getChatId(), parametro);
                Bot.sendMessage(usuario.getChatId(), message);
                break;

            case "Nota":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                switch (parametro) {
                    case "DT":
                        message = "7.0\n\nAprovado!";
                        message += "\nEscolha outra opcao ou digite /sair para finalizar.";
                        break;
                    default:
                        message = "Nota não divulgada.";
                        message += "\nEscolha outra opcao ou digite /sair para finalizar.";
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
                        message += "\nDigite /sair para finalizar ou escolha outra opcao do Menu Principal\n";
                        break;
                    case "Dezembro":
                        message += "03/12/2019 - Persistence - Rafael\n";
                        message += "25/12/2019 - UX Design - Igor\n";
                        message += "10/12/2019 - Mobile Development - Heider\n";
                        message += "12/12/2019 - Mobile Development - Heider\n";
                        message += "14/12/2019 - Mobile Development (Manhã) - Heider\n";
                        message += "14/12/2019 - Mobile Development (Tarde) - Heider\n";
                        message += "17/12/2019 - Mobile Development - Heider\n";
                        message += "\nDigite /sair para finalizar ou escolha outra opcao do Menu Principal\n";
                        break;
                    case "Janeiro":
                        message += "21/01/2020 - Modern Web - Danilo\n";
                        message += "23/01/2020 - Spring - Fabio\n";
                        message += "28/01/2020 - Modern Web - Danilo\n";
                        message += "30/01/2020 - Spring - Fabio\n";
                        message += "\nDigite /sair para finalizar ou escolha outra opcao do Menu Principal\n";
                        break;
                    case "Fevereiro":
                        message += "04/02/2020 - Modern Web - Danilo\n";
                        message += "06/02/2020 - Spring - Fabio\n";
                        message += "11/02/2020 - Modern Web - Danilo\n";
                        message += "13/02/2020 - Modern Web - Danilo\n";
                        message += "15/02/2020 - Spring (Manhã) - Fabio\n";
                        message += "15/02/2020 - Spring (Tarde) - Fabio\n";
                        message += "18/02/2020 - Empreendorismo - Ricardo\n";
                        message += "20/02/2020 - Modern Web - Danilo\n";
                        message += "\nDigite /sair para finalizar ou escolha outra opcao do Menu Principal\n";
                        break;
                }
                Bot.sendMessage(usuario.getChatId(), message);
                break;
            case "Trabalho":
                Bot.sendBaseResponse(usuario.getChatId(), ChatAction.typing.name());
                if (usuario.hasTrabalho(parametro)) {
                    usuario.setServico(null);
                    usuario.setOpcao("");
                    throw new ErrorsChatbot("Trabalho  já foi entregue!");
                }
                Bot.sendMessage(usuario.getChatId(), "Faça o upload do documento ou digite /start para retornar ao Menu Principal.");
                usuario.setAguardandoUpload(true);
                return;
            //break;
            case "Sair":
            default:
                break;
        }
        usuario.setServico(null);
        usuario.setOpcao("");
        getMainMenu(usuario);
    }
}



