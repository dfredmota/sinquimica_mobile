package br.com.sindquimica.task;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.sindquimica.delegate.LoginDelegate;
import br.com.sindquimica.delegate.SendMessageDelegate;
import br.developersd3.sindquimica.ws.Mensagem;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class SendMessageTask extends AsyncTask<Mensagem, Usuario, Mensagem> {

    private SendMessageDelegate delegate;

    public SendMessageTask(SendMessageDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected Mensagem doInBackground(Mensagem... params) {

        WsDao ws = new WsDao();

        try {

       Integer id =  ws.insertMensagem(params[0]);

       params[0].setId(id);

        // envia as notificações
        if(params[0].getUsuarios() != null && !params[0].getUsuarios().isEmpty()){


            for(Usuario user : params[0].getUsuarios()){


                if(user.getToken() != null){

                    sendMessageFirebase(user.getToken(),"Você tem uma Nova Mensagem!");

                }
            }


        }


        }catch(Exception e){
            e.printStackTrace();
        }

        return params[0];

    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Mensagem msg) {

        this.delegate.sendMessageOK(msg);
    }

    private void sendMessageFirebase(String token,String conteudo) throws JSONException,IOException {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "key=AAAAg6OKGls:APA91bHCLYvN31Zk09s6FmLy5k6pFYGGj74Ah9JSSLlFAMVoxupEVBEe8MFMPAdfyuqw-TsSPdJ_fjmjUzuKFcNXTcDlDHnroM0kGQPt6RDjNpO2hA-rpOU7YTn44SOdMCp9l6fUErc0");

        JSONObject message = new JSONObject();
        message.put("to", token);
        message.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Sindquimica");
        notification.put("body", conteudo);

        message.put("notification", notification);

        post.setEntity(new StringEntity(message.toString(), "UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println(response);
        System.out.println(message);
    }
}
