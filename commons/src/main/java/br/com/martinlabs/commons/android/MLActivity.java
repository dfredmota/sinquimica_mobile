package br.com.martinlabs.commons.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.martinlabs.commons.android.purchase.MLRenderer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by gil on 18/01/16.
 */
public abstract class MLActivity extends AppCompatActivity implements MLRenderable {


    @Override
    protected void onCreate (Bundle bundle)
    {
        super.onCreate(bundle);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        View parentView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        unbindDrawables(parentView);
        System.gc();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public void unbindDrawables(View view) {
        MLRenderer.unbindDrawables(view);
    }

    public void queue(Runnable action) {
        MLRenderer.queue(action);
    }

    public void ui(Runnable action)
    {
        MLRenderer.ui(this, action);
    }

    public View f(int resId) {
        return MLRenderer.f(this, resId);
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

    public void startActivity(Class destino) {
        MLRenderer.startActivity(this, destino);
    }

    public void queueLoading(int resMessage, Runnable del) {
        MLRenderer.queueLoading(this, resMessage, del);
    }

    public void queueLoading(SwipeRefreshLayout refreshV, Runnable del) {
        MLRenderer.queueLoading(this, refreshV, del);
    }

    public void uiToast(Exception excError)
    {
        MLRenderer.uiToast(this, excError);
    }

    public void uiToast(OpResponse operationResponse) {
        MLRenderer.uiToast(this, operationResponse);
    }

    public void uiToast(String texto, Boolean success) {
        MLRenderer.uiToast(this, texto, success);
    }

    public void uiToast(int resTitulo, Boolean success) {
        MLRenderer.uiToast(this, resTitulo, success);
    }

    public void startActivityClearTask(Class destino) {
        MLRenderer.startActivityClearTask(this, destino);
    }

    public void MinimizeApp()
    {
        MLRenderer.MinimizeApp(this);
    }

    public ViewCollection f(String tag) {
        return MLRenderer.f(this, tag);
    }

    @Override
    public void withEndAnimation(ViewPropertyAnimator animator, Runnable done) {
        MLRenderer.withEndAnimation(animator, done);
    }

    public String formatWithRes(int resId, Object... values) {
        return MLFormatter.withRes(this, resId, values);
    }

    public void setTextAppearance(TextView v, int resId) {
        MLRenderer.setTextAppearance(this, v, resId);
    }

    public int getColorCompat(int res) {
        return ContextCompat.getColor(this, res);
    }

    public int dpToPx(int dp) {
        return MLRenderer.dpToPx(this, dp);
    }

    public String extractValue(int id) {
        return MLRenderer.extractValue(this, id);
    }

    public int extractIntValue(int id) {
        return MLRenderer.extractIntValue(this, id);
    }

    public float extractFloatValue(int id) {
        return MLRenderer.extractFloatValue(this, id);
    }

}
