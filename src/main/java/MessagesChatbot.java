import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(Update update) {
        SendResponse sendResponse;
        String responseMessage;

        if(update.message().equals(("/myname"))){

            // Para testar enviar /myname no telegram e verificar o retorno no chatbot
            System.out.println(update.message().text());
            responseMessage =  "teste";
        }
        if(update.message().toString().contains("burro")){
            responseMessage ="Burro é vc seu troxa";
        }
        else if(update.message().toString().contains("gorda")){
            responseMessage = "Gorda é sua mae";

        }
        else{
            responseMessage = "Nao entendi";

        }
        return responseMessage ;
    }

    public String getBotUsername() {

        return "jaquelaurenti_bot";
    }

    public String getTokenId() {
        return "812219430:AAE8Pti6Jj0MgeLKVyK6ZEivaytq1FheNUE";
    }
}
