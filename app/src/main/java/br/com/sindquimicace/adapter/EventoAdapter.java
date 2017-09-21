package br.com.sindquimicace.adapter;

/**
 * Created by fred on 12/10/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.sindquimicace.R;
import br.com.sindquimicace.activity.DetalheEventoAct;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Evento;
import de.hdodenhof.circleimageview.CircleImageView;


public class EventoAdapter extends BaseAdapter {

    List<Evento> result;

    Context context;

    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());

    SimpleDateFormat formatAg = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private static LayoutInflater inflater=null;

    public EventoAdapter(Context mainActivity, List<Evento> comissoes) {
        result=comissoes;
        context=mainActivity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public class Holder
    {
        TextView  descricao;
        TextView  local;
        TextView  dataInicioEvento;
        TextView  dataFimEvento;
        TextView  status;
        Button    btnDetalhe;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.li_evento, null);
            holder.descricao = (TextView) rowView.findViewById(R.id.evento);
            holder.local = (TextView) rowView.findViewById(R.id.local);
            holder.dataInicioEvento = (TextView) rowView.findViewById(R.id.dataInicioEvento);
            holder.dataFimEvento = (TextView) rowView.findViewById(R.id.dataFimEvento);
            holder.status = (TextView) rowView.findViewById(R.id.status);
            holder.btnDetalhe = (Button) rowView.findViewById(R.id.btnDetalhe);

            holder.descricao.setText(result.get(position).getDescricao());
            holder.local.setText(result.get(position).getLocal());
            holder.dataInicioEvento.setText(formatAg.format(result.get(position).getInicio()));
            holder.dataFimEvento.setText(formatAg.format(result.get(position).getFim()));


            if(result.get(position).getStatus() == null || !result.get(position).getStatus()){
                holder.status.setText("CANCELADO");
            }else{
                holder.status.setText("CONFIRMADO");
            }

            holder.btnDetalhe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Data.insertEvento(PreferenceManager.getDefaultSharedPreferences(context),result.get(position));
                    Intent intent= new Intent(context, DetalheEventoAct.class);
                    intent.putExtra("idEvento",result.get(position).getId());
                    context.startActivity(intent);

                }
            });

            if(result.get(position).getStatus() != null && result.get(position).getStatus() == false){
                holder.btnDetalhe.setVisibility(View.GONE);
            }

            rowView.setTag(holder);

        }else {
            holder= (Holder) convertView.getTag();
            holder.descricao.setText(result.get(position).getDescricao());
            holder.local.setText(result.get(position).getLocal());
            holder.dataInicioEvento.setText(formatAg.format(result.get(position).getInicio()));
            holder.dataFimEvento.setText(formatAg.format(result.get(position).getFim()));
        }

        return rowView;
    }

}

