package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.Chat;
import com.example.myapplication.Models.ChatRecord;
import com.example.myapplication.Models.Message;
import com.example.myapplication.Models.User;
import com.example.myapplication.RecyclerAdapters.*;
import com.example.myapplication.R;
import com.example.myapplication.Tags;
import com.example.myapplication.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentChat extends Fragment {

    private FirebaseFirestore db;
    private ChatRecord currentChatRecord;
    private Chat currentChat;
    private User currentLocalUser;
    private ArrayList<String> chatEmails;
    private ArrayList<Message> messages;
    private TextView textViewChatName;
    private RecyclerView recyclerViewChat;
    private RecyclerView.LayoutManager recyclerViewChatLayoutManager;
    private RecyclerView.Adapter recyclerViewChatAdapter;
    private EditText editText_Chat;
    private ImageButton imageButtonSelectPhoto;
    private ImageButton imageButtonSend;

    public FragmentChat(User currentLocalUser, ArrayList<String> chatEmails){
        this.currentLocalUser = currentLocalUser;
        this.chatEmails = chatEmails;
    };

    public FragmentChat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            db = FirebaseFirestore.getInstance();
            messages = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        textViewChatName = rootView.findViewById(R.id.textView_chatUserNames);
        editText_Chat = rootView.findViewById(R.id.editText_TextMessage);
        imageButtonSelectPhoto = rootView.findViewById(R.id.imageButton_SelectPhoto);
        imageButtonSend = rootView.findViewById(R.id.imageButton_Send);

        recyclerViewChat = rootView.findViewById(R.id.recyclerView_Chat);
        recyclerViewChatLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewChatAdapter = new MessagesAdapter(messages, currentLocalUser);
        recyclerViewChat.setLayoutManager(recyclerViewChatLayoutManager);
        recyclerViewChat.setAdapter(recyclerViewChatAdapter);

        imageButtonSelectPhoto.setOnClickListener(this::onButtonSelectPhotoClicked);
        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText_Chat.getText().toString().trim();
                if( !text.equals("")){
                    Message message = new Message(text, currentLocalUser);
//            Upload it to Firebase.....
                    uploadMessageToFirebase(message);
                    editText_Chat.setText("");
//            Hide Keyboard.......
                    try {
                        InputMethodManager inputMethodManager =
                                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager
                                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }else{
                    editText_Chat.setError("Can't be empty!");
                }
            }
        });
//        Fetch the current messages for this chat......
        fetchCurrentMessagesForThisChat(chatEmails);
//        Fetch all the users in this chat.....
//        fetchUsersInThisChat(chatEmails);

        return rootView;
    }

    private void fetchCurrentMessagesForThisChat(ArrayList<String> chatEmails) {
        String chatRecordID = Utils.generateUniqueID(chatEmails);

        DocumentReference chatRef = db.collection("users")
                .document(currentLocalUser.getEmail())
                .collection("chats")
                .document(chatRecordID);
        //fetch the chat record ID......
        chatRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        currentChatRecord = documentSnapshot.toObject(ChatRecord.class);
//                        Now, fetch all the messages from that chat room.....
                        fetchCurrentChat();
                    }
                });
    }

    private void fetchCurrentChat() {
        DocumentReference chatDocument = db.collection("chats")
                .document(currentChatRecord.getDocumentReference());

//        Get the chat document from chats.....
        chatDocument.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            currentChat = task.getResult().toObject(Chat.class);
//                        Set the chat name.....
                            textViewChatName.setText(currentChat.getChat_name());

                            fetchMessages(chatDocument);

                        }else{
                            Toast.makeText(getContext(), "Error loading chats", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchMessages(DocumentReference chatDocument) {
        CollectionReference messagesCollection = chatDocument.collection("messages");
        messagesCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error ==null){
                    messages.clear();
                    for(DocumentSnapshot documentSnapshot: value.getDocuments()){
                        messages.add(documentSnapshot.toObject(Message.class));
                    }
                    Collections.sort(messages, new Comparator<Message>() {
                        @Override
                        public int compare(Message t1, Message t2) {
                            return (int) ((t1.getTime()-t2.getTime()));
                        }
                    });
                    recyclerViewChatAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void uploadMessageToFirebase(Message message) {
        db.collection("chats")
                .document(currentChatRecord.getDocumentReference())
                .collection("messages")
                .add(message);
    }

    private void onButtonSelectPhotoClicked(View view) {
        Toast.makeText(view.getContext(), "To be implemented!", Toast.LENGTH_SHORT).show();
    }

}