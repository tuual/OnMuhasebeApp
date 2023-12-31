package com.example.sqlliteuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sqlliteuygulama.databinding.ActivityAddPersonBinding;
import com.example.sqlliteuygulama.databinding.ActivityMainBinding;

import java.sql.Connection;

public class addPersonActivity extends AppCompatActivity {

    private ActivityAddPersonBinding binding;
    ConnectionHelper dbHelper;
    Boolean actionButtonVisible;
    String emailValue;
    private String etEmail,etKadi,etSifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new ConnectionHelper(getApplicationContext());

        binding.btnAdd.setOnClickListener(view -> {
            VeriEkleme();
        });


        binding.personList.setVisibility(View.GONE);
        binding.addPerson.setVisibility(View.GONE);
        binding.personListText.setVisibility(View.GONE);
        binding.addPersonText.setVisibility(View.GONE);
        actionButtonVisible = false;

        binding.add.setOnClickListener(view -> {
            if (!actionButtonVisible){
                binding.personList.show();
                binding.addPerson.show();
                binding.personListText.setVisibility(View.VISIBLE);
                binding.addPersonText.setVisibility(View.VISIBLE);
                actionButtonVisible = true;

            }
            else{
                binding.personList.hide();
                binding.addPerson.hide();
                binding.personListText.setVisibility(View.GONE);
                binding.addPersonText.setVisibility(View.GONE);
                actionButtonVisible = false;

            }

        });

        binding.addPerson.setOnClickListener(view -> {
            Intent intent = new Intent(this,addPersonActivity.class);
            startActivity(intent);
            finish();
        });
        binding.personList.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    public void VeriEkleme() {

        try {
            etEmail = binding.etemail.getText().toString().trim();
            etKadi = binding.etkullaniciadi.getText().toString().trim();
            etSifre = binding.etsifre.getText().toString().trim();
            if (!etKadi.matches("") && !binding.etsifre.getText().toString().matches("") && !binding.etemail.getText().toString().matches("")) {
                emailValue = binding.etemail.getText().toString();
                if (!emailValue.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                    String query = "INSERT INTO tblUser (sirketAdi, sifre, email) VALUES ( '"
                            + binding.etkullaniciadi.getText().toString() + "','"
                            + binding.etsifre.getText().toString() + "','"
                            + binding.etemail.getText().toString() + "')";

                    Toast.makeText(this, "Kayıt Oluşturuldu", Toast.LENGTH_SHORT).show();

                    dbHelper.execSQL(query, addPersonActivity.this);
                    binding.etemail.setText(null);
                    binding.etsifre.setText(null);
                    binding.etkullaniciadi.setText(null);
                }
                else{
                    Toast.makeText(this, "Geçerli Bir Email Adresi Giriniz", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                binding.etkullaniciadi.setError("Boş Geçilemez");
                binding.etemail.setError("Boş Geçilemez");
                binding.etsifre.setError("Boş Geçilemez");

            }

        } catch (Exception se) {
            Toast.makeText(this, se.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}