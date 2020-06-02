package softeng2.teamhortons.myxa.ui.menu.home.schedule;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import softeng2.teamhortons.myxa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietScheduleFragment extends Fragment
{
    public DietScheduleFragment() { }
    CalendarView calendar_view;
    TextView calendar_year;
    TextView calendar_date;
    FloatingActionButton heart_button;
    Calendar calendar = Calendar.getInstance();

    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_schedule,null);
        View popup = inflater.inflate(R.layout.diaglog_diet_plan_popup_layout, null);
        calendar_view = (CalendarView) view.findViewById(R.id.diet_schedule_calendar_view);
        calendar_year = (TextView) view.findViewById(R.id.diet_schedule_year_text);
        calendar_date = (TextView) view.findViewById(R.id.diet_schedule_date_text);


        final String[]monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        final String calendarMonth = monthName[Calendar.MONTH + 2];
        calendar_date.setText(calendarMonth + ", " +Integer.toString(calendar.get(Calendar.DATE)));
        calendar_year.setText(Integer.toString(calendar.get(Calendar.YEAR)));

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int date_year, final int date_month, final int date_day)
            {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.diaglog_diet_plan_popup_layout, null);
                String monthArray = monthName[date_month];
                String Year = Integer.toString(date_year);
                String Date = monthArray + ", " + date_day;
                calendar_date.setText(Date);
                calendar_year.setText(Year);

                if(date_day == 25)
                {
                    dialogBuilder.setView(dialogView);
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                    heart_button = dialog.findViewById(R.id.diet_plan_heart_button);
                    heart_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            Toast toast = Toast.makeText(getContext(),"Diet was Added to Favorites", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }

            }
        });



        return view;
    }
}
