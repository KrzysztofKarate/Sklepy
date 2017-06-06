package a.sklepy;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button spozywczy_btn = (Button) findViewById(R.id.spozywczy_btn);
        spozywczy_btn.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("wybor", "sklep_spożywczy");
                startActivity(intent);  //putExtra adres czy cos...
            }
        });

        Button odziezowy_btn = (Button) findViewById(R.id.odziezowy_btn);
        odziezowy_btn.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("wybor", "sklep_odzieżowy");
                startActivity(intent);
            }
        });

        Button zabytki_btn = (Button) findViewById(R.id.zabytki_btn);
        zabytki_btn.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("wybor", "zabytki");
                startActivity(intent);
            }
        });
        Button univ_btn = (Button) findViewById(R.id.univ_btn);
        univ_btn.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("wybor", "uniwersytety");
                startActivity(intent);
            }
        });
    }
}
