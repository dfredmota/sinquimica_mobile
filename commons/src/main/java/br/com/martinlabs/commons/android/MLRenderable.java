package br.com.martinlabs.commons.android;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gil on 9/14/16.
 */
public interface MLRenderable {

    View f(int resId);
    TextView fText(int resId);
    ImageView fImg(int resId);
    EditText fEdit(int resId);
    Activity getActivity();


    void unbindDrawables(View view) ;

    void queue(Runnable action);

    void ui(Runnable action);

    void queueLoading(int resMessage, Runnable del);

    void queueLoading(SwipeRefreshLayout refreshV, Runnable del);

    void uiToast(Exception excError);

    void uiToast(OpResponse operationResponse);

    void uiToast(String texto, Boolean success);

    void uiToast(int resTitulo, Boolean success);

    void startActivityClearTask(Class destino);

    void MinimizeApp();

    ViewCollection f(String tag);

    void withEndAnimation(ViewPropertyAnimator animator, Runnable done) ;

    String formatWithRes(int resId, Object... values) ;

    void setTextAppearance(TextView v, int resId) ;

    int getColorCompat(int res) ;

    int dpToPx(int dp);

    String extractValue(int id);

    int extractIntValue(int id);

    float extractFloatValue(int id);

}
