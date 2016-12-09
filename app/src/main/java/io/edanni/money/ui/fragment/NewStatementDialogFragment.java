package io.edanni.money.ui.fragment;

import android.support.v4.app.DialogFragment;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Debit;
import io.edanni.money.ui.activity.MainActivity;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by eduardo on 08/12/16.
 */
@EFragment(R.layout.fragment_statement_new_dialog)
public class NewStatementDialogFragment extends DialogFragment
{
    @Click
    void close()
    {
        getDialog().dismiss();
    }

    @Click
    void spending()
    {
        NewStatementFragment fragment = NewStatementFragment.newInstance( new Credit() );
        ((MainActivity) getTargetFragment().getActivity()).changeToFragment( fragment );
        getDialog().dismiss();
    }

    @Click
    void income()
    {
        NewStatementFragment fragment = NewStatementFragment.newInstance( new Debit() );
        ((MainActivity) getTargetFragment().getActivity()).changeToFragment( fragment );
        getDialog().dismiss();
    }
}
