package br.edu.ifsul.sapucaia.topicos.exercicio1.utils;

import android.text.Editable;
import android.text.TextWatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaskUtils implements TextWatcher {
    private final String mascara;
    private boolean estaExecutando = false;
    private boolean estaDeletando = false;

    public static MaskUtils mascara(String mascara) {
        return new MaskUtils(mascara);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        estaDeletando = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (estaExecutando || estaDeletando) {
            return;
        }

        estaExecutando = true;

        int tamanhoEditable = editable.length();

        if (tamanhoEditable < mascara.length()) {
            if (mascara.charAt(tamanhoEditable) != '#') {
                editable.append(mascara.charAt(tamanhoEditable));
            } else if (mascara.charAt(tamanhoEditable - 1) != '#') {
                editable.insert(tamanhoEditable - 1,
                        mascara,
                        tamanhoEditable - 1,
                        tamanhoEditable);
            }
        }

        estaExecutando = false;
    }
}
