package eu.tuttivers.unite100;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import org.apache.commons.lang3.StringUtils;

public class ConfirmOptionActivationDialogFragment extends DialogFragment {

    private static final String OPTION_SUMMARY_KEY = "optionSummary";
    private static final String OPTION_COMMAND_KEY = "optionCommand";

    public static ConfirmOptionActivationDialogFragment newInstance(String option_summary, String option_command) {
        ConfirmOptionActivationDialogFragment f = new ConfirmOptionActivationDialogFragment();
        Bundle args = new Bundle();
        args.putString(OPTION_SUMMARY_KEY, option_summary);
        args.putString(OPTION_COMMAND_KEY, option_command);
        f.setArguments(args);
        return f;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String optionSummary = bundle.getString(OPTION_SUMMARY_KEY);
        String command = bundle.getString(OPTION_COMMAND_KEY);
        String[] summaries = StringUtils.split(optionSummary, "\n");
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_confirm)
                .setTitle(R.string.dialog_title)
                .setMessage(String.format(getString(R.string.dialog_message), summaries[0], summaries[1]))
                .setPositiveButton(R.string.ok,
                        (dialog, whichButton) -> {
                            dismiss();
                            SMSSender.sendCommand(getContext(), command);
                        }
                )
                .setNegativeButton(R.string.cancel, (dialog, wichButton) -> {
                })
                .setNeutralButton(R.string.check_balance, null /* keep alertDialog on screen */)
                .create();
        alertDialog.setOnShowListener(dialog -> {
            Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(v -> USSDRequest.askBalance(getContext()));
        });
        return alertDialog;
    }
}
