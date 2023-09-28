package com.example.sqlliteuygulama;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listModel {
    String sirketAdi, sifre, email,ID;
    String sorgu;
    Boolean tamamlandı = false;
    Connection conn;
    ResultSet rs;
    ConnectionHelper dbHelper;
    Context context;



    public listModel(Context context) {

        this.context = context;
    }

    public List<Map<String, String>> getList() {
        dbHelper = new ConnectionHelper(context);
        List<Map<String, String>> data;
        data = new ArrayList<Map<String, String>>();
        try {
            conn = dbHelper.connection;
            sorgu = "SELECT * FROM tblUser";
            Statement smt = conn.createStatement();
            rs = smt.executeQuery(sorgu);
            while (rs.next()) {
                Map<String, String> tabloverisi = new HashMap<String, String>();
                tabloverisi.put("sirketAdi", rs.getString("sirketAdi"));
                tabloverisi.put("Sifre", rs.getString("sifre"));
                tabloverisi.put("Email", rs.getString("Email"));
                tabloverisi.put("ID", rs.getString("ID"));

                ID = rs.getString("ID");
                sirketAdi = rs.getString("sirketAdi");
                sifre = rs.getString("sifre");
                email = rs.getString("Email");


                data.add(tabloverisi);


            }
            conn.close();
        } catch (SQLException ex) {
            Log.e("HATA", ex.getMessage());
        }
        return data;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSirketAdi() {
        return sirketAdi;
    }

    public void setSirketAdi(String sirketAdi) {
        this.sirketAdi = sirketAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
