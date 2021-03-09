package org.fhi360.lamis.mobile.lite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.neurotec.samples.view.BaseDialogFragment;

import org.fhi360.lamis.mobile.lite.R;

public class EnrollmentDialogFragment extends BaseDialogFragment {

    // ===========================================================
    // Public types
    // ===========================================================

    public interface EnrollmentDialogListener {
        void onEnrollmentIDProvided(String id);
    }

    // ===========================================================
    // Private fields
    // ===========================================================

    private EnrollmentDialogListener mListener;
    private EditText mEditText;

    // ===========================================================
    // Public constructor
    // ===========================================================

    public EnrollmentDialogFragment() {
    }

    // ===========================================================
    // Public methods
    // ===========================================================

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EnrollmentDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EnrollmentDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_enrollment, null);
        mEditText = view.findViewById(R.id.enrollment_id);
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(mEditText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return !mEditText.getText().toString().equals("");
            }
            return false;
        });

        builder.setView(view);
        builder.setTitle(R.string.msg_enter_id);
        builder.setPositiveButton(getString(R.string.msg_enroll), (dialog, id) -> mListener.onEnrollmentIDProvided(mEditText.getText().toString().trim()));
        builder.setNegativeButton(getString(R.string.msg_cancel), (dialog, id) -> dialog.cancel());
        return builder.create();
    }
}