package com.sportify.notifications.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-19.
 */

public class YesNoDialogFragment extends DialogFragment{

    private Button btnYes, btnNo;
    private int friendID;
    private String friendName;

    public interface YesNoDialogListener{
        void onFinishYesNoDialog(int friendID, String friendName, String response);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.yes_no_dialog_fragment, null);

        Bundle bundle = getArguments();
        friendID = bundle.getInt("friendID");
        friendName = bundle.getString("friendName");

        btnYes = (Button) view.findViewById(R.id.btnYesDialogFragment);
        btnNo = (Button) view.findViewById(R.id.btnNoDialogFragment);

        btnYes.setOnClickListener(btnListener);
        btnNo.setOnClickListener(btnListener);

        return view;
    }
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YesNoDialogListener activity = (YesNoDialogListener) getActivity();
            String response = ((Button) v).getText().toString().equals(getString(R.string.dialog_fragment_yes)) ? "accepted" : "decline";
            activity.onFinishYesNoDialog(friendID, friendName, response);

            dismiss();
        }
    };
}