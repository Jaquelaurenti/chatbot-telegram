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

class Bot {
    private static TelegramBot _bot;
    private static Map<Long, Usuario> usuarioList;
    private static MessagesChatbot messagesChatbot = new MessagesChatbot();
    private static SendResponse sendResponse;
    private static BaseResponse baseResponse;

    static void start() {
        if (_bot == null) {
            _bot = new TelegramBot(ConfigBot.BOT_TOKEN);
            usuarioList = new HashMap<>();
            messagesChatbot.load();
            getMessages();
        }
    }

    public static void sendMessage(Long chatId, String message){
        sendResponse = _bot.execute(new SendMessage(chatId, message));
    }

    public static void sendBaseResponse(Long chatId, String action){
        baseResponse = _bot.execute(new SendChatAction(chatId, action));
    }

    public static void sendDocument(Long chatId, String documentId){
        baseResponse = _bot.execute((new SendDocument(chatId, documentId)));
    }

    private static void getMessages() {
        GetUpdatesResponse updatesResponse;
        int m = 0;
        /* loop infinito pode ser alterado por algum timer de intervalo curto */
        while (true) {
            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)

            updatesResponse = _bot.execute(new GetUpdates().limit(100).offset(m));
            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            for (Update update : updates) {

                long chatId = update.message().chat().id();
                Usuario usuario = usuarioList.get(chatId);
                m = update.updateId() + 1;

                System.out.println("Id: " + chatId);
                System.out.println("Mensagem recebida :" + update.message().text());

                if (usuario == null){
                    User from = update.message().from();
                    usuario = new Usuario(chatId, from.firstName(), from.lastName());
                    usuarioList.put(chatId, usuario);
                    sendBaseResponse(chatId, ChatAction.typing.name());
                    messagesChatbot.helloMessage(usuario);
                    messagesChatbot.getMainMenu(usuario);
                } else {
                    sendBaseResponse(chatId, ChatAction.typing.name());
                    String message = update.message().text() != null  ? update.message().text().toLowerCase() : "";
                    message = messagesChatbot.getOpcoes(message, usuario);
                    sendMessage(chatId, message);
                    System.out.println("Resposta enviada : " + message);
                }
            }
        }
    }
}
