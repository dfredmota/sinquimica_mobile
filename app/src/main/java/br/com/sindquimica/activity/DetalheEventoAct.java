package br.com.sindquimica.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.sindquimica.R;
import br.com.sindquimica.delegate.ConfirmaPresencaDelegate;
import br.com.sindquimica.delegate.EventoByIdDelegate;
import br.com.sindquimica.task.ConfirmarPresencaTask;
import br.com.sindquimica.task.EventoByIdTask;
import br.com.sindquimica.util.Data;
import br.developersd3.sindquimica.ws.Evento;
import br.developersd3.sindquimica.ws.Usuario;

public class DetalheEventoAct extends AppCompatActivity implements ConfirmaPresencaDelegate,EventoByIdDelegate {

    Evento evento;

    ProgressDialog ringProgressDialog;

    Button btnConfirmarPresenca;

    Usuario user;

    TextView descricao;
    TextView local;
    TextView dataInicioEvento;
    TextView dataFimEvento;

    TextView statusEvento;
    ImageView imagem;

    SimpleDateFormat formatAg = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_evento);

        descricao = (TextView) findViewById(R.id.evento);
        local = (TextView) findViewById(R.id.local);
        dataInicioEvento = (TextView) findViewById(R.id.dataInicioEvento);
        dataFimEvento = (TextView) findViewById(R.id.dataFimEvento);
        statusEvento = (TextView) findViewById(R.id.statusEvento);
        imagem = (ImageView) findViewById(R.id.imagem_evento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btnConfirmarPresenca = (Button) findViewById(R.id.btnConfirmarPresenca);

        btnConfirmarPresenca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarPresenca();
            }
        });

        user = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            Integer idEvento = (Integer)extras.get("idEvento");

            Integer[] ids = new Integer[]{user.getId(),idEvento};

            EventoByIdTask task = new EventoByIdTask(this);

            task.execute(ids);


        }



    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void listou(Evento evento) {
        ringProgressDialog.dismiss();
        if(evento != null){

            if(evento.getConfirmou() != null && evento.getConfirmou()){

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheEventoAct.this);

                builder.setMessage("Você já confirmou presença nesse evento.Obrigado!")
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        navToHome();

                                        return;

                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();


            }


            this.evento = evento;

            descricao.setText(this.evento.getDescricao());
            local.setText(this.evento.getLocal());
            dataInicioEvento.setText(formatAg.format(this.evento.getInicio()));
            dataFimEvento.setText(formatAg.format(this.evento.getFim()));

            if(this.evento.getStatus() == null || this.evento.getStatus()){
                statusEvento.setText("Confirmado");
            }else{
                statusEvento.setText("Cancelado");
            }

            if(this.evento.getImagem() !=null) {

                Bitmap bmp = BitmapFactory.decodeByteArray(this.evento.getImagem(), 0, this.evento.getImagem().length);

                Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 300, 200, true);

                imagem.setImageBitmap(bMapScaled);

            }

        }

    }

    @Override
    public void confirmou(Boolean confirmacao) {

        if(confirmacao){

            AlertDialog.Builder builder = new AlertDialog.Builder(DetalheEventoAct.this);

            builder.setMessage("Você confirmou presença no evento com sucesso!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    navToHome();

                                    return;

                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(DetalheEventoAct.this);

            builder.setMessage("Ocorreu um erro ao confirmar a sua presenca no evento. Tente mais tarde!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    navToHome();

                                    return;

                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        }

    }

    private void confirmarPresenca(){

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalheEventoAct.this);

        builder.setMessage("Você irá comparecer ao evento?")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                evento.setConfirmou(true);
                                executarConfirmacao();


                            }
                        }).setNegativeButton("Não",
                    new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        evento.setConfirmou(false);
                        executarConfirmacao();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void executarConfirmacao(){

        ConfirmarPresencaTask task = new ConfirmarPresencaTask(this);

        task.execute(evento);
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
