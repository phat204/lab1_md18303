package phatdtph37313.fpoly.lab1_md18303;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {
    private EditText emailEdt, passEdt;
    private Button btnLogin;
    private TextView txtSign, txtForgot;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        mAuth = FirebaseAuth.getInstance();
        emailEdt = findViewById(R.id.edtUserDN);
        passEdt = findViewById(R.id.edtPassDN);
        btnLogin = findViewById(R.id.btnDangNhap);
        txtSign = findViewById(R.id.txtSignUp);
        txtForgot = findViewById(R.id.txtForgot);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        txtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot();
            }
        });
    }

    private void forgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(dialogView);

        EditText emailEdt = dialogView.findViewById(R.id.edtEmail);
        Button btnSend = dialogView.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(emailAddress)) {
                    mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DangNhap.this, "Vui lòng kiểm tra mail để cập nhật lại mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DangNhap.this, "Lỗi gửi email", Toast.LENGTH_SHORT).show();
                            }
                            builder.create().dismiss();
                        }
                    });
                } else {
                    Toast.makeText(DangNhap.this, "Vui lòng nhập địa chỉ email của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    private void signin() {
        Intent i = new Intent(DangNhap.this, DangKy.class);
        startActivity(i);
    }

    private void login() {
        String email, pass;
        email = emailEdt.getText().toString();
        pass = passEdt.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Vui lòng nhập pass", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}