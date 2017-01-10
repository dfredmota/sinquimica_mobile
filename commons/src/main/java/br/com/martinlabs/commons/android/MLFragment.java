package br.com.martinlabs.commons.android;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.martinlabs.commons.android.purchase.MLRenderer;

/**
 * Created by gil on 9/1/16.
 */
public abstract class MLFragment extends Fragment implements FragmentLifecycle, MLRenderable {

    protected Activity act;
    protected View view;

    public void setContentView(int resource, LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(resource, container, false);
        act = getActivity();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

    public void unbindDrawables(View view) {
        MLRenderer.unbindDrawables(view);
    }

    public void queue(Runnable action)
    {
        MLRenderer.queue(action);
    }

    public void ui(Runnable action)
    {
        MLRenderer.ui(act, action);
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

    public void startActivity(Class destino) {
        MLRenderer.startActivity(act, destino);
    }

    public void queueLoading(int resMessage, Runnable del)
    {
        MLRenderer.queueLoading(act, resMessage, del);
    }

    public void queueLoading(SwipeRefreshLayout refreshV, Runnable del)
    {
        MLRenderer.queueLoading(act, refreshV, del);
    }

    public void uiToast(Exception excError)
    {
        MLRenderer.uiToast(act, excError);
    }

    public void uiToast(OpResponse operationResponse)
    {
        MLRenderer.uiToast(act, operationResponse);
    }

    public void uiToast(String texto, Boolean success)
    {
        MLRenderer.uiToast(act, texto, success);
    }

    public void uiToast(int resTitulo, Boolean success)
    {
        MLRenderer.uiToast(act, resTitulo, success);
    }

    public void startActivityClearTask(Class destino)
    {
        MLRenderer.startActivityClearTask(act, destino);
    }


    public void MinimizeApp()
    {
        MLRenderer.MinimizeApp(act);
    }

    public ViewCollection f(String tag) {
        ViewGroup v = (ViewGroup) f(android.R.id.content);
        return MLRenderer.f(v, tag);
    }

    public void withEndAnimation(ViewPropertyAnimator animator, Runnable done) {
        MLRenderer.withEndAnimation(animator, done);
    }

    public String formatWithRes(int resId, Object... values) {
        return MLFormatter.withRes(act, resId, values);
    }

    public void setTextAppearance(TextView v, int resId) {
        MLRenderer.setTextAppearance(act, v, resId);
    }

    public int getColorCompat(int res) {
        return ContextCompat.getColor(act, res);
    }

    public int dpToPx(int dp) {
        return MLRenderer.dpToPx(act, dp);
    }

    public String extractValue(int id) {
        return MLRenderer.extractValue(act, id);
    }

    public int extractIntValue(int id) {
        return MLRenderer.extractIntValue(act, id);
    }

    public float extractFloatValue(int id) {
        return MLRenderer.extractFloatValue(act, id);
    }
}
