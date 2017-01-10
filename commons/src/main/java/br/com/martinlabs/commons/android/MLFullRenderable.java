package br.com.martinlabs.commons.android;

import android.app.Activity;

import br.com.martinlabs.commons.android.purchase.MLCloneable;

/**
 * Created by gil on 9/28/16.
 */

public interface MLFullRenderable<T extends MLCloneable> {

    void fullRender(T oldstate, T newstate);
    void singleAction(T oldstate, T newstate, int... actions);
    Activity getActivity();

}
