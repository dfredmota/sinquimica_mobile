package br.com.sindquimicace.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sindquimicace.R;
import br.com.sindquimicace.delegate.ListaUsuariosDelegate;
import br.com.sindquimicace.delegate.SendMessageDelegate;
import br.com.sindquimicace.task.ListaUsuariosTask;
import br.com.sindquimicace.task.SendMessageTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;


public class SendMessageAct extends AppCompatActivity {

    Button btnSelectUsers;

    List<Usuario> usuarios;

    Mensagem mensagem;

    EditText editConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Envio de Mensagens");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        editConteudo = (EditText) findViewById(R.id.editConteudo);

        mensagem = new Mensagem();

        mensagem.setUsuarios(new ArrayList<Usuario>());

        usuarios = new ArrayList<Usuario>();

        btnSelectUsers = (Button) findViewById(R.id.btnSelectUsers);

        btnSelectUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String conteudoMsg = editConteudo.getText().toString();

                if(conteudoMsg != null && conteudoMsg.isEmpty()){
                    Toast.makeText(SendMessageAct.this,"Favor preencher a mensagem",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    mensagem.setConteudo(conteudoMsg);
                }

                Data.insertMensagem(PreferenceManager.getDefaultSharedPreferences(SendMessageAct.this),mensagem);

                navToSelectUsuarios();

            }
        });


    }



    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToSelectUsuarios(){

        Intent i = new Intent(this, MsgUsuariosAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            navToHome();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

        navToHome();
    }


}
