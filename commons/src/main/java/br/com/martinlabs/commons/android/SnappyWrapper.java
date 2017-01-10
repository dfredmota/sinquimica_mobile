package br.com.martinlabs.commons.android;

import android.content.Context;

import com.android.internal.util.Predicate;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Created by gil on 18/01/16.
 */
public class SnappyWrapper {

    public synchronized static <T> T open(Context ctx, SnappyConsumer<T> action) {
        DB snappydb = null;
        T resp = null;
        try {
            snappydb = DBFactory.open(ctx);

            resp = action.accept(snappydb);

        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }

        return resp;
    }

    public interface SnappyConsumer<T> {
        T accept(DB snappy) throws SnappydbException;
    }

}
