package com.practica.practica02_03_intentsexplicitos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.practica.practica02_03_intentsexplicitos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            try {
                // Verificar que los campos no estén vacíos
                if (binding.userWeight.getText().toString().isEmpty() ||
                        binding.userHeight.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Reemplazar comas por puntos para soportar diferentes formatos regionales
                String pesoStr = binding.userWeight.getText().toString().replace(',', '.');
                String alturaStr = binding.userHeight.getText().toString().replace(',', '.');

                double peso = Double.parseDouble(pesoStr);
                double altura = Double.parseDouble(alturaStr);
                openResultActivity(peso,altura);

                // Validar que los valores sean positivos
                if (peso <= 0 || altura <= 0) {
                    Toast.makeText(this, getString(R.string.invalid_values_message), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Calcular y mostrar el IMC
                double imc = calcularIMC(peso, altura);
                //binding.textResult.setText(getString(R.string.result_message, imc));

            } catch (NumberFormatException ex) {
                Log.e("MainActivity", "Error al parsear números", ex);
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double calcularIMC(double peso, double altura) {
        double alturaMetros = altura / 100;
        return peso / (alturaMetros * alturaMetros);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    private void openResultActivity(double peso, double altura) {
        double imc = calcularIMC(peso, altura);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.WEIGHT_KEY,peso);
        intent.putExtra(ResultActivity.HEIGHT_KEY,altura);
        intent.putExtra(ResultActivity.IMC_KEY, imc);
        startActivity(intent);
    }

}