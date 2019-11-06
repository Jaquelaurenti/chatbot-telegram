import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(Update update) {
        SendResponse sendResponse;
        String responseMessage;

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
}