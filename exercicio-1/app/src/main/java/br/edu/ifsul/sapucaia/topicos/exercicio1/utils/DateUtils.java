package br.edu.ifsul.sapucaia.topicos.exercicio1.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {
    private static final String PADRÃO = "dd/MM/yyyy";

    @SuppressLint("SimpleDateFormat")
    public static Date interpretar(String data) throws ParseException {
        return new SimpleDateFormat(PADRÃO)
                .parse(data);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatar(Date data) {
        return new SimpleDateFormat(PADRÃO)
                .format(data);
    }
}
