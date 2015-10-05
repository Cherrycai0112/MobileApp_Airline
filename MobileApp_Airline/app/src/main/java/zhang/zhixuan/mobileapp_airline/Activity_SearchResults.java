package zhang.zhixuan.mobileapp_airline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;

public class Activity_SearchResults extends AppCompatActivity {

    private ListView flightResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__search_results);
        Intent intent = getIntent();
        int yearD = intent.getIntExtra("yearD",0);
        int monthOfYearD = intent.getIntExtra("monhOfYearD",0);
        int dayOfMonthD = intent.getIntExtra("dayOfMonthD",0);
        int yearA = intent.getIntExtra("yearA",0);
        int monthOfYearA = intent.getIntExtra("monhOfYearA",0);
        int dayOfMonthA = intent.getIntExtra("dayOfMonthA",0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearD);
        cal.set(Calendar.MONTH, monthOfYearD);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonthD);
        Date dateD = cal.getTime();
        cal.set(Calendar.YEAR, yearA);
        cal.set(Calendar.MONTH, monthOfYearA);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonthA);
        Date dateA = cal.getTime();
        String origin = intent.getStringExtra("origin");
        String destination = intent.getStringExtra("destination");

        flightResults = (ListView)findViewById(R.id.lv_flightRecord);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
