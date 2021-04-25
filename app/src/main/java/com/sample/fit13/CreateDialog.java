package com.sample.fit13;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CreateDialog extends AppCompatDialogFragment {

    private EditText edTitle, edDate, edDuration, edDescription;
    private CreateDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        /*textView.setText("Log insert failed");
        textView.setPadding(20, 30, 20, 10);
        textView.setTextSize(20F);
        textView.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);*/


        builder.setView(view).setTitle("New Log").setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                boolean accepted = true;


                Workout w = new Workout();
                String title = edTitle.getText().toString();
                String date = edDate.getText().toString();
                String duration = edDuration.getText().toString();
                String description = edDescription.getText().toString();

                w.setTitle(title);
                w.setDate(date);
                w.setDuration(duration);
                w.setDescription(description);

                String error = "";


                if(!WorkoutHandler.checkTitle(title)) {
                    error = error + "Title is too long\n";
                    accepted = false;
                }

                if(!WorkoutHandler.checkDate(date)) {
                    error = error + "Incorrect Date format\n";
                    accepted = false;
                }

                if(!WorkoutHandler.checkDuration(duration)) {
                    error = error + "Incorrect Duration format\n";
                    accepted = false;
                }

                if(!WorkoutHandler.checkDescription(description)) {
                    error = error + "Description is too long\n";
                    accepted = false;
                }


                if(accepted){
                    listener.saveData(w);

                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Log insert failed")
                            .setMessage(error)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }

        });


        edTitle = view.findViewById(R.id.title_edit);
        edDate = view.findViewById(R.id.date_edit);
        edDescription = view.findViewById(R.id.description_edit);
        edDuration = view.findViewById(R.id.duration_edit);


        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (CreateDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement CreateDialogListener");
        }

    }

    public interface CreateDialogListener {
        void saveData(Workout w);
    }


}



