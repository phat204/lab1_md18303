package cuonghtph34430.poly.lab1_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Logout extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Khởi tạo Firebase Authentication
        auth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);

        if(btnLogout != null) {
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Đăng xuất người dùng
                    auth.signOut();
                    Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển về màn hình đăng nhập
                    finish();
                }
            });
        }
    }
}
