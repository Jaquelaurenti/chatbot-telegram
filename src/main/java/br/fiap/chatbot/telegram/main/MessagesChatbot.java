package br.fiap.chatbot.telegram.main;

import br.fiap.chatbot.telegram.model.Usuario;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;

public class MessagesChatbot {
    public String onUpdateReceived(String message, Usuario usuario) {
         message = message.toLowerCase();
         String responseMessage = "";
        
        if(message.contains("burro")){
            responseMessage ="Burro � vc seu trouxa";
        }
        else if(message.startsWith("1")){
            responseMessage = "Informe a mat�ria que deseja consultar Apostilas";
            usuario.setUltimoComando("1");
        }
        else if(message.startsWith("2")){
            responseMessage = "T� preocupado com as notas n�.";
            usuario.setUltimoComando("2");
        }
        else if(message.startsWith("3")){
            responseMessage = "V�aarias reposi��es!";
            usuario.setUltimoComando("3");
        }
        else if(message.startsWith("4")){
            responseMessage = "Vixi tem trabalho pacas pra fazer!";
            usuario.setUltimoComando("4");
        }
        else{
            responseMessage = "Aguardando escolha.";
            usuario.setUltimoComando("");
        }
        return responseMessage ;
    }
}


