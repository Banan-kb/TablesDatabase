package com.example.a1109_tablesdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;

    EditText productQuantity, productName;
    TextView productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB= new DatabaseHelper(this);

        Button bttnAdd = (Button)findViewById(R.id.buttonADD);
        Button bttnFind = (Button)findViewById(R.id.buttonFIND);
        Button bttnDelete = (Button)findViewById(R.id.buttonDEL);

        productID = (TextView) findViewById(R.id.productID);
        productQuantity = (EditText) findViewById(R.id.productQuantity);
        productName = (EditText) findViewById(R.id.productName);


        bttnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!myDB.addData("FirstItem", "9"))
//                    Toast.makeText(MainActivity.this,"Insertion Failed",Toast.LENGTH_SHORT).show();
//
//                myDB.addData("SecondItem","13");


                String pnameVal= productName.getText().toString();
                String pQuant= productQuantity.getText().toString();


                myDB.addData( pnameVal,pQuant );
                Toast.makeText(MainActivity.this, "Successful ADD",Toast.LENGTH_SHORT ).show();

            }
        });

        bttnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Cursor cursor = myDB.structuredQuery(4);
//                String cID = cursor.getString(0);
//                String cName = cursor.getString(1);
//                String cPrQuant = cursor.getString(2);
//                Toast.makeText(MainActivity.this,
//                        cID+","+cName+","+cPrQuant, Toast.LENGTH_LONG).show();
//

                String pnameVal= productName.getText().toString();

                Cursor cursor = myDB.getSpecificResult(pnameVal);

                if(cursor.getCount()== 0){
                    Toast.makeText(MainActivity.this, "Error: item does not exist \nHint: make sure spelling is correct", Toast.LENGTH_LONG).show();

                }else {


                    String cID = cursor.getString(0);
                    String cName = cursor.getString(1);
                    String cPrQuant = cursor.getString(2);

                    productID.setText(cID);
                    productName.setText(cName);
                    productQuantity.setText(cPrQuant);

                    Toast.makeText(MainActivity.this, cID + "," + cName + "," + cPrQuant, Toast.LENGTH_SHORT).show();

                }


            }
        });

        bttnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idVal= productID.getText().toString();

                myDB.deleteData(idVal);

                Toast.makeText(MainActivity.this, "Successful DELETE",Toast.LENGTH_SHORT ).show();

            }
        });
    }
}