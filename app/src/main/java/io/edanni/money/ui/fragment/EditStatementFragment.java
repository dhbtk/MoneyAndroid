package io.edanni.money.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Statement;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;

/**
 * Created by eduardo on 09/12/16.
 */
@EFragment(R.layout.fragment_statement_form)
@OptionsMenu(R.menu.form)
public class EditStatementFragment extends Fragment
{
    private Statement statement;

    public static EditStatementFragment newInstance( Integer id )
    {
        EditStatementFragment fragment = new EditStatementFragment_();
        Bundle arguments = new Bundle();
        arguments.putSerializable( "id", id );
        fragment.setArguments( arguments );
        return fragment;
    }
}
