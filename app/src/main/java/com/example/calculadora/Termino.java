package com.example.calculadora;

import java.io.InvalidObjectException;
import java.sql.Struct;

public class Termino {
    private boolean esNumero;
    private String valor;
    private String operador;

    public Termino(boolean esNumero, String valor)
    {
        this.esNumero = esNumero;
        if(esNumero)
            this.valor = valor;
        else
            this.operador = valor;
    }

    public boolean getEsNumero()
    {
        return esNumero;
    }

    public void setEsNumero(boolean v)
    {
        esNumero = v;
    }

    public String getValor()
    {
        if (esNumero)
            return valor;
        else
            return  operador;
    }

    public void setValor(String v)
    {
        valor = v;
    }

    public void setOperador(String v)
    {
        operador = v;
    }

}
