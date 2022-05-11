package com.example.MireaProject.stories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.MireaProject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoriesFragment extends Fragment {
    DBHelper dbHelper;
    FloatingActionButton buttonAdd;
    Button buttonClear;
    static ArrayList<Story> stories = new ArrayList<Story>();
    static StoryAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoriesFragment newInstance(String param1, String param2) {
        StoriesFragment fragment = new StoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflateView = inflater.inflate(R.layout.fragment_stories, container, false);

        setInitialData();
        RecyclerView recyclerView = inflateView.findViewById(R.id.list);
        // создаем адаптер
        adapter = new StoryAdapter(getActivity(), stories);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        buttonAdd = inflateView.findViewById(R.id.floatingActionButton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "custom");
            }
        });

        buttonClear = inflateView.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                stories.clear();
                adapter.notifyDataSetChanged();
            }
        });

        return inflateView;
    }

    private void setInitialData() {
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        Log.d("myLog","Now position:"+cursor.getPosition());
        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int storyIndex = cursor.getColumnIndex(DBHelper.KEY_STORY);
            do {
                Log.d("myLog", "ID = " + cursor.getInt(idIndex) +
                        ", story = " + cursor.getString(storyIndex));
                stories.add(new Story(cursor.getInt(idIndex), cursor.getString(storyIndex)));
               // onResetRecyclerView();
            } while (cursor.moveToNext());

        }
        else
            Log.d("myLog", "o rows");

    }
    public static void onResetRecyclerView(){
        adapter.notifyItemInserted(stories.size()-1);
    }


     public static class CustomDialogFragment extends DialogFragment {
        DBHelper dbHelper;

        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            dbHelper = new DBHelper(getActivity());

            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText edittext1 = new EditText(getActivity());
            return builder
                    .setTitle("Добавьте историю")
                    .setIcon(android.R.drawable.btn_default)
                    .setView(edittext1)
                    .setNeutralButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = edittext1.getText().toString();
                            Log.d("myLog", "Имя: " + name);
                            contentValues.put(DBHelper.KEY_STORY, name);
                            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                            Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);


                            cursor.moveToLast();
                            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                            int storyIndex = cursor.getColumnIndex(DBHelper.KEY_STORY);
                            stories.add(new Story(cursor.getInt(idIndex), cursor.getString(storyIndex)));
                            cursor.close();

                        StoriesFragment.onResetRecyclerView();
                        }
                    })
                    .create();
        }
    }
}