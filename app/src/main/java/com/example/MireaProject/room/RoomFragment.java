package com.example.MireaProject.room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.MireaProject.MainActivity;
import com.example.MireaProject.R;
import com.example.MireaProject.stories.StoriesFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Native;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {
    private static UserListAdapter userListAdapter;
    static RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
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
        View view =  inflater.inflate(R.layout.fragment_room, container, false);


        Button addNewUserButton = view.findViewById(R.id.addUserButton);
        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomFragment.AddNewUser dialog = new RoomFragment.AddNewUser();
                dialog.show(getActivity().getSupportFragmentManager(), "add");
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView();
        loadUserList();
        return view;
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        userListAdapter = new UserListAdapter(getActivity());
        recyclerView.setAdapter(userListAdapter);
    }
    private void loadUserList(){
        AppDatabase db = AppDatabase.getObInstance(getActivity().getApplicationContext());
        List<User> userList = db.userDao().getAllUsers();
        userListAdapter.setUserList(userList);

    }

    public static class AddNewUser extends DialogFragment{


        @NotNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText edittext1 = new EditText(getActivity());
            return builder
                    .setTitle("Добавьте историю")
                    .setIcon(android.R.drawable.btn_default)
                    .setView(edittext1)
                    .setNeutralButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveNewUser(edittext1.getText().toString());
                        }
                    })
                    .create();

        }
        private void saveNewUser(String name){
            AppDatabase db = AppDatabase.getObInstance(getActivity().getApplicationContext());

            User user = new User();
            user.Name = name;
            db.userDao().insertUser(user);

            AppDatabase database = AppDatabase.getObInstance(getActivity().getApplicationContext());
            List<User> userList = db.userDao().getAllUsers();
            userListAdapter.setUserList(userList);

        }
        private void deleteByUserId(int id){
            AppDatabase db = AppDatabase.getObInstance(getActivity().getApplicationContext());
            db.userDao().deleteByUserId(id);
        }

    }
}