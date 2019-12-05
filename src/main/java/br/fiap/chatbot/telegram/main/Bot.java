package br.fiap.chatbot.telegram.main;

import br.fiap.chatbot.telegram.constants.ConfigBot;
import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Classe criada para configuracao do recebimento e envio de mensagens, todos os metodos
 * criados nessa classe, serao utilizados para controlar o recebimento e o envio das mensagens.
 * Metodo start(): inicia o bot
 * Metodo sendMessage(): executa o envio da mensagem do bot ao usuario
 * Metodo sendDocumento(): executa o envio de documentos ao usuario
 * Metodo getMessages(): busca as mensagens enviadas pelo usuario ao bot
 *
 * @author @sergioHenriquePedrosa @jaquelineLaurenti
 * @return nenhum
 * @since outubro de 2019
 * @version 1.0
 * */


class Bot {
    private static TelegramBot _bot;
    private static Map<Long, Usuario> usuarioList;
    private static MessagesChatbot messagesChatbot = new MessagesChatbot();
    private static SendResponse sendResponse;
    private static BaseResponse baseResponse;

    static void start() {
        //se não existe cria nova instância.
        if (_bot == null) {
            _bot = new TelegramBot(ConfigBot.BOT_TOKEN);
            usuarioList = new HashMap<>();
            messagesChatbot.load();
            getMessages();
        }
    }

    static void sendMessage(Long chatId, String message){
        sendResponse = _bot.execute(new SendMessage(chatId, message));
    }

    static void sendBaseResponse(Long chatId, String action){
        baseResponse = _bot.execute(new SendChatAction(chatId, action));
    }

    static void sendDocument(Long chatId, String documentId){
        baseResponse = _bot.execute((new SendDocument(chatId, documentId)));
    }

    private static void getMessages() {
        GetUpdatesResponse updatesResponse;
        int m = 0;
        //Loop para lista de mensagens
        while (true) {
            updatesResponse = _bot.execute(new GetUpdates().limit(100).offset(m));
            List<Update> updates = updatesResponse.updates();

            for (Update update : updates) {

                long chatId = update.message().chat().id();
                Usuario usuario = usuarioList.get(chatId);
                m = update.updateId() + 1;

                //Se ainda não encontrou o usuário na lista
                //Adiciona e envia mensagem de Olá
                //e depois o menu principal
                if (usuario == null){
                    User from = update.message().from();
                    usuario = new Usuario(chatId, from.firstName(), from.lastName());
                    usuarioList.put(chatId, usuario);
                    sendBaseResponse(chatId, ChatAction.typing.name());
                    messagesChatbot.helloMessage(usuario);
                    messagesChatbot.getMainMenu(usuario);
                } else {
                    //se usuário já está na lista chama método para identificar opção selecionada
                    sendBaseResponse(chatId, ChatAction.typing.name());
                    String message = update.message().text() != null  ? update.message().text().toLowerCase() : "";
                    message = messagesChatbot.getOpcoes(message, usuario);
                    sendMessage(chatId, message);
                }
            }
        }
    }
}
