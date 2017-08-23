package br.com.sindquimicace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.sindquimicace.R;
import br.com.sindquimicace.adapter.MensagemAdapter;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.task.GruposTask;
import br.com.sindquimicace.task.MensagensGrupoTask;
import br.com.sindquimicace.task.MensagensTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;


public class MensagensGrupoAct extends AppCompatActivity implements MensagensDelegate {

    private List<Mensagem> listaMensagens;

    ListView listViewMensagens;

    ProgressDialog ringProgressDialog;

    Usuario usuario;

    Grupo grupo;

    TextView alertMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens_grupo);

        alertMsg = (TextView)findViewById(R.id.alertMsg);

        grupo = Data.getGrupo(PreferenceManager.getDefaultSharedPreferences(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tituloToolbar = (TextView) findViewById(R.id.timelineGrupo);

        tituloToolbar.setText("GRUPO - "+grupo.getNome());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        listViewMensagens = (ListView) findViewById(R.id.list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToSendMessage();
            }
        });

        MensagensGrupoTask taskMsg = new MensagensGrupoTask(this);

        taskMsg.execute(grupo);

    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void listou(List<Mensagem> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            this.listaMensagens = lista;

            populateMensagens();
        }else{
            alertMsg.setVisibility(View.VISIBLE);
        }

    }

    private void populateMensagens(){

        if(listaMensagens != null && !listaMensagens.isEmpty()){

            MensagemAdapter msgAdapter = new MensagemAdapter(this,listaMensagens);

            listViewMensagens.setAdapter(msgAdapter);
        }
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);
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

    private void navToSendMessage(){

        Intent i = new Intent(this, SendMessageAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

}
