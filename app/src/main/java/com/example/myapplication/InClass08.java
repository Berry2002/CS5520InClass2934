package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Fragments.FragmentChat;
import com.example.myapplication.Fragments.FragmentLoginPage;
import com.example.myapplication.Fragments.FragmentMain;
import com.example.myapplication.Fragments.FragmentNewChatSelectFriend;
import com.example.myapplication.Fragments.FragmentRegisterPage;
import com.example.myapplication.Interfaces.IconnectToActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.example.myapplication.Models.User;
import com.example.myapplication.Models.Chat;
import com.example.myapplication.Models.ChatRecord;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class InClass08 extends AppCompatActivity implements IconnectToActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private User currentLocalUser;
    private FirebaseFirestore db;

    private ArrayList<FirebaseUser> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        setTitle("Firebase Exercise");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentUser = mAuth.getCurrentUser();
//        this.allUsers.add(currentUser);
        populateScreen();
    }

    private void populateScreen() {
        // check for authenticated users
        if (this.currentUser != null) {
            // The user is authenticated, fetching the details of the current user from Firebase...
            db.collection("users")
                    .document(currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                currentLocalUser = task.getResult().toObject(User.class);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.containerMain, new FragmentMain(currentLocalUser),"mainFragment")
                                        .commit();
                            } else {
                                mAuth.signOut();
                                currentUser = null;
                                populateScreen();
                            }
                        }
                    });
        } else {
            // The user is not logged in, load the login Fragment....
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerMain, FragmentLoginPage.newInstance(), "loginFragment")
                    .commit();
        }
    }

    @Override
    public void populateMainFragment(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }

    @Override
    public void populateRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, FragmentRegisterPage.newInstance(), "registerFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void registerDone(FirebaseUser firebaseUser, User user) {
        this.currentUser = firebaseUser;
        updateFirestoreWithUserDetails(user);
    }

    private void updateFirestoreWithUserDetails(User user) {
        db.collection("users")
                .document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // on success populate home screen
                        populateScreen();
                    }
                });
    }

    @Override
    public void logoutPressed() {
        mAuth.signOut();
        currentUser = null;
        populateScreen();
    }

    @Override
    public void newMessageButtonPressedFromMainFragment(ArrayList<User> users) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain,
                        new FragmentNewChatSelectFriend(users, currentLocalUser),
                        "selectFriendFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFriendSelectedFromSelectFriendFragment(User selectedFriend) {
//        Using set for future scalability for chat among more than two persons...
        ArrayList<String> chatEmails = new ArrayList<>();
        final ChatRecord[] theRecord = new ChatRecord[1];
        chatEmails.add(currentLocalUser.getEmail());
        chatEmails.add(selectedFriend.getEmail());

//        Generate a unique value for the list of users in this chat...
        String uIDforChat = Utils.generateUniqueID(chatEmails);

//        Fetch the collection of chat records from users tree for current user...
        DocumentReference chatDocRefInChatsTree = db.collection("users")
                .document(currentLocalUser.getEmail())
                .collection("chats")
                .document(uIDforChat);

        chatDocRefInChatsTree
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getData() != null){
                            // There is chat record there, populate the chat fragment .....
                            populateChatFragment(chatEmails);
                        }else{
                            // We need to create a new chat record ....
                            // First, we need to fetch the users in this chat, and generate the Chat Name....
                            fetchUsersInTheSelectedChat(chatEmails, uIDforChat);
                        }
                    }
                });
    }

    @Override
    public void onChatSelectedFromRecentChats(ChatRecord record) {
        ArrayList<String> chatEmails = new ArrayList<>();
        chatEmails = record.getUser_emails();
        populateChatFragment(chatEmails);
    }

    private void fetchUsersInTheSelectedChat(ArrayList<String> chatEmails, String uIDforChat) {
        ArrayList<User> chatUsers = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                if(chatEmails.contains(documentSnapshot.get("email"))){
                                    chatUsers.add(documentSnapshot.toObject(User.class));
                                }
                            }
                            //        setting the name of the chat...
                            String chatName = Utils.generateChatName(chatUsers);
                            //        Then, create a chat record in chats tree...
                            createRecordInFirebaseChatsCollection(chatName,chatEmails, uIDforChat);
                        } else{
                            Toast.makeText(getApplicationContext(),
                                    "Failed to retrieve users information", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void createRecordInFirebaseChatsCollection(String chatName, ArrayList<String> chatEmails, String uIDforChat) {
        Chat newChat = new Chat(chatEmails);
        newChat.setChat_name(chatName);
        db.collection("chats")
                .add(newChat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ChatRecord newChatRecord = new ChatRecord(chatName, documentReference.getId(), chatEmails);
                        //          Now, we need to add the this document ID to users tree for all users batch update.....
                        updateChatRecordsInFirebaseUsersCollection(chatEmails, newChatRecord, uIDforChat);
                    }
                });
    }

    private void updateChatRecordsInFirebaseUsersCollection(ArrayList<String> chatEmails, ChatRecord newChatRecord, String uIDforChat) {
        WriteBatch batch = db.batch();
        for(String email: chatEmails){
            batch.set(db.collection("users")
                            .document(email)
                            .collection("chats")
                            .document(uIDforChat),
                    newChatRecord);
        }
        batch.commit()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            populateChatFragment(chatEmails);
                        }else{
                            Toast.makeText(InClass08.this,
                                    "An error occured! Try again!", Toast.LENGTH_SHORT).show();
                            populateScreen();
                        }
                    }
                });
    }
    //
    private void populateChatFragment(ArrayList<String> chatEmails) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, new FragmentChat(currentLocalUser,
                        chatEmails),"newChatFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}