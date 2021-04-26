package com.sample.fit13;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditDialog extends AppCompatDialogFragment {

    private EditText edTitle, edDate, edDuration, edDescription;
    private EditDialog.EditDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view).setTitle("Create New Workout").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                Workout w = new Workout();
                String title = edTitle.getText().toString();
                String date = edDate.getText().toString();
                String duration = edDuration.getText().toString();
                String description = edDescription.getText().toString();

                w.setTitle(title);
                w.setDate(date);
                w.setDuration(duration);
                w.setDescription(description);


                listener.editData(w);
                    /*if(WorkoutHandler.checkDate(date)){
                        w.setDate(date);
                        if(WorkoutHandler.checkDuration(duration)){
                            w.setDuration(duration);
                            if(WorkoutHandler.checkDescription(description)){
                                w.setDescription(description);


                            } else {
                                edDescription.setText("Incorrect Title Format");
                                edDescription.setTextColor(Color.parseColor("#eb5555"));
                            }
                        } else {
                            edDuration.setText("Incorrect Title Format");
                            edDuration.setTextColor(Color.parseColor("#eb5555"));
                        }
                    } else {
                        edDate.setText("Incorrect Title Format");
                        edDate.setTextColor(Color.parseColor("#eb5555"));
                    }*/




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
            listener = (EditDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement EditDialogListener");
        }

    }

    public interface EditDialogListener{
        void editData(Workout w);
    }

}

