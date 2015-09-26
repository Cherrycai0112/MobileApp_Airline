package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int year_D,month_D,day_D;
    private int year_R,month_R,day_R;
    private int departDialogId = 1;
    private int returnDialogId = 2;
    Button main_btn_departDate;
    Button main_btn_returnDate;
    RadioButton main_radioBtn_roundTrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Calendar calendar =Calendar.getInstance();
        year_D = calendar.get(Calendar.YEAR);
        month_D = calendar.get(Calendar.MONTH);
        day_D = calendar.get(Calendar.DAY_OF_MONTH);
        year_R = calendar.get(Calendar.YEAR);
        month_R = calendar.get(Calendar.MONTH);
        day_R = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void main_btn_pickDate (View view) {
        if (view.getId() == R.id.main_btn_departDate) {
            showDialog(departDialogId);
        } else if (view.getId() == R.id.main_btn_returnDate) {
            showDialog(returnDialogId);
        }


    }

    @Override
    protected Dialog onCreateDialog (int id){
        if (id == departDialogId) {
            return new DatePickerDialog(this,datePickerListenerD, year_D, month_D,day_D);
        } else if (id == returnDialogId){
            return new DatePickerDialog(this,datePickerListenerR, year_R, month_R,day_R);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListenerD = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_D = year;
            month_D = monthOfYear + 1;
            day_D = dayOfMonth;
            main_btn_departDate = (Button)findViewById(R.id.main_btn_departDate);
            main_btn_departDate.setText(day_D + "/" + month_D + "/" + year_D);
            Toast.makeText(MainActivity.this, day_D + "/" + month_D + "/" +year_D, Toast.LENGTH_SHORT).show();
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListenerR = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_R = year;
            month_R = monthOfYear + 1;
            day_R = dayOfMonth;
            main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
            main_btn_returnDate.setText(day_R + "/" + month_R + "/" + year_R);
            Toast.makeText(MainActivity.this, day_R + "/" + month_R + "/" +year_R, Toast.LENGTH_SHORT).show();
        }
    };

    public void main_radioBtn_roundTrip(View view) {
        main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
        main_btn_returnDate.setEnabled(false);
    }

    public void main_radioBtn_oneWay(View view) {
        main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
        main_btn_returnDate.setEnabled(true);
    }

    public void main_btn_search (View view) {
        Intent intent = new Intent (this, MainActivity.class);
        main_radioBtn_roundTrip = (RadioButton)findViewById(R.id.main_radioBtn_roundTrip);
        if (main_radioBtn_roundTrip.isChecked()){
            intent.putExtra("returnDate","i am return date");
        }
        intent.putExtra("departDate","i am depart date");
        intent.putExtra("origin","here");
        intent.putExtra("destination","there");
        startActivity(intent);
    }
}
