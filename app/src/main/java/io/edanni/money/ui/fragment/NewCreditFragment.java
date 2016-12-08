package io.edanni.money.ui.fragment;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;
import io.edanni.money.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by eduardo on 08/12/16.
 */
@EFragment(R.layout.statement_form)
public class NewCreditFragment extends Fragment implements DatePickerDialog.OnDateSetListener
{
    @ViewById
    EditText date;

    @AfterViews
    void startup()
    {
        date.setText( new SimpleDateFormat("dd/MM/yyyy").format( Calendar.getInstance().getTime() ) );
    }

    @Click
    void date()
    {
        String[] dateArray = date.getText().toString().split( "\\/" );
        DatePickerDialog dialog = new DatePickerDialog( getActivity(), this, Integer.parseInt( dateArray[2] ), Integer.parseInt( dateArray[1], 10 ) - 1, Integer.parseInt( dateArray[0], 10 ) );
        dialog.show();
    }

    @Override
    public void onDateSet( DatePicker datePicker, int year, int month, int day )
    {
        Calendar time = Calendar.getInstance();
        time.set( year, month, day );
        date.setText( new SimpleDateFormat("dd/MM/yyyy").format( time.getTime() ) );
    }
}
