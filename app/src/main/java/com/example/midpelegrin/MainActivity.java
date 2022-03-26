package com.example.midpelegrin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.app.AlertDialog;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id, name, dsc, price, qty;
    Button create, retrieve, update, delete, retrieveid;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.prodID);
        name = findViewById(R.id.prodName);
        dsc = findViewById(R.id.prodDesc);
        price = findViewById(R.id.prodPrice);
        qty = findViewById(R.id.prodqty);

        create = findViewById(R.id.createbtn);
        retrieve = findViewById(R.id.retBtn);
        retrieveid = (Button) findViewById(R.id.retrieveID);
        update = findViewById(R.id.updatebtn);
        delete = findViewById(R.id.deletebtn);

        DB = new DBHelper(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTXT = id.getText().toString();
                String nameTXT = name.getText().toString();
                String dscTXT = dsc.getText().toString();
                String priceTXT = price.getText().toString();
                String qtyTXT = qty.getText().toString();

                if (idTXT.isEmpty()) {
                    id.setError("Bread ID is required!");
                    id.requestFocus();
                    return;
                }
                if (nameTXT.isEmpty()) {
                    name.setError("Bread name is required!");
                    name.requestFocus();
                    return;
                }
                if (dscTXT.isEmpty()) {
                    dsc.setError("Bread description is required!");
                    dsc.requestFocus();
                    return;
                }
                if (priceTXT.isEmpty()) {
                    price.setError("Bread price is required!");
                    price.requestFocus();
                    return;
                }
                if (qtyTXT.isEmpty()) {
                    qty.setError("Bread quantity is required!");
                    qty.requestFocus();
                    return;
                }

                if (idTXT.isEmpty() || nameTXT.isEmpty() || dscTXT.isEmpty() || priceTXT.isEmpty() || qtyTXT.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill up required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkinsertdata = DB.insertuserdata(idTXT, nameTXT, dscTXT, priceTXT, qtyTXT);
                    if (checkinsertdata == true) {
                        Toast.makeText(MainActivity.this, "Bread Inserted", Toast.LENGTH_SHORT).show();
                        id.getText().clear();
                        name.getText().clear();
                        dsc.getText().clear();
                        price.getText().clear();
                        qty.getText().clear();
                    } else
                        Toast.makeText(MainActivity.this, "Bread Not Inserted.Bread ID Exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTXT = id.getText().toString();
                String nameTXT = name.getText().toString();
                String dscTXT = dsc.getText().toString();
                String priceTXT = price.getText().toString();
                String qtyTXT = qty.getText().toString();

                if (idTXT.isEmpty()) {
                    id.setError("Bread ID is required!");
                    id.requestFocus();
                    return;
                }
                if (nameTXT.isEmpty()) {
                    name.setError("Bread name is required!");
                    name.requestFocus();
                    return;
                }
                if (dscTXT.isEmpty()) {
                    dsc.setError("Bread description is required!");
                    dsc.requestFocus();
                    return;
                }
                if (priceTXT.isEmpty()) {
                    price.setError("Bread price is required!");
                    price.requestFocus();
                    return;
                }
                if (qtyTXT.isEmpty()) {
                    qty.setError("Bread quantity is required!");
                    qty.requestFocus();
                    return;
                }

                if (idTXT.isEmpty() || nameTXT.isEmpty() || dscTXT.isEmpty() || priceTXT.isEmpty() || qtyTXT.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill up required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkupdatedata = DB.updateuserdata(idTXT, nameTXT, dscTXT, priceTXT, qtyTXT);
                    if (checkupdatedata == true) {
                        Toast.makeText(MainActivity.this, "Bread Updated", Toast.LENGTH_SHORT).show();
                        id.getText().clear();
                        name.getText().clear();
                        dsc.getText().clear();
                        price.getText().clear();
                        qty.getText().clear();
                    } else
                        Toast.makeText(MainActivity.this, "Bread Not Updated.Bread ID does not Exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTXT = id.getText().toString();
                Boolean checkudeletedata = DB.deletedata(idTXT);

                if (idTXT.isEmpty()) {
                    id.setError("Bread ID is required!");
                    id.requestFocus();
                    return;
                }

                if (checkudeletedata == true) {
                    Toast.makeText(MainActivity.this, "Bread Orders Deleted", Toast.LENGTH_SHORT).show();
                    id.getText().clear();
                    name.getText().clear();
                    dsc.getText().clear();
                    price.getText().clear();
                    qty.getText().clear();
                } else
                    Toast.makeText(MainActivity.this, "Bread Orders Not Deleted.Bread ID does not Exists!", Toast.LENGTH_SHORT).show();
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Product/s Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Description: " + res.getString(2) + "\n");
                    buffer.append("Price: " + res.getString(3) + "\n");
                    buffer.append("Quantity: " + res.getString(4) + "\n\n");
                }

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Product Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        retrieveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = id.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("BakeShop.db", Context.MODE_PRIVATE,null);
                Cursor c = simpledb.rawQuery("Select * from Derivativedetails where id ='"+n+"'",null);
                if (c.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "No Product/s Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("ID: " + c.getString(0) + "\n");
                    buffer.append("Name: " + c.getString(1) + "\n");
                    buffer.append("Description: " + c.getString(2) + "\n");
                    buffer.append("Price: " + c.getString(3) + "\n");
                    buffer.append("Quantity: " + c.getString(4) + "\n\n");

                }
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Products");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}