package com.vn.vietatech.combo;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.model.Setting;
import com.vn.vietatech.utils.SettingUtil;

import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    private EditText txtServerIp;
    private EditText txtStoreNo;
    private EditText txtPosGroup;
    private EditText txtPosId;
    private EditText txtVAT;
    private EditText txtType;
    private EditText txtServiceTax;
    private EditText txtSection;
    private Button btnSaveConfig;
    private Button btnTestConnect;
    private Button btnCloseSetting;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("Config");

        txtServerIp = findViewById(R.id.txtServerIP);
        txtStoreNo = findViewById(R.id.txtStoreNo);
        txtPosGroup = findViewById(R.id.txtPosGroup);
        txtPosId = findViewById(R.id.txtPosId);
        txtVAT = findViewById(R.id.txtVAT);
        txtType = findViewById(R.id.txtType);
        txtServiceTax = findViewById(R.id.txtServiceTax);
        txtSection = findViewById(R.id.txtSection);

        btnSaveConfig = findViewById(R.id.btnSaveConfig);
        btnCloseSetting = findViewById(R.id.btnCloseSetting);
        btnTestConnect = findViewById(R.id.btnTestConnect);

        // load settings
        loadSettings();

        btnCloseSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSaveConfig.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting setting = new Setting();
                setting.setServerIP(txtServerIp.getText().toString());
                setting.setStoreNo(txtStoreNo.getText().toString());
                setting.setPosGroup(txtPosGroup.getText().toString());
                setting.setPosId(txtPosId.getText().toString());
                setting.setVat(txtVAT.getText().toString());
                setting.setType(txtType.getText().toString());
                setting.setServiceTax(txtServiceTax.getText().toString());
                setting.setSection(txtSection.getText().toString());

                try {
                    SettingUtil.write(setting, getApplicationContext());
                    Toast.makeText(getApplicationContext(),
                            "Save OK", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTestConnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean result = Boolean.parseBoolean(new AbstractAPI(context).isSQLConnected());

                    if (result) {
                        Toast.makeText(getApplicationContext(), "Connection OK", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Connection FAILED", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loadSettings() {
        Setting setting;
        try {
            setting = SettingUtil.read(getApplicationContext());
            if (setting != null) {
                txtServerIp.setText(setting.getServerIP());
                txtStoreNo.setText(setting.getStoreNo());
                txtPosGroup.setText(setting.getPosGroup());
                txtPosId.setText(setting.getPosId());
                txtVAT.setText(setting.getVat());
                txtType.setText(setting.getType());
                txtSection.setText(setting.getSection());
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
