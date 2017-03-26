package br.com.sindquimica.adapter;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.sindquimica.R;
import br.developersd3.sindquimica.ws.Grupo;
import br.developersd3.sindquimica.ws.Usuario;


public class GrupoAdapter extends BaseAdapter{

    List<Grupo> result;

    Context context;

    int [] imageId;

    private static LayoutInflater inflater=null;


    public GrupoAdapter(Context mainActivity, List<Grupo> alunos) {
        result=alunos;
        context=mainActivity;
        imageId=null;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView tv;
        ImageView img;
        CheckBox  checkboxGrupo;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.li_msg_grupos, null);
            holder.tv = (TextView) rowView.findViewById(R.id.nome);
            holder.checkboxGrupo = (CheckBox) rowView.findViewById(R.id.checkboxGrupo);

            holder.tv.setText(result.get(position).getNome());


            holder.checkboxGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(result.get(position).getIsChecked() != null &&
                            !result.get(position).getIsChecked()){

                        result.get(position).setIsChecked(false);

                    }else{

                        result.get(position).setIsChecked(true);
                    }
                }
            });

            rowView.setTag(holder);


        }else {
            holder= (Holder) convertView.getTag();
            holder.tv.setText(result.get(position).getNome());
        }


        return rowView;
    }

}

