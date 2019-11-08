import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(Update update) {
        SendResponse sendResponse;
        String responseMessage;

        String message = update.message().text().toLowerCase();
        
        if(message.contains("burro")){
            responseMessage ="Burro é vc seu trouxa";
        }
        else if(message.contains("apostila")){
            responseMessage = "Informe a matéria que deseja consultar Apostilas";

        }
        else{
            responseMessage = "Aguardando escolha.";

        }
        return responseMessage ;
    }
}


