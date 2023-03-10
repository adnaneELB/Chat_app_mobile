package com.example.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapters.Chat_Recycle_View;
import com.example.application_chat.Chat;
import com.example.application_chat.MemoryData;
import com.example.models.Contactos;
import com.example.application_chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contactos_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contactos_Fragment extends Fragment {
    FirebaseAuth fireAuth;
    FirebaseUser firebaseUser;
    View view;
    RecyclerView recyclerView;
    ArrayList<Contactos> arrayList=new ArrayList<>();
    Chat_Recycle_View adapter;

   private int unseen=0;
    private String last="";
    private  String chatKey="";
    private boolean dataSet=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contactos_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contactos_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Contactos_Fragment newInstance(String param1, String param2) {
        Contactos_Fragment fragment = new Contactos_Fragment();
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
        view= inflater.inflate(R.layout.fragment_contactos_, container, false);
        recyclerView =view.findViewById(R.id.chat_rcy);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef1 = database.getReference("users");
        fireAuth = FirebaseAuth.getInstance();
        firebaseUser = fireAuth.getCurrentUser();

        adapter= new Chat_Recycle_View(container.getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                last="";
                unseen=0;
                chatKey="";
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        String getID=dataSnapshot.getKey();
                        String userID=firebaseUser.getUid();
                    try {
                        MemoryData.saveData(userID,view.getContext());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    dataSet=false;

                    if (!getID.equals(userID)){

                        String getname=dataSnapshot.child("name").getValue(String.class);



                        userRef1.getParent().child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCount=(int)snapshot.getChildrenCount();
                                if (getChatCount>0){
                                    for (DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                                        final String getKey=dataSnapshot1.getKey();
                                        chatKey=getKey;

                                       if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2")&&dataSnapshot1.hasChild("messages")){
                                           final String getUserOne=dataSnapshot1.child("user_1").getValue(String.class);
                                           final String getUserTwo=dataSnapshot1.child("user_2").getValue(String.class);

                                           if ((getUserOne.equals(getID)&&getUserTwo.equals(userID)||getUserOne.equals(userID)&&getUserTwo.equals(getID))){
                                               for (DataSnapshot chatSnap :dataSnapshot1.child("messages").getChildren()){
                                                   try {
                                                       final long getMsgKey= Long.parseLong(chatSnap.getKey());
                                                       final long getlastMesgs= Long.parseLong(MemoryData.getlastMsg(getKey,view.getContext()));

                                                       last=chatSnap.child("msg").getValue(String.class);

                                                       if (getMsgKey>getlastMesgs){
                                                           unseen++;
                                                       }
                                                   } catch (IOException e) {
                                                       throw new RuntimeException(e);
                                                   }
                                               }
                                           }
                                       }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                            if (!dataSet){
                                dataSet=true;
                                arrayList.add(new Contactos(getname,R.drawable.image1,unseen,last,chatKey,getID));
                                //Toast.makeText(view.getContext(), chatKey, Toast.LENGTH_SHORT).show();
                                adapter.updateData(arrayList);
                                LinearLayoutManager llm=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                recyclerView.setLayoutManager(llm);
                            }


                            }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return view;

    }
}