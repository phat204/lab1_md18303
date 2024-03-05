package cuonghtph34430.poly.lab1_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone extends AppCompatActivity {

    private FirebaseAuth mauth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String isXacMinh;
    EditText txtPhone, txtXacMinh;
    Button btnGetOTP, btnDannhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mauth = FirebaseAuth.getInstance();
        txtPhone = findViewById(R.id.edtPhone);
        txtXacMinh = findViewById(R.id.edtOTP);
        btnGetOTP = findViewById(R.id.btnOTP);
        btnDannhap = findViewById(R.id.btnLoginPhone);

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOTP(txtPhone.getText().toString());
            }
        });

        btnDannhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacThucOTP(txtXacMinh.getText().toString());
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                txtXacMinh.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // Xử lý lỗi xác minh
                Toast.makeText(getApplicationContext(), "Xác minh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                isXacMinh = s;
            }
        };
    }

    void getOTP(String phoneNB) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mauth)
                .setPhoneNumber("+84" + phoneNB)
                .setTimeout(120L, TimeUnit.SECONDS) // Sửa lại timeout thành giây
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void XacThucOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(isXacMinh, code);
        signInWithPhone(credential);
    }

    void signInWithPhone(PhoneAuthCredential credential) {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
