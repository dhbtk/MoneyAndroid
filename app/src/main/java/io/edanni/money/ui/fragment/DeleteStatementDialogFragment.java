package io.edanni.money.ui.fragment;

import android.support.v4.app.DialogFragment;
import io.edanni.money.R;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by eduardo on 09/12/16.
 */
@EFragment(R.layout.delete_statement_confirm)
public class DeleteStatementDialogFragment extends DialogFragment
{
    private OnDeleteButtonClickedListener listener;

    @Click
    void delete()
    {
        if ( listener != null )
        {
            listener.onDeleteButtonClicked();
        }
        getDialog().dismiss();
    }

    @Click
    void cancel()
    {
        getDialog().dismiss();
    }

    public OnDeleteButtonClickedListener getListener()
    {
        return listener;
    }

    public void setListener( OnDeleteButtonClickedListener listener )
    {
        this.listener = listener;
    }

    public interface OnDeleteButtonClickedListener
    {
        void onDeleteButtonClicked();
    }
}
