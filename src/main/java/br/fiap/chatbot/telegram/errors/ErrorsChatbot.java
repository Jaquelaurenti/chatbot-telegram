package br.fiap.chatbot.telegram.errors;

/**  Classe criada para configuracao das exceptions que poderao ser criadas no bot
 * @author @sergioHenriquePedrosa @jaquelineLaurenti
 * @return nenhum
 * @since outubro de 2019
 * @version 1.0
 * */


@SuppressWarnings("serial")
public class ErrorsChatbot extends Exception {
    public ErrorsChatbot(String message) {
        super(message);
    }
}
