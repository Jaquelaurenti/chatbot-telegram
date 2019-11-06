
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

public class Main {

    public static void main(String[] args) {

        TelegramBot bot = TelegramBotAdapter.build("812219430:AAE8Pti6Jj0MgeLKVyK6ZEivaytq1FheNUE");
        GetUpdatesResponse updatesResponse;
        SendResponse sendResponse;
        //objeto responsável por gerenciar o envio de ações do chat
        BaseResponse baseResponse;

        //controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
        int m=0;

        //loop infinito pode ser alterado por algum timer de intervalo curto
        while (true){

            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)

            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            //análise de cada ação da mensagem
            for (Update update : updates) {

                m = update.updateId()+1;

                System.out.println("Mensagem recebida "+ update.message().text());

                MessagesChatbot messagesChatbot = new MessagesChatbot();

                //envio de "Escrevendo" antes de enviar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

                sendResponse = bot.execute(new SendMessage(update.message().chat().id(),messagesChatbot.onUpdateReceived(update)));

                if(baseResponse.isOk()){
                    System.out.println("Resposta enviada") ;
                }
            }

        }

    }

}