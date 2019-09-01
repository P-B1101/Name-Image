package m.pedram.nameprofileimage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import m.pedram.name_image.NameImage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = new NameImage.Builder(this)
                .withFirstName("پدرام")
                .withLastName("محمد علی پور")
                .build()
                .makeCircle()
                .createBitmap();
        final AppCompatImageView imageView = findViewById(R.id.test_image);
        imageView.setImageBitmap(bitmap);
        final EditText nameEdt = findViewById(R.id.name);
        final EditText lastNameEdt = findViewById(R.id.lastname);
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdt.getText().toString().trim();
                String lastName = lastNameEdt.getText().toString().trim();
                Bitmap bitmap = new NameImage.Builder(MainActivity.this)
                        .withFirstName(name)
                        .withLastName(lastName)
                        .build()
                        .makeCircle()
                        .createBitmap();
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
