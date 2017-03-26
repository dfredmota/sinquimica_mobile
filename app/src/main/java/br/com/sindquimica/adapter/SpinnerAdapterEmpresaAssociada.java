package br.com.sindquimica.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.developersd3.sindquimica.ws.EmpresaAssociada;

/**
 * Created by fred on 07/01/17.
 */

public class SpinnerAdapterEmpresaAssociada extends ArrayAdapter<EmpresaAssociada> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<EmpresaAssociada> values;

    public SpinnerAdapterEmpresaAssociada(Context context, int textViewResourceId,
                                          List<EmpresaAssociada> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public EmpresaAssociada getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getNomeFantasia());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setText(values.get(position).getNomeFantasia());

        return label;
    }
}
