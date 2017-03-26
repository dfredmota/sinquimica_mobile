package br.com.sindquimica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sindquimica.R;
import br.com.sindquimica.delegate.ReenvioSenhaDelegate;
import br.com.sindquimica.task.ReenvioSenhaTask;

public class EsqueciSenhaAct extends AppCompatActivity implements ReenvioSenhaDelegate {

    ProgressDialog ringProgressDialog;

    EditText email;

    Button btnReenvioSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        email = (EditText) findViewById(R.id.editEmailReenvioSenha);

        btnReenvioSenha = (Button) findViewById(R.id.btnReenvioSenha);

        btnReenvioSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailT = email.getText().toString();

                if(!isValidEmail(emailT)){
                    Toast.makeText(EsqueciSenhaAct.this, "Digite um email válido!", Toast.LENGTH_LONG).show();
                    return;
                }

                else {

                    ReenvioSenhaTask task = new ReenvioSenhaTask(EsqueciSenhaAct.this);

                    task.execute(emailT);
                }

            }
        });


    }

    @Override
    public void carregaDialog() {

    }

    @Override
    public void reenvioOK() {

        Toast.makeText(this, "Reenvio de Senha realizado com sucesso!", Toast.LENGTH_LONG).show();
        navToLogin();

    }

    private void navToLogin(){

        Intent i = new Intent(this, LoginAct.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        i.addCategory(Intent.CATEGORY_HOME);

        this.startActivity(i);

    }

    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }
}
