package br.com.martinlabs.commons.android;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gil on 9/1/16.
 */
@Deprecated
public class ViewHolder {

    private View view;

    public ViewHolder(View view) {
        this.view = view;
    }

    public View f(int resId)
    {
        return view.findViewById(resId);
    }

    public TextView fText(int resId)
    {
        return (TextView) f(resId);
    }

    public ImageView fImg(int resId)
    {
        return (ImageView) f(resId);
    }

    public EditText fEdit(int resId)
    {
        return (EditText) f(resId);
    }

    public View getView() {
        return view;
    }
}
