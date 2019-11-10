package br.fiap.chatbot.telegram.main;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(Update update) {
        SendResponse sendResponse;
        String responseMessage;

        String message = update.message().text().toLowerCase();
        
        if(message.contains("burro")){
            responseMessage ="Burro � vc seu trouxa";
        }
        else if(message.startsWith("1")){
            responseMessage = "Informe a mat�ria que deseja consultar Apostilas";

        }
        else if(message.startsWith("2")){
            responseMessage = "T� preocupado com as notas n�.";

        }
        else if(message.startsWith("3")){
            responseMessage = "V�aarias reposi��es!";

        }
        else if(message.startsWith("4")){
            responseMessage = "Vixi tem trabalho pacas pra fazer!";

        }
        else{
            responseMessage = "Aguardando escolha.";

        }
        return responseMessage ;
    }
}


