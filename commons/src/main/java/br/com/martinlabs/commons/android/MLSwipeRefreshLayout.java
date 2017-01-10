package br.com.martinlabs.commons.android;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by gil on 3/17/16.
 */
public class MLSwipeRefreshLayout extends SwipeRefreshLayout {

    public MLSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MLSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private MLSupplier<Boolean> mayRefreshChecker;

    public void onTryToRefresh(MLSupplier<Boolean> mayRefreshChecker) {
        this.mayRefreshChecker = mayRefreshChecker;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mayRefreshChecker != null) {
            return !mayRefreshChecker.get();
        } else {
            return super.canChildScrollUp();
        }
    }


}
