import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        //System.out.println(update.getMessage().getText());
        //System.out.println(update.getMessage().getFrom().getFirstName());

        SendMessage mensagem = new SendMessage();

        String comand = update.getMessage().getText();

        if(comand.equals(("/myname"))){
            // Para testar enviar /myname no telegram e verificar o retorno no chatbot

            System.out.println(update.getMessage().getFrom().getFirstName());
            mensagem.setText(update.getMessage().getFrom().getFirstName());
        }

        if(comand.equals(("/mylastname"))){
            System.out.println(update.getMessage().getFrom().getLastName());
            mensagem.setText(update.getMessage().getFrom().getLastName());
        }

        mensagem.setChatId(update.getMessage().getChatId());
        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        // Nome do meu Bot criado pelo telegram
        return "jaquelaurenti_bot";
    }

    public String getBotToken() {
        // Numero do meu token
        return "812219430:AAE8Pti6Jj0MgeLKVyK6ZEivaytq1FheNUE\n";
    }
}
