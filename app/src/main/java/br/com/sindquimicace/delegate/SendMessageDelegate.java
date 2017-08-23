package br.com.sindquimicace.delegate;


import br.com.sindquimicace.ws.Mensagem;

/**
 * Created by fred on 20/10/16.
 */


public interface SendMessageDelegate {

    void sendMessageOK(Mensagem msg);
    void carregaDialog();
}
