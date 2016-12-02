package in.shpt.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by poovarasanv on 2/12/16.
 *
 * @author poovarasanv
 * @project SHPT
 * @on 2/12/16 at 3:05 PM
 */

public class StyledSpinner extends Spinner {
    public StyledSpinner(Context context) {
        super(context);
    }

    public StyledSpinner(Context context, int mode) {
        super(context, mode);
    }

    public StyledSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StyledSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StyledSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public StyledSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
    }

    public StyledSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }
}
