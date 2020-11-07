package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;

    private Double operand1 =null;
    private Double operand2 = null;
    private String pendingOperation= "=";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //travame ta stoixeia apo to layout
        result =findViewById(R.id.resultTxt);
        newNumber = findViewById((R.id.newNumberTxt));
        operation=findViewById(R.id.operationLabel);

        //theloume ena listener sta koumpia pou apla bazoun keimeno
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button buttonDot = findViewById(R.id.buttonDot);

        View.OnClickListener listener1= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buttonClicked = (Button) view;
                newNumber.append(buttonClicked.getText());

            }
        };
        button0.setOnClickListener(listener1);
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener1);
        button3.setOnClickListener(listener1);
        button4.setOnClickListener(listener1);
        buttonDot.setOnClickListener(listener1);



        //kai  ena listener sta ipoloipa pou kanoun mia praxi
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonClear = findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNumber.setText("");
                pendingOperation = "=";
                result.setText("");
            }
        });

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b= (Button)v; //pernei to koumpi pou patithike (=,+,-)
                String operationString =b.getText().toString();  //pernei to operation apo to koumpi
                String newNumberString =newNumber.getText().toString(); // pernei kai ton arithmo pou einai gramenos
                try{
                    Double newValueDouble =Double.valueOf(newNumberString); //ton arithmo ton metatrepei se double kai mazi me to operation
                    performOperation(newValueDouble, operation); //ton stelnei se mia methodo pou tha kanei tin praxi
                }catch (NumberFormatException nfe){
                    newNumber.setText("");//an pai kati strava midenizei to konter
                pendingOperation = operationString; //krataei kai to pending operation se mia metravliti
                operation.setText(pendingOperation); // kai to vazei sitn othoni
            }
        }

        };
        buttonPlus.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonEquals.setOnClickListener(operationListener);




    }

    private void performOperation(Double newValueDouble, TextView operation) {

        if(operand1==null){
            operand1=newValueDouble;
        }else{
            operand2=newValueDouble;


            switch (pendingOperation){
                case "=":
                    operand1=operand2;
                    break;
                case "+":
                    operand1+=operand2;
                    break;
                case "-":
                    operand1-=operand2;
                    break;

            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
      }

    @Override//enas kouvas gia na apothikeuso pragmata ti thelo na apothikeuso: topendingoperation kai ton operand
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("PENDING_OPERATION",pendingOperation);
        if(operand1 != null){
            outState.putDouble("OPERAND1_STATE",operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override//edo travao auta pou esosa sto outstate
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString("PENDING_OPERATION");
        operand1=savedInstanceState.getDouble("OPERAND1_STATE");
        operation.setText(pendingOperation);
    }
}