package phpmysql.example.com.sqldb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void userReg(View view)
    {

    }

    public void userLogin(View view)
    {
        startActivity(new Intent(this, Register.class));
    }
}
