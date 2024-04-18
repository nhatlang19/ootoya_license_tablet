package com.vn.vietatech.combo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.api.async.LoginAsync;
import com.vn.vietatech.model.Setting;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //LicenseUtils licenseUtils;
    final int MY_PERMISSIONS_REQUEST = 1;
    EditText txtUserName;
    EditText txtPassword;
    EditText txtLicense;
    EditText txtPasscode;
    LinearLayout linearLayoutLicense;
    LinearLayout linearLayoutLogin;
    MyApplication globalVariable;
    boolean continued = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.disableStrictMode();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))
//                {
////                Không hỏi lần kế tiếp
//                    System.out.println("12323");
//                }
//                else
                {
                    this.requestPermissions(
                            new String[]{
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST);
                }
            } else {
                continued = true;
            }

        } else {
            continued = true;
        }

        if (continued) {
            try {
                LoadForm();
            } catch (Exception ex) {
                System.out.println("messages:" + ex.getMessage());
                Toast.makeText(getApplicationContext(),
                        ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void LoadForm() {
        globalVariable = (MyApplication) getApplicationContext();

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnExit = findViewById(R.id.btnExit);
        Button btnActivate = findViewById(R.id.btnActivate);

        linearLayoutLicense = findViewById(R.id.linearLayoutLicense);
        linearLayoutLogin = findViewById(R.id.linearLayoutLogin);

        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtLicense = findViewById(R.id.txtLicense);
        txtPasscode = findViewById(R.id.txtPasscode);


        //licenseUtils = LicenseUtils.getInstance(context);

        /*try {
            if (licenseUtils.read() == null) {
                linearLayoutLicense.setVisibility(LinearLayout.VISIBLE);
                linearLayoutLogin.setVisibility(LinearLayout.INVISIBLE);

                String imei = Utils.getIMEI(context);
                txtPasscode.setText(imei);
                //String license = licenseUtils.createLicenseKey(imei);
                //System.out.println("license:" + license);
            } else {
                linearLayoutLicense.setVisibility(LinearLayout.INVISIBLE);
                linearLayoutLogin.setVisibility(LinearLayout.VISIBLE);
            }

            Cashier cashier = UserUtil.read(this);
            if (cashier != null) {
                txtUserName.setText(cashier.getId().toString().trim());
            }
        } catch (IOException e1) {
        }*/

        linearLayoutLicense.setVisibility(LinearLayout.INVISIBLE);
        linearLayoutLogin.setVisibility(LinearLayout.VISIBLE);

        txtPassword.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        login();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        // exit application
        btnExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // login
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // activate
        /*btnActivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activate();
            }
        });*/
    }

    /*private void activate() {
        String licenseKey = txtLicense.getText().toString();
        if (licenseKey.trim().length() > 0 && licenseUtils.checkLicenseKey(licenseKey)) {
            try {
                licenseUtils.write(licenseKey);
                linearLayoutLicense.setVisibility(LinearLayout.INVISIBLE);
                linearLayoutLogin.setVisibility(LinearLayout.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utils.showAlert(context, "Mã kích hoạt không hợp lệ ");
        }
    }*/

    private void login() {
        Calendar endLicenseDate = Calendar.getInstance();
        endLicenseDate.set(2026, Calendar.JANUARY, 15);

        Calendar nowDate = Calendar.getInstance();

        if (nowDate.after(endLicenseDate)) {
            Utils.showAlert(context, "Chương trình hết hạn license từ ngày: 15/01/2024\nVui lòng cài bản mới nhất.");
            return;
        }


        String username = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Username / password không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        String posBizDate = null;
        try {
            Setting setting = SettingUtil.read(context);
            posBizDate = new AbstractAPI(context).getPOSBizDate(setting.getPosId());
            globalVariable.setPosBizDate(posBizDate);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (posBizDate == null) {
            Utils.playAlarm(context);
            Utils.showAlert(context, "Vui lòng kết ngày trước khi Order");
            return;
        }
        //new UpdateTimeAsync(context).execute();
        new LoginAsync(context, getApplication()).execute(username, password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] != PackageManager.PERMISSION_GRANTED

                    ) {
                        Toast.makeText(context, "Không được phép sử dụng điện thoại và đọc số IMEI, chương trình tạm dừng", Toast.LENGTH_LONG).show();

//                        if (grantResults[1] != PackageManager.PERMISSION_GRANTED)
//                        {
//                            Toast.makeText(context, "Không được gọi điện, chương trình tạm dừng", Toast.LENGTH_LONG).show();
//                        }

                        continued = false;
                    } else {
                        continued = true;
                    }

                    if (continued)
                        LoadForm();
                    else
                        finish();
                }
        }
    }
}
