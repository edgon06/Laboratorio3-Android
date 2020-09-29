package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn0,btn00,btnPunto,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnSumar,btnRestar,btnMultiplicar,btnDividir,btnIgual,btnBorrar;
    private TextView txtRespuesta, txtExpresion;

    private int indiceGlobal;
    private float resultado;

    private ArrayList<Termino> expresion = new ArrayList<Termino>();
    private ArrayList<Identificador> idTerminos = new ArrayList<Identificador>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn00 = findViewById(R.id.btn00);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnPunto = findViewById(R.id.btnPunto);

        btnSumar = findViewById(R.id.btnSumar);
        btnRestar = findViewById(R.id.btnRestar);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);
        btnIgual = findViewById(R.id.btnIgual);
        btnBorrar = findViewById(R.id.btnBorrar);

        txtRespuesta = findViewById(R.id.txtRespuesta);
        txtExpresion = findViewById(R.id.txtExpresion);

        asignarEventoEscribirDigito(btn00);
        asignarEventoEscribirDigito(btn0);
        asignarEventoEscribirDigito(btn1);
        asignarEventoEscribirDigito(btn2);
        asignarEventoEscribirDigito(btn3);
        asignarEventoEscribirDigito(btn4);
        asignarEventoEscribirDigito(btn5);
        asignarEventoEscribirDigito(btn6);
        asignarEventoEscribirDigito(btn7);
        asignarEventoEscribirDigito(btn8);
        asignarEventoEscribirDigito(btn9);
        asignarEventoEscribirDigito(btnPunto);

        asignarEventoEscribirOperador(btnSumar);
        asignarEventoEscribirOperador(btnRestar);
        asignarEventoEscribirOperador(btnMultiplicar);
        asignarEventoEscribirOperador(btnDividir);

        asignarEventoBorrar(btnBorrar);

        asignarEventoResolver(btnIgual);

    }

    /* TODO:

        - Obtener término justo antes de ejecutar el evento de escribir operador y de resolver operacion
    */

    // Funciones para asignar eventos a los botones
    public void asignarEventoEscribirDigito(final Button vista){
        vista.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try{
                   escribirDigito(vista.getText().toString());
                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, getText(R.string.mensajeError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void asignarEventoEscribirOperador(final Button vista){
        vista.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try{
                    escribirOperador(vista.getText().toString());
                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, getText(R.string.mensajeError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void asignarEventoBorrar(final Button vista){
        vista.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try{
                    limpiarPantalla();
                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, getText(R.string.mensajeError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void asignarEventoResolver(final Button vista){
        vista.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try{
                    identificarTerminos();
                    resolver();

                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Métodos al presionar un boton
    public void escribirDigito(String digito){
        txtExpresion.setText( txtExpresion.getText().toString() + digito );
        identificarNumero();
    }

    public void identificarNumero(){
        idTerminos.add(new Identificador(true,indiceGlobal));
        indiceGlobal++;
    }

    public void escribirOperador(String operador){

        txtExpresion.setText(txtExpresion.getText().toString() + operador);
        identificarOperador(operador);
    }

    public void identificarOperador(String operacion){
        idTerminos.add(new Identificador(false,indiceGlobal,operacion));
        indiceGlobal++;
    }

    public void limpiarPantalla()
    {
        txtExpresion.setText("");
        txtRespuesta.setText("");
        idTerminos.clear();
        expresion.clear();
        indiceGlobal = 0;
    }

    // Métodos para resolver la expresión dada

    public void identificarTerminos(){
    String cadenaPrincipal = txtExpresion.getText().toString();

    int i;
    String numero = "";
    String operacion = "";
    for(i=0; i<cadenaPrincipal.length();i++)
    {
        if (idTerminos.get(i).getEsNumero())
        {
            numero += cadenaPrincipal.charAt(idTerminos.get(i).getIndiceEnCadena());
        }else
        {
            expresion.add(new Termino(true, numero));
            numero = "";

            operacion += cadenaPrincipal.charAt(idTerminos.get(i).getIndiceEnCadena());
            expresion.add(new Termino(false, operacion));
            operacion = "";
        }
    }
        expresion.add(new Termino(true, numero));
    }

    // Realizar la operación final
    public void resolver()
    {
        int x;
        resultado = 0;
        for(x=0; x<expresion.size(); x++)
        {
            if (!expresion.get(x).getEsNumero())
            {
                switch (expresion.get(x).getValor())
                {
                    case "+":
                        resultado += sumar(Float.parseFloat(expresion.get(x-1).getValor()),Float.parseFloat(expresion.get(x+1).getValor()));
                        break;
                    case "-":
                        resultado += restar(Float.parseFloat(expresion.get(x-1).getValor()),Float.parseFloat(expresion.get(x+1).getValor()));
                        break;
                    case "*":
                        resultado += multiplicar(Float.parseFloat(expresion.get(x-1).getValor()),Float.parseFloat(expresion.get(x+1).getValor()));
                        break;
                    case "/":
                        resultado += dividir(Float.parseFloat(expresion.get(x-1).getValor()),Float.parseFloat(expresion.get(x+1).getValor()));
                        break;
                    default:
                        break;
                }
            }
        }
        txtRespuesta.setText(String.valueOf(resultado));
    }



    // Funciones para resolver las operaciones
    public float sumar(float num1, float num2){
        return num1 + num2;
    }

    public float restar(float num1, float num2){
        return num1 - num2;
    }

    public float multiplicar(float num1, float num2){
        return num1 * num2;
    }

    public float dividir(float num1, float num2){
        return num1 / num2;
    }

}