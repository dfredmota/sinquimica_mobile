package br.com.martinlabs.commons.android;

import android.support.v4.widget.SwipeRefreshLayout;

import br.com.martinlabs.commons.android.purchase.MLCloneable;
import br.com.martinlabs.commons.android.purchase.MLRenderer;

/**
 * Created by gil on 9/28/16.
 */

public abstract class MLOperator<T extends MLCloneable> {

    private MLFullRenderable<T> renderer;
    private T state;

    public MLOperator(MLFullRenderable<T> renderer) {
        this.renderer = renderer;
    }

    private void innerOperate(OperatorConsumer<T> op) {
        T oldstate;
        T newstate;

        try {

            if (state != null) {
                oldstate = (T) state.clone();
            } else {
                oldstate = null;
            }

            Stepper stepper = (actions) -> {
                T midstate;

                try {
                    if (state != null) {
                        midstate = (T) state.clone();
                    } else {
                        midstate = null;
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    return;
                }

                if (actions != null && actions.length > 0) {
                    MLRenderer.ui(renderer.getActivity(), () -> renderer.singleAction(oldstate, midstate, actions));
                } else {
                    MLRenderer.ui(renderer.getActivity(), () -> renderer.fullRender(oldstate, midstate));
                }
            };

            op.accept(state, stepper);

            stepper.step();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }
    }

    public interface OperatorConsumer<T extends Cloneable> {

        void accept(T state, Stepper stepper);

    }

    public interface Stepper {

        void step(int... actions);

    }

    public void operate(OperatorConsumer<T> op) {
        MLRenderer.queue(() -> innerOperate(op));
    }

    public void operate(int resMessage, OperatorConsumer<T> op) {
        MLRenderer.queueLoading(renderer.getActivity(), resMessage, () -> innerOperate(op));
    }

    public void operate(SwipeRefreshLayout refreshV, OperatorConsumer<T> op) {
        MLRenderer.queueLoading(renderer.getActivity(), refreshV, () -> innerOperate(op));
    }

    public void uiToast(Exception excError)
    {
        MLRenderer.uiToast(renderer.getActivity(), excError);
    }

    public void uiToast(OpResponse operationResponse)
    {
        MLRenderer.uiToast(renderer.getActivity(), operationResponse);
    }

    public void uiToast(String texto, Boolean success)
    {
        MLRenderer.uiToast(renderer.getActivity(), texto, success);
    }

    public void uiToast(int resTitulo, Boolean success)
    {
        MLRenderer.uiToast(renderer.getActivity(), resTitulo, success);
    }

    public void setRenderer(MLFullRenderable<T> renderer) {
        this.renderer = renderer;
    }

    public void setState(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }

}
