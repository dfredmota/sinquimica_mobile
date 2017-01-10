package br.com.sindquimica.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.sindquimica.R;

public class ValidaFoto extends Activity {

    Button valida;
    Button cancela;
    byte[] fotoParceiro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valida_foto);

        Bundle extras = getIntent().getExtras();
        fotoParceiro = extras.getByteArray("fotoParceiro");

        Bitmap bmp = BitmapFactory.decodeByteArray(fotoParceiro, 0, fotoParceiro.length);

//        ImageView image = (ImageView) findViewById(R.id.staff_image);
//
//        image.setImageBitmap(bmp);

        setTitle(" ");

        RelativeLayout layout =(RelativeLayout)findViewById(R.id.validaFotoBackGround);
        BitmapDrawable backgroundDrawable = new BitmapDrawable(getResources(), bmp);
        layout.setBackgroundDrawable(backgroundDrawable);

        valida = (Button) findViewById(R.id.btnOk);

        cancela = (Button) findViewById(R.id.btnCancel);

        valida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ValidaFoto.this, UsuarioAct.class);
                intent.putExtra("fotoParceiro", fotoParceiro);

                startActivity(intent);
            }
        });

        cancela.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ValidaFoto.this, HomeAct.class);
                startActivity(intent);

            }
        });

    }

    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream = null;

            // Write to SD Card
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/SecureHouse");
                dir.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
                outStream.write(data[0]);
                outStream.flush();
                outStream.close();

                refreshGallery(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return null;
        }

    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

}
