package br.com.sindquimica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.sindquimica.R;
import br.com.sindquimica.adapter.EventoAdapter;
import br.com.sindquimica.adapter.MensagemAdapter;
import br.com.sindquimica.delegate.EventosDelegate;
import br.com.sindquimica.task.EventosTask;
import br.com.sindquimica.util.Data;
import br.developersd3.sindquimica.ws.Evento;
import br.developersd3.sindquimica.ws.Grupo;
import br.developersd3.sindquimica.ws.Mensagem;
import br.developersd3.sindquimica.ws.Usuario;

public class EventosAct extends AppCompatActivity implements EventosDelegate {

    private List<Evento> listaEventos;

    ListView listViewEventos;

    ProgressDialog ringProgressDialog;

    Usuario usuario;

    TextView alertMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        alertMsg = (TextView)findViewById(R.id.alertMsg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        listViewEventos = (ListView) findViewById(R.id.list);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        EventosTask task = new EventosTask(this);

        task.execute(usuario);

    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void listou(List<Evento> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            this.listaEventos = lista;

            populateEventos();
        }else{
            alertMsg.setVisibility(View.VISIBLE);
        }
    }

    private void populateEventos(){

        if(listaEventos != null && !listaEventos.isEmpty()){

            EventoAdapter msgAdapter = new EventoAdapter(this,listaEventos);

            listViewEventos.setAdapter(msgAdapter);
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
}
