package br.com.martinlabs.commons.android.purchase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadPoolExecutor;

import br.com.martinlabs.commons.android.OpResponse;
import br.com.martinlabs.commons.android.R;
import br.com.martinlabs.commons.android.ThreadPool;
import br.com.martinlabs.commons.android.ViewCollection;

/**
 * Created by gil on 9/28/16.
 */

public final class MLRenderer {

    private static Toast _toast;

    public static void queue(Runnable action) {
        ThreadPool.queue(action);
    }

    public static void queueLoading(Activity ctx, int resMessage, Runnable del)
    {
        String sMessage = ctx.getString(resMessage);

        queue(() -> {
            ProgressDialog[] loading = new ProgressDialog[1];

            ui(ctx, () -> {
                loading[0] = ProgressDialog.show(ctx, "", sMessage, true);
            });

            del.run();

            ui(ctx, () -> {
                if (loading[0] != null) {
                    loading[0].cancel();
                }
            });
        });
    }

    public static void queueLoading(Activity act, SwipeRefreshLayout refreshV, Runnable del)
    {
        queue(() -> {

            ui(act, () -> {
                refreshV.setRefreshing(true);
            });

            del.run();

            ui(act, () -> {
                refreshV.setRefreshing(false);
            });

        });
    }

    public static void ui(Activity act, Runnable action) {
        act.runOnUiThread(action);
    }

    public static void uiToast(Activity ctx, String texto, Boolean success)
    {
        ui(ctx, () ->
        {
            LayoutInflater inflater = ctx.getLayoutInflater();

            View layout = inflater.inflate(R.layout.toast, (ViewGroup) f(ctx, R.id.toast));

            if (texto != null) {
                TextView textV = (TextView) layout.findViewById(R.id.toast_text);
                textV.setText(texto, TextView.BufferType.NORMAL);
                textV.setTextColor(ContextCompat.getColor(ctx, success ? R.color.verde : R.color.vinho));
            }

            if (_toast == null) {
                _toast = new Toast(ctx.getApplicationContext());
            }

            _toast.setGravity(Gravity.TOP, 0, 0);
            _toast.setDuration(Toast.LENGTH_LONG);
            _toast.setView(layout);
            _toast.show();
        });
    }

    public static void uiToast(Activity ctx, OpResponse operationResponse)
    {
        uiToast(ctx, operationResponse.getMessage(), operationResponse.isSuccess());
    }

    public static void uiToast(Activity ctx, int resTitulo, Boolean success)
    {
        String sTitulo = ctx.getString(resTitulo);
        uiToast(ctx, sTitulo, success);
    }

    public static void uiToast(Activity ctx, Exception excError) {
        uiToast(ctx, excError.getMessage(), false);
    }

    public static View f(Activity act, int resId) {
        return act.findViewById(resId);
    }

    public static ViewCollection f(ViewGroup root, String tag){

        ViewCollection vc = new ViewCollection();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                vc.addAllViews(f((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                vc.addView(child);
            }

        }
        return vc;
    }

    public static ViewCollection f(Activity root, String tag) {
        ViewGroup v = (ViewGroup) root.findViewById(android.R.id.content);
        return f(v, tag);
    }

    public static void startActivity(Activity act, Class destino) {
        Intent i = new Intent(act, destino);
        act.startActivity(i);
    }

    public static void startActivityClearTask(Activity act, Class destino)
    {
        Intent i = new Intent(act, destino);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        act.startActivity(i);
    }

    public static void unbindDrawables(View view) {

        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            try {
                ((ViewGroup) view).removeAllViews();
            } catch (UnsupportedOperationException e) {}
        }
    }

    public static void MinimizeApp(Context ctx)
    {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    public static void withEndAnimation(ViewPropertyAnimator animator, Runnable done) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    done.run();
                }
            });
        } else {
            animator.withEndAction(done);
        }
    }

    public static void setTextAppearance(Context context, TextView v, int resId) {

        if (Build.VERSION.SDK_INT < 23) {

            v.setTextAppearance(context, resId);

        } else {

            v.setTextAppearance(resId);
        }
    }

    public static int dpToPx(Context ctx, int dp) {

        return (int) ((dp) * ctx.getResources().getDisplayMetrics().density);

    }

    public static String extractValue(Activity act, int id)
    {
        return ((EditText)f(act, id)).getText().toString();
    }

    public static int extractIntValue(Activity act, int id)
    {
        int aux = 0;

        try {
            aux = Integer.parseInt(extractValue(act, id));
        } catch (NumberFormatException e) {}

        return aux;
    }

    public static float extractFloatValue(Activity act, int id)
    {
        float aux = 0;

        try {
            aux = Float.parseFloat(extractValue(act, id));
        } catch (NumberFormatException e) {}

        return aux;
    }

}
