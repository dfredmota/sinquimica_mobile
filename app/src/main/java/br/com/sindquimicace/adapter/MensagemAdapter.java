package br.com.sindquimicace.adapter;

/**
 * Created by fred on 12/10/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.sindquimicace.R;


import br.com.sindquimicace.ws.Mensagem;
import de.hdodenhof.circleimageview.CircleImageView;


public class MensagemAdapter extends BaseAdapter {

    List<Mensagem> result;

    Context context;

    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());

    SimpleDateFormat formatAg = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private static LayoutInflater inflater = null;

    public MensagemAdapter(Context mainActivity, List<Mensagem> comissoes) {
        result = comissoes;
        context = mainActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView descricao;
        TextView data;
        TextView usuario;
        CircleImageView imagemUsuario;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        convertView = inflater.inflate(R.layout.li_mensagem, null);
        holder.descricao = (TextView) convertView.findViewById(R.id.mensagem);
        holder.data = (TextView) convertView.findViewById(R.id.data_mensagem);
        holder.usuario = (TextView) convertView.findViewById(R.id.usuario_mensagem);

        holder.imagemUsuario = (CircleImageView) convertView.findViewById(R.id.foto_usuario_msg);

        holder.descricao.setText(result.get(position).getConteudo());

        holder.usuario.setText(result.get(position).getUsuario().getNome());

        if (result.get(position).getCreatedAt() != null)
            holder.data.setText(formatAg.format(result.get(position).getCreatedAt()));

        // converte os bytes da imagem na foto do usuario que enviou a msg

        if (result.get(position).getUsuario() != null && result.get(position).getUsuario()
                .getImagem() != null) {

            Bitmap bmp = BitmapFactory.decodeByteArray(result.get(position).getUsuario()
                    .getImagem(), 0, result.get(position).getUsuario()
                    .getImagem().length);

            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 70, 70, true);

            holder.imagemUsuario.setImageBitmap(bMapScaled);

        }


        convertView.setTag(holder);

        return convertView;
    }

}

