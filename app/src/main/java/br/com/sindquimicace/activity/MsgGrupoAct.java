package br.com.sindquimicace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sindquimicace.R;
import br.com.sindquimicace.adapter.GrupoAdapter;
import br.com.sindquimicace.adapter.UsuarioAdapter;
import br.com.sindquimicace.delegate.GruposDelegate;
import br.com.sindquimicace.delegate.ListaUsuariosDelegate;
import br.com.sindquimicace.delegate.SendMessageDelegate;
import br.com.sindquimicace.task.GruposTask;
import br.com.sindquimicace.task.ListaUsuariosTask;
import br.com.sindquimicace.task.SendMessageTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;


public class MsgGrupoAct extends AppCompatActivity implements GruposDelegate,
        SendMessageDelegate {


    private ListView listViewGrupos;

    private List<Grupo> listaGrupos;

    ProgressDialog ringProgressDialog;

    ImageView btnEnviar;

    Mensagem mensagem;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_send_grupos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Selecione os Grupos");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem = Data.getMensagem(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem.setUsuarios(new ArrayList<Usuario>());
        mensagem.setGrupos(new ArrayList<Grupo>());

        listViewGrupos = (ListView) findViewById(R.id.listView2);

        ListUtils.setDynamicHeight(listViewGrupos);

        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listaGrupos != null && !listaGrupos.isEmpty()) {
                    for (Grupo grupo : listaGrupos) {

                        if (grupo.getIsChecked() != null && grupo.getIsChecked()) {
                            mensagem.getGrupos().add(grupo);
                        }

                    }
                }

                postMessage();

            }
        });

        GruposTask taskGrupos = new GruposTask(this);

        taskGrupos.execute(usuario);
    }

    @Override
    public void listouGrupos(List<Grupo> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            listaGrupos = lista;

            GrupoAdapter adapter = new GrupoAdapter(this,listaGrupos);

            listViewGrupos.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed()
    {

        navToHome();
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        i.addCategory(Intent.CATEGORY_HOME);

        this.startActivity(i);

    }



    private void postMessage(){

        if(mensagem.getGrupos().isEmpty() && mensagem.getUsuarios().isEmpty()){

            Toast.makeText(this, "Selecione ao menos um grupo!", Toast.LENGTH_LONG).show();
            return;

        }

        SendMessageTask task = new SendMessageTask(this);

        mensagem.setUsuario(usuario);

        task.execute(mensagem);
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }


    @Override
    public void sendMessageOK(Mensagem msg) {

        if(msg != null && msg.getId() != null ){

            Toast.makeText(this, "Mensagem Enviada com Sucesso!", Toast.LENGTH_LONG).show();
            navToHome();

        }else{
            Toast.makeText(this, "Erro ao Enviar a Mensagem. Tente Mais Tarde!", Toast.LENGTH_LONG).show();
            navToHome();
        }

    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }




}

