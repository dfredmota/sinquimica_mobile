package br.com.martinlabs.commons.android;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gil on 9/1/16.
 */
public class MLFormatter {

    public static String withMask(Context ctx, int resId, Object val) {
        return new MLMaskFormatter(ctx.getString(resId)).valueToString(val);
    }

    public static String withRes(Context ctx, int resId, Object... val) {
        return String.format(ctx.getString(resId), val);
    }

    public static String monthAndYear(Date data) {
        return new SimpleDateFormat("MMMM 'de' yyyy").format(data);
    }

    public static String dateExtense(Date data) {
        return new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(data);
    }

    public static String date(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    public static String time(Date data) {
        return new SimpleDateFormat("HH:mm").format(data);
    }

    public static String day(Date data) {
        return new SimpleDateFormat("dd").format(data);
    }

    public static String weakDay(Date data) {
        return new SimpleDateFormat("EEE").format(data);
    }

    public static String amountDays(Date dataPassada)
    {
        String result = "";

        if(dataPassada != null)
        {
            Date dataAgora = new Date();

            long diff = Math.abs(dataAgora.getTime() - dataPassada.getTime());


            long diffDays = diff / (24 * 60 * 60 * 1000);

            if(diffDays == 1)
            {
                return String.format("%1$d dia",diffDays);
            }

            if(diffDays > 1)
            {
                return String.format("%1$d dias",diffDays);
            }

            long diffHour = diff/ (60 * 60 * 1000);
            long diffMin = (diff / (60 * 1000)) - (diffHour * 60);

            if(diffHour > 0)
            {
                return String.format("%1$d:%2$d",diffHour,diffMin);
            }

            diffMin = diff / (60 * 1000);

            result = String.format("%1$dm",diffMin);

        }

        return result;
    }

}
