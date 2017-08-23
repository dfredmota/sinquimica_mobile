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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.com.sindquimicace.R;
import br.com.sindquimicace.ws.Usuario;


public class UsuarioAdapter extends BaseAdapter{

    List<Usuario> result;

    Context context;

    int [] imageId;

    private static LayoutInflater inflater=null;


    public UsuarioAdapter(Context mainActivity,List<Usuario> alunos) {
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
        CheckBox  checkboxUsuario;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.li_msg_usuarios, null);
            holder.tv = (TextView) rowView.findViewById(R.id.nome);
            holder.img = (ImageView) rowView.findViewById(R.id.liFotoUsuario);
            holder.checkboxUsuario = (CheckBox) rowView.findViewById(R.id.checkboxUsuario);

            holder.tv.setText(result.get(position).getNome());

            if(result.get(position).getImagem() !=null) {

                Bitmap bmp = BitmapFactory.decodeByteArray(result.get(position).getImagem(), 0, result.get(position).getImagem().length);

                Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 50, 50, true);

                holder.img.setImageBitmap(bMapScaled);

            }

            holder.checkboxUsuario.setOnClickListener(new View.OnClickListener() {
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

