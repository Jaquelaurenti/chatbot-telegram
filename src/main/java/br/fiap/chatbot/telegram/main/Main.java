package br.fiap.chatbot.telegram.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.fiap.chatbot.telegram.constants.ConfigBot;
import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Main {

    public static void main(String[] args) {

        TelegramBot bot = TelegramBotAdapter.build(ConfigBot.BOT_TOKEN);
        GetUpdatesResponse updatesResponse;
        SendResponse sendResponse;

        BaseResponse baseResponse;

        Map<Long, Usuario> usuarioList = new HashMap<>();

        int m=0;
           /* loop infinito pode ser alterado por algum timer de intervalo curto */
        while (true){
            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)

            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            //an√°lise de cada a√ß√£o da mensagem
            for (Update update : updates) {

                long chatId = update.message().chat().id();
                Usuario usuario = usuarioList.get(chatId);
                m = update.updateId()+1;

                System.out.println("Id: " + chatId);
                System.out.println("Mensagem recebida :"+ update.message().text());

               if(usuario == null){
                	User from = update.message().from();
                	usuario = new Usuario(chatId, from.firstName(), from.lastName());
                	usuarioList.put(chatId, usuario);
                	sendResponse = bot.execute(new SendMessage(chatId, "Ol· " + usuario.getNomeCompleto()));
                    //sendResponse = bot.execute(new SendMessage(chatId, "Id " + usuario.getChatId()));
                    sendResponse = bot.execute(new SendMessage(chatId, "Bem vindo ao Chatbot " + ConfigBot.BOT_NOME));
                    /*
                    sendResponse = bot.execute(new SendMessage(chatId, "Informe o serviÁo que deseja consultar: "
                            + "\n 1 - Apostilas"
                            + "\n 2 - Boletim"
                            + "\n 3 - Calend·rio de Aulas"
                            + "\n 4 - Entrega de Trabalhos"));
                     */

                }
                else {
                    MessagesChatbot messagesChatbot = new MessagesChatbot();
                    baseResponse = bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));
                    sendResponse = bot.execute(new SendMessage(chatId,messagesChatbot.onUpdateReceived(update)));
                    System.out.println("Resposta enviada : " + sendResponse.message().text()) ;
                }
            }

        }

    }

}