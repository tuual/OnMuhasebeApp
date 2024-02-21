package com.example.sqlliteuygulama;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper extends SQLiteOpenHelper {
    Connection connection;
    String username, password, ip, port, database;


    public ConnectionHelper(Context context) {
        super(context, "OnMuhasebe.db", null, 2);
        ip = "";
        database = "OnMuhasebe";
        username = "";
        password = "";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectionUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database + ";user=" + username + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            Toast.makeText(context, "HATA Bağlanılamadı" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }




    public void execSQL(String sorgu, Context co) throws SQLException {


        try {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sorgu);


        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tblUser (ID INTEGER PRIMARY KEY AUTOINCREMENT, sirketAdi TEXT, sifre TEXT, email TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");
        onCreate(sqLiteDatabase);
    }
}
