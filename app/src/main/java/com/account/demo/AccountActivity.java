package com.account.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AccountActivity extends AppCompatActivity {

    private EditText etMchIdPay;
    private EditText etOrderId;
    private EditText etAppName;
    private EditText etMchIdBind;
    private EditText etUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        etMchIdPay = findViewById(R.id.mch_id_pay);
        etOrderId = findViewById(R.id.order_id);
        etAppName = findViewById(R.id.app_name);

        etMchIdBind = findViewById(R.id.mch_id_bind);
        etUserId = findViewById(R.id.user_id);

//        etMchIdPay.setText("123456789012345678");
//        etOrderId.setText("123456789012345678");
//        etAppName.setText("demo");
//
//        etMchIdBind.setText("123456789012345678");
//        etUserId.setText("22610494112");

        startPay();
        startBind();
    }

    private void startPay() {
        findViewById(R.id.button_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mchId = etMchIdPay.getText().toString();
                String orderId = etOrderId.getText().toString();
                String appName = etAppName.getText().toString();
                AccountUtils.startPay(AccountActivity.this, mchId, orderId, appName);
            }
        });
    }

    private void startBind() {
        findViewById(R.id.button_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mchId = etMchIdBind.getText().toString();
                String userId = etUserId.getText().toString();
                AccountUtils.startBind(AccountActivity.this, mchId, userId);
            }
        });
    }
}