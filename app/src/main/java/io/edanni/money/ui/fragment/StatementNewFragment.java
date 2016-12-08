package io.edanni.money.ui.fragment;

import android.support.v4.app.DialogFragment;
import io.edanni.money.R;
import io.edanni.money.ui.activity.MainActivity;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by eduardo on 08/12/16.
 */
@EFragment(R.layout.fragment_statement_new_dialog)
public class StatementNewFragment extends DialogFragment
{
    @Click
    void close()
    {
        getDialog().dismiss();
    }

    @Click
    void spending()
    {
        ((MainActivity) getTargetFragment().getActivity()).changeToFragment( new NewCreditFragment_() );
        getDialog().dismiss();
    }
}
