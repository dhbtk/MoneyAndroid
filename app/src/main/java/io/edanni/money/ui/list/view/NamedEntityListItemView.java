package io.edanni.money.ui.list.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import io.edanni.money.domain.entity.NamedEntity;

/**
 * Created by eduardo on 09/12/16.
 */
public class NamedEntityListItemView extends TextView
{
    public NamedEntityListItemView( Context context )
    {
        super( context );
        TypedValue typedValue = new TypedValue();
        final DisplayMetrics metrics = new android.util.DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        wm.getDefaultDisplay().getMetrics( metrics );
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            setTextAppearance( android.R.attr.textAppearanceListItemSmall );
        }
        ((Activity) context).getTheme().resolveAttribute( android.R.attr.listPreferredItemHeightSmall, typedValue, true );
        setMinHeight( (int) typedValue.getDimension( metrics ) );
        setGravity( Gravity.CENTER_VERTICAL );
        setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        ((Activity) context).getTheme().resolveAttribute( android.R.attr.listPreferredItemPaddingStart, typedValue, true );
        setPadding( (int) typedValue.getDimension( metrics ), 0, 0, 0 );
    }

    public void bind( NamedEntity entity )
    {
        setText( entity.name );
    }
}
