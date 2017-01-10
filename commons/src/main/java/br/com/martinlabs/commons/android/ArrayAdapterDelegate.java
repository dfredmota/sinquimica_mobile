package br.com.martinlabs.commons.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by gil on 3/17/16.
 */
public class ArrayAdapterDelegate extends ArrayAdapter<String> {

    int _resource;
    int _length;
    boolean _reusable = false;

    public interface PopulateViewDelegate {
        void popular(View rowView, int position, View convertView, ViewGroup parent);
    }

    PopulateViewDelegate _del;

    public interface InflateViewDelegate {
        View inflate(LayoutInflater inflater, int position, View convertView, ViewGroup parent);
    }

    InflateViewDelegate _multidel;

    public ArrayAdapterDelegate(Context context, int resource, boolean reusable, PopulateViewDelegate del)
    {
        super(context, resource);
        _del = del;
        _resource = resource;
        _reusable = reusable;

    }

    public ArrayAdapterDelegate(Context context, int resource, PopulateViewDelegate del, List<String> values)
    {
        super(context, resource, values);
        _del = del;
        _resource = resource;

    }

    public ArrayAdapterDelegate(Context context, int resource, PopulateViewDelegate del) {
        this(context, resource, false, del);
    }

    public ArrayAdapterDelegate(Context context, InflateViewDelegate del) {
        super(context, android.R.layout.simple_list_item_1);
        _multidel = del;
    }

    public int getLength()
    {
        return _length;
    }

    public void setLength(int value){
        _length = value;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;

        if (_del != null)
        {
            if (_reusable)
            {
                rowView = convertView;
            }

            if (rowView == null)
            {
                rowView = inflater.inflate(_resource, parent, false);
            }

            _del.popular(rowView, position, convertView, parent);
        }
        else if (_multidel != null)
        {
            rowView = _multidel.inflate(inflater, position, convertView, parent);
        }

        return rowView;
    }


}
