import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(Update update) {
        SendResponse sendResponse;
        String responseMessage;

        if(update.message().toString().contains("burro")){
            responseMessage ="Burro é vc seu troxa";
        }
        else if(update.message().toString().toLowerCase().contains("apostila")){
            responseMessage = "Informe a matéria que deseja consultar Apostilas";

        }
        else{
            responseMessage = "Aguardando escolha";

        }
        return responseMessage ;
    }
}


