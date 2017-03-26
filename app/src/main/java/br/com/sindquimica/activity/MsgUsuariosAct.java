package br.com.sindquimica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sindquimica.R;
import br.com.sindquimica.adapter.GrupoAdapter;
import br.com.sindquimica.adapter.UsuarioAdapter;
import br.com.sindquimica.delegate.GruposDelegate;
import br.com.sindquimica.delegate.GruposMsgDelegate;
import br.com.sindquimica.delegate.ListaUsuariosDelegate;
import br.com.sindquimica.delegate.SendMessageDelegate;
import br.com.sindquimica.task.GruposMsgTask;
import br.com.sindquimica.task.GruposTask;
import br.com.sindquimica.task.ListaUsuariosTask;
import br.com.sindquimica.task.SendMessageTask;
import br.com.sindquimica.util.Data;
import br.com.sindquimica.util.TinyDB;
import br.developersd3.sindquimica.ws.Grupo;
import br.developersd3.sindquimica.ws.Mensagem;
import br.developersd3.sindquimica.ws.Usuario;

public class MsgUsuariosAct extends AppCompatActivity implements ListaUsuariosDelegate,GruposDelegate,
        SendMessageDelegate {

    private ListView listViewUsuarios;

    private List<Usuario> listaUsuarios;

    private ListView listViewGrupos;

    private List<Grupo> listaGrupos;

    ProgressDialog ringProgressDialog;

    ImageView btnEnviar;

    Mensagem mensagem;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_usuarios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Selecione os Usuários");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem = Data.getMensagem(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem.setUsuarios(new ArrayList<Usuario>());
        mensagem.setGrupos(new ArrayList<Grupo>());

        listViewUsuarios = (ListView) findViewById(R.id.listView1);

        listViewGrupos = (ListView) findViewById(R.id.listView2);

        ListUtils.setDynamicHeight(listViewUsuarios);
        ListUtils.setDynamicHeight(listViewGrupos);

        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Usuario user : listaUsuarios){

                    if(user.getIsChecked() != null && user.getIsChecked()){
                        mensagem.getUsuarios().add(user);
                    }

                }

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

        ListaUsuariosTask taskUser = new ListaUsuariosTask(this);

        taskUser.execute();
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

            Toast.makeText(this, "Selecione ao menos um destinatário!", Toast.LENGTH_LONG).show();
            return;

        }

        // nesse ponto eu envio uma mensagem para mim mesmo pra visualiza-la na linha do tempo

        Usuario usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem.getUsuarios().add(usuario);

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
    public void listaUsuarios(List<Usuario> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            listaUsuarios = lista;

            UsuarioAdapter adapter = new UsuarioAdapter(this,listaUsuarios);

            listViewUsuarios.setAdapter(adapter);

            GruposTask taskGrupos = new GruposTask(this);

            taskGrupos.execute(usuario);
        }

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

