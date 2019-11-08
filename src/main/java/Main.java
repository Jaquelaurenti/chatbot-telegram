
import java.util.List;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import constants.ConfigBot;

public class Main {

    public static void main(String[] args) {

        TelegramBot bot = TelegramBotAdapter.build(ConfigBot.BOT_TOKEN);
        GetUpdatesResponse updatesResponse;
        SendResponse sendResponse;
        boolean lcontrol;
        BaseResponse baseResponse;

        int m=0;
        lcontrol = false;
        /* loop infinito pode ser alterado por algum timer de intervalo curto */
        while (true){
            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)

            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            //an√°lise de cada a√ß√£o da mensagem
            for (Update update : updates) {

                m = update.updateId()+1;

                System.out.println("Mensagem recebida :"+ update.message().text());

                if(!lcontrol){
                	com.pengrad.telegrambot.model.User from = update.message().from();
                	sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Ol· " + from.firstName() + ' ' + from.lastName()));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem vindo ao Chatbot " + ConfigBot.BOT_NOME));
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o serviÁo que deseja consultar: "
                            + "\n 1 - Apostilas"
                            + "\n 2 - Boletim"
                            + "\n 3 - Calend·rio de Aulas"
                            + "\n 4 - Entrega de Trabalhos"));
                    lcontrol = true;
                }

                MessagesChatbot messagesChatbot = new MessagesChatbot();

                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                if(baseResponse.isOk() && lcontrol){
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(),messagesChatbot.onUpdateReceived(update)));
                    System.out.println("Resposta enviada : " + sendResponse.message().text()) ;
                }
            }

        }

    }

}