package cuonghtph34430.poly.lab1_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCityActivity extends AppCompatActivity {

    private EditText edtCityName, edtCityState, edtCityCountry, edtCityPopulation;
    private Button btnAddCity;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        edtCityName = findViewById(R.id.edtCityName);
        edtCityState = findViewById(R.id.edtCityState);
        edtCityCountry = findViewById(R.id.edtCityCountry);
        edtCityPopulation = findViewById(R.id.edtCityPopulation);
        btnAddCity = findViewById(R.id.btnAddCity);

        db = FirebaseFirestore.getInstance();

        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityToFirestore();
            }
        });
    }

    private void addCityToFirestore() {
        String cityName = edtCityName.getText().toString();
        String cityState = edtCityState.getText().toString();
        String cityCountry = edtCityCountry.getText().toString();
        String cityPopulation = edtCityPopulation.getText().toString();

        Map<String, Object> cityData = new HashMap<>();
        cityData.put("name", cityName);
        cityData.put("state", cityState);
        cityData.put("country", cityCountry);
        cityData.put("population", Integer.parseInt(cityPopulation));

        // Thêm dữ liệu vào Firestore
        db.collection("cities")
                .add(cityData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddCityActivity.this, "Thêm thành phố thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang MainActivity
                        Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                        startActivity(intent);

                        // Kết thúc màn hình hiện tại
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCityActivity.this, "Lỗi khi thêm thành phố: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
