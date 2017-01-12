package br.com.sindquimica.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.List;
import br.com.martinlabs.commons.android.MLActivity;
import br.com.martinlabs.commons.android.OpResponse;
import br.com.sindquimica.R;
import br.com.sindquimica.adapter.MensagemAdapter;
import br.com.sindquimica.model.Grupo;
import br.com.sindquimica.model.Mensagem;
import br.com.sindquimica.model.Usuario;
import br.com.sindquimica.process.ProcessServices;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAct extends MLActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List<Mensagem> listaMensagens;
    private List<Grupo>    listaGrupos;
    ListView listViewMensagens;
    NavigationView navigationView2;
    NavigationView navigationView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Timeline");
        setSupportActionBar(toolbar);

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

        Usuario usuario = ProcessServices.getUsuarioLogado(this);

        if(usuario != null && usuario.getImagem() !=null) {

            Bitmap bmp = BitmapFactory.decodeByteArray(usuario.getImagem(), 0, usuario.getImagem().length);

            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 70, 70, true);

            View hView =  navigationView1.getHeaderView(0);
            CircleImageView nav_user = (CircleImageView)hView.findViewById(R.id.profile_image);
            nav_user.setImageBitmap(bMapScaled);
        }

        queueLoading(R.string.carregando, () -> carregaMensagens());
        queueLoading(R.string.carregando, () -> carregaGrupos());

    }

    private void carregaGrupos(){

        Usuario usuario = ProcessServices.getUsuarioLogado(this);

        OpResponse<List<Grupo>> resp = ProcessServices.getSessionInstance().carregaGruposUsuario(this,usuario);

        if (resp.isSuccess())
        {
            listaGrupos = resp.getData();

            ui(() -> populateGrupos());

        }
        else
        {
            uiToast(resp);
        }

    }

    private void carregaMensagens(){

        Usuario usuario = ProcessServices.getUsuarioLogado(this);

        OpResponse<List<Mensagem>> resp = ProcessServices.getSessionInstance().carregaMensagensUsuario(this,usuario);

        if (resp.isSuccess())
        {
            listaMensagens = resp.getData();

            ui(() -> populateMensagens());
        }
        else
        {
            uiToast(resp);
        }

    }

    private void populateGrupos(){

        if(listaGrupos != null && !listaGrupos.isEmpty()){

            final Menu menu = navigationView2.getMenu();
            for (int i = 0; i < listaGrupos.size(); i++) {
                menu.add(listaGrupos.get(i).getNome());
            }

        }
    }


    private void populateMensagens(){

        if(listaMensagens != null && !listaMensagens.isEmpty()){

            MensagemAdapter msgAdapter = new MensagemAdapter(getActivity(),listaMensagens);

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

        if (id == R.id.nav_almoxarifado) {



        } else if (id == R.id.nav_sair) {

            finish();

            android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


}
