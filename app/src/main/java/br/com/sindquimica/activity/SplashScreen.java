package br.com.sindquimica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.sindquimica.R;
import br.com.sindquimica.delegate.AtualizaTokenDelegate;
import br.com.sindquimica.task.AtualizaTokenTask;
import br.com.sindquimica.util.Data;
import br.developersd3.sindquimica.ws.Usuario;

public class SplashScreen extends AppCompatActivity implements AtualizaTokenDelegate {

    Usuario usuarioLogado;

    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        usuarioLogado = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        // se usuario for nulo redireciona pra tela de inicio para cadastro ou login
        if(usuarioLogado == null)
            navToInicio();

        // se não for nulo e o status for true redireciona para a home caso contrario para a tela de login
        if(usuarioLogado != null){

            if(usuarioLogado.getStatus()){

                String token = Data.getToken(PreferenceManager.getDefaultSharedPreferences(this));

                if(token.equals(usuarioLogado.getToken())) {
                    navToHome();
                }else{
                    usuarioLogado.setToken(token);
                    atualizaTokenApp();
                }

            }else{

                navToLogin();

            }

        }
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void tokenAtualizado(Boolean retorno) {

        ringProgressDialog.dismiss();

        if(retorno){

            Data.insertUsuario(PreferenceManager.getDefaultSharedPreferences(this),usuarioLogado);

            navToHome();

        }else{
            navToHome();
        }
    }

    private void atualizaTokenApp(){

        AtualizaTokenTask task = new AtualizaTokenTask(this);

        task.execute(usuarioLogado);

    }



    private void navToInicio(){

        Intent i = new Intent(this, LoginAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToLogin(){

        Intent i = new Intent(this, LoginAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }
}
