package zhang.zhixuan.mobileapp_airline;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchResults extends AppCompatActivity {
    TextView originTV;
    TextView destinationTV;
    TextView depD;
    TextView retD;
    String originStr;
    String destinationStr;
    private int year_R,month_R,day_R;
    private int year_D,month_D,day_D;
    boolean oneWayOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        originTV = (TextView)findViewById(R.id.sr_tv_ori);
        destinationTV = (TextView)findViewById(R.id.sr_tv_dest);
        depD = (TextView)findViewById(R.id.sr_tv_depD);
        retD = (TextView)findViewById(R.id.sr_tv_retD);
        Intent intent = getIntent();
        //set parameter
        originStr = intent.getStringExtra("origin");
        destinationStr = intent.getStringExtra("destination");
        year_D = intent.getIntExtra("year_D", 0);
        month_D = intent.getIntExtra("month_D",0);
        day_D = intent.getIntExtra("day_D", 0);
        oneWayOrNot = intent.getBooleanExtra("OneWayOrNot", false);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        System.out.println("one way or not "+oneWayOrNot);
        if(!oneWayOrNot){
            year_R = intent.getIntExtra("year_R", 0);
            month_R = intent.getIntExtra("month_R",0);
            day_R = intent.getIntExtra("day_R",0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year_R);
            calendar.set(Calendar.MONTH, month_R);
            calendar.set(Calendar.DAY_OF_MONTH, day_R);
            Date returnD = calendar.getTime();
            retD.setText("Return Date: "+simpleDateFormat.format(returnD).toString());

        }
        else
        {
            retD.setText("");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year_D);
        calendar.set(Calendar.MONTH, month_D);
        calendar.set(Calendar.DAY_OF_MONTH, day_D);
        Date departureD = calendar.getTime();
        depD.setText("Departure Date: "+simpleDateFormat.format(departureD).toString());


        originTV.setText("Origin: "+originStr);
        destinationTV.setText("Destination: "+destinationStr);

        if(oneWayOrNot){

            searchFlights_OneWay(originStr,destinationStr,departureD);
        }else
        {
//            searchFlights_RoundWay();
        }


    }
    public void searchFlights_OneWay(String originStr, String destinationStr, Date departureD){
        System.err.println("enter searchFlights_Oneway");
        ClassAsyncTask_Flookup classAsyncTask_flookup = new ClassAsyncTask_Flookup();
        classAsyncTask_flookup.execute("https://172.25.98.227:8181/MerlionAirlinesSystem/webresources/generic?origin="+originStr+"&destination="+destinationStr+"&departureD="+departureD.toString());

    }
    public class ClassAsyncTask_Flookup extends AsyncTask<String, Void, String> {



        public String doInBackground(String... str) {
            System.err.println("enter do in background");return getJSON(str[0]);
        }

        public void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);

                       } catch (Exception e) {
                Log.d("ReadCurrencyJSON", e.getLocalizedMessage());
            }
            Toast.makeText(getApplication(), "in onPostExecute of Activity4, result = " + result, Toast.LENGTH_LONG).show();
        }
    }
    public String getJSON(String urlStr) {

        URL url = convertToUrl(urlStr);

        HttpURLConnection httpURLConnection = null;

        int responseCode;

        StringBuilder stringBuilder = new StringBuilder();

        String line;

        System.err.println("enter getJSON URL"+urlStr);

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode == httpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return stringBuilder.toString();
    }


    // the following code convertToUrl is from
    // http://fancifulandroid.blogspot.sg/2013/07/android-convert-string-to-valid-url.html
    //
    private URL convertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
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
