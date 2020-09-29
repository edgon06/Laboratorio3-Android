package com.example.calculadora;

public class Identificador {

    private boolean esNumero;
    private int indiceEnCadena;
    private String operador;

    public Identificador(boolean esNumero, int indiceEnCadena) {
        this.esNumero = esNumero;
        this.indiceEnCadena = indiceEnCadena;
    }

    public Identificador(boolean esNumero, int indiceEnCadena, String operador) {
        this.esNumero = esNumero;
        this.indiceEnCadena = indiceEnCadena;
        this.operador = operador;
    }

    public void setEsNumero(boolean esNumero){
        this.esNumero = esNumero;
    }

    public void setIndiceEnCadena(int indiceEnCadena){
        this.indiceEnCadena = indiceEnCadena;
    }

    public void setOperador(String operador){
        this.operador = operador;
    }

    public int getIndiceEnCadena(){
        return indiceEnCadena;
    }

    public boolean getEsNumero() {
        return esNumero;
    }

    public String getOperador() {
        return operador;
    }
}
