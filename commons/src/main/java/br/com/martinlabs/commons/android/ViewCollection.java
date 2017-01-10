package br.com.martinlabs.commons.android;

import android.view.View;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gil on 3/8/16.
 */
public class ViewCollection {

    private ArrayList<View> views = new ArrayList<>();

    public ViewCollection() {
    }

    public ViewCollection(ArrayList<View> views) {
        this.views = views;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    public void addView(View v) {
        this.views.add(v);
    }

    public void addAllViews(Collection<View> views) {
        this.views.addAll(views);
    }

    public void addAllViews(ViewCollection other) {
        this.views.addAll(other.getViews());
    }

    public void setVisibility(int visibility) {
        for (View v : this.views) {
            v.setVisibility(visibility);
        }
    }
}
