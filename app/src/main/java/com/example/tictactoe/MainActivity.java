package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] boton = new Button[3][3];
    private boolean turnoJugador1 = true;
    private Button reseteo;
    private int conteo, puntajeJ1 = 0, puntajeJ2 = 0;
    private TextView tv_jugador1, tv_jugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_jugador1 = findViewById(R.id.tex_jugador1);
        tv_jugador2 = findViewById(R.id.tex_jugador2);
        reseteo = findViewById(R.id.botonReinicio);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String elBoton = "boton_" + i + j;
                int rID = getResources().getIdentifier(elBoton, "id", getPackageName());
                boton[i][j] = findViewById(rID);
                boton[i][j].setOnClickListener(this);
            }
        }

        reseteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (turnoJugador1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        conteo++;
        if (comprobarGanador()) {
            if (turnoJugador1 == true) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (conteo == 9) {
            empate();
        } else {
            turnoJugador1 = !turnoJugador1;
        }
    }

    private boolean comprobarGanador() {
        String campo[][] = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                campo[i][j] = boton[i][j].getText().toString();
            }
        }
        /** Se encarga de verificar si la  PRIMER FILA(FILA SUPERIOR) se lleno por "X" u "O"   */
        for (int i = 0; i < 3; i++) {
            if (campo[i][0].equals(campo[i][1])
                    && campo[i][0].equals(campo[i][2])
                    && !campo[i][0].equals("")) {
                return true;
            }
        }
        /**Se encarga de verificar si la primer columna se lleno
         * por "X" u "O"    */
        for (int i = 0; i < 3; i++) {
            if (campo[0][i].equals(campo[1][i])
                    && campo[0][i].equals(campo[2][i])
                    && !campo[0][i].equals("")) {
                return true;
            }
        }
        /**Se encarga de verificar si se formo una diagonal
         *  por "X" u "O"   */
        if (campo[0][0].equals(campo[1][1])
                && campo[0][0].equals(campo[2][2])
                && !campo[0][0].equals("")) {
            return true;
        }
        /**Se encarga de verificar si se formo una diagonal inversa
         *  por "X" u "O"   */
        if (campo[0][2].equals(campo[1][1])
                && campo[0][2].equals(campo[2][0])
                && !campo[0][2].equals("")) {
            return true;
        }
        return false;
    }

    @SuppressLint("ShowToast")
    private void player1Wins() {
        puntajeJ1++;
        Toast.makeText(this, "Paleyer1 wins", Toast.LENGTH_SHORT);
        actualizarPuntaje();
        limpiarTablero();
    }

    @SuppressLint("ShowToast")
    private void player2Wins() {
        puntajeJ2++;
        Toast.makeText(this, "Paleyer2 wins", Toast.LENGTH_SHORT);
        actualizarPuntaje();
        limpiarTablero();
    }

    private void empate() {
        Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        limpiarTablero();
    }

    private void actualizarPuntaje() {
        tv_jugador1.setText("Player One: " + puntajeJ1);
        tv_jugador2.setText("Player Two: " + puntajeJ2);
    }

    private void limpiarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boton[i][j].setText("");
            }
        }
        conteo = 0;
        turnoJugador1 = true;
    }

    private void reiniciarGame() {
        puntajeJ1 = 0;
        puntajeJ2 = 0;
        actualizarPuntaje();
        limpiarTablero();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("conteo", conteo);
        outState.putInt("puntajeJ1", puntajeJ1);
        outState.putInt("puntajeJ2", puntajeJ2);
        outState.putBoolean("turnoJugador1", turnoJugador1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        conteo = savedInstanceState.getInt("conteo");
        puntajeJ1 = savedInstanceState.getInt("puntajeJ1");
        puntajeJ2 = savedInstanceState.getInt("puntajeJ2");
        turnoJugador1 = savedInstanceState.getBoolean("turnoJugador1");
    }
}