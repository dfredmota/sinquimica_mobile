package br.com.sindquimicace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import br.com.sindquimicace.R;
import br.com.sindquimicace.adapter.MensagemAdapter;
import br.com.sindquimicace.delegate.GruposDelegate;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.task.GruposTask;
import br.com.sindquimicace.task.MensagensTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MensagensDelegate,GruposDelegate{

    private List<Mensagem> listaMensagens;
    private List<Grupo>    listaGrupos;
    ListView listViewMensagens;
    NavigationView navigationView2;
    NavigationView navigationView1;

    ProgressDialog ringProgressDialog;

    Usuario usuario;

    TextView alertMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Timeline");
        setSupportActionBar(toolbar);

        alertMsg = (TextView)findViewById(R.id.alertMsg);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageButton menuLeft = (ImageButton) findViewById(R.id.menuLeft);
        ImageButton menuRight = (ImageButton) findViewById(R.id.menuRight);

        menuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        navigationView1.setNavigationItemSelectedListener(this);
        navigationView2.setNavigationItemSelectedListener(this);

        listViewMensagens = (ListView) findViewById(R.id.list);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        View hView =  navigationView1.getHeaderView(0);

        if(usuario != null){

         TextView nmUsuario = (TextView) hView.findViewById(R.id.nomeUsuario);

            nmUsuario.setText(usuario.getNome());

        }

        if(usuario.getImagem() !=null) {

            Bitmap bmp = BitmapFactory.decodeByteArray(usuario.getImagem(), 0, usuario.getImagem().length);

            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 100, 100, true);

            hView =  navigationView1.getHeaderView(0);
            CircleImageView nav_user = (CircleImageView)hView.findViewById(R.id.profile_image);
            nav_user.setImageBitmap(bMapScaled);

         }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToSendMessage();
            }
        });

        MensagensTask taskMsg = new MensagensTask(this);

        taskMsg.execute(usuario);

    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void listouGrupos(List<Grupo> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            this.listaGrupos = lista;

            populateGrupos();
        }
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

        GruposTask taskGrupos = new GruposTask(this);

        taskGrupos.execute(usuario);
    }

    private void populateGrupos(){

        if(listaGrupos != null && !listaGrupos.isEmpty()){

            final Menu menu = navigationView2.getMenu();
            for (int i = 0; i < listaGrupos.size(); i++) {
                menu.add(listaGrupos.get(i).getNome());

            }

        }
    }

    private void navToSendMessage(){

        Intent i = new Intent(this, SendMessageAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }


    private void populateMensagens(){

        if(listaMensagens != null && !listaMensagens.isEmpty()){

            MensagemAdapter msgAdapter = new MensagemAdapter(this,listaMensagens);

            listViewMensagens.setAdapter(msgAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean isMenuGrupo = false;

        if(listaGrupos != null && !listaGrupos.isEmpty()){

            for(Grupo grupo : listaGrupos){

                if(grupo.getNome().equalsIgnoreCase(item.getTitle().toString())){

                    isMenuGrupo = true;

                    Data.insertGrupo(PreferenceManager.getDefaultSharedPreferences(this),grupo);
                    break;
                }

            }
        }

        if(isMenuGrupo){

            timeLineGrupo();
        }

        if (id == R.id.nav_perfil) {

            navToEditarUsuario();

        } else if (id == R.id.nav_sair) {

            Data.loggoutUsuario(PreferenceManager.getDefaultSharedPreferences(this));

            navToSplashScreen();

        }else if (id == R.id.nav_timeline) {

            navToHome();

        }else if (id == R.id.nav_eventos) {

            navToEventos();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navToSplashScreen(){

        Intent i = new Intent(this, SplashScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToEventos(){

        Intent i = new Intent(this, EventosAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void timeLineGrupo(){

        Intent i = new Intent(this, MensagensGrupoAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToEditarUsuario(){

        Intent i = new Intent(this, EditUsuarioAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//
//        menu.add(0, Menu.FIRST, Menu.NONE, "GRUPOS");
//
//        if(this.listaGrupos != null && !this.listaGrupos.isEmpty()) {
//
//            for(int i=0;i < listaGrupos.size();i++) {
//
//             menu.add(i+1, Menu.FIRST+i, Menu.NONE, listaGrupos.get(i).getNome());
//
//            }
//        }
//
//        return super.onPrepareOptionsMenu(menu);
//    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this,item.getTitle(),Toast.LENGTH_LONG).show();
        return super.onContextItemSelected(item);
    }
}
