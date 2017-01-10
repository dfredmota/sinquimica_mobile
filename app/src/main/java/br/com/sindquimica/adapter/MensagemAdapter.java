package br.com.sindquimica.adapter;

/**
 * Created by fred on 12/10/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import br.com.sindquimica.R;
import br.com.sindquimica.model.Mensagem;


public class MensagemAdapter extends BaseAdapter {

    List<Mensagem> result;

    Context context;

    DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());


    private static LayoutInflater inflater=null;

    public MensagemAdapter(Context mainActivity,List<Mensagem> comissoes) {
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
        TextView descricao;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.li_mensagem, null);
            holder.descricao = (TextView) rowView.findViewById(R.id.mensagem);

            holder.descricao.setText(result.get(position).getConteudo());

            rowView.setTag(holder);

        }else {
            holder= (Holder) convertView.getTag();
            holder.descricao.setText(result.get(position).getConteudo());
        }

        return rowView;
    }

}

