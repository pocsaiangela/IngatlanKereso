package com.example.ingatlankereso;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class RealEstatesActivity extends AppCompatActivity {
    private  static  final String LOG_TAG = LoginActivity.class.getName();
    private FirebaseUser user;
    private RecyclerView mRecyclerView;
    private ArrayList<Estate> mEstateList;
    private RealEstateAdapter mAdapter;

    private final int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mEstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_real_estates);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG, "Hitelesített profil!");
        }else{
            Log.d(LOG_TAG, "Nem hitelesített profil!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mEstateList = new ArrayList<>();

        mAdapter = new RealEstateAdapter(this, mEstateList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mEstate = mFirestore.collection("Estates");

        queryData();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void queryData() {
        //létező adatok törlése hogy ne legyen ismétlés
        mEstateList.clear();
        mEstate.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                            Estate estate = document.toObject(Estate.class);
                            mEstateList.add(estate);

                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w(LOG_TAG, "Error getting documents.", task.getException());
                    }
                });
    }

//private void initializeData() {
//        TypedArray imageResource;
//        String[] estateAddress;
//        String[] estatePrice;
//        String[] estateSize;
//        String[] estateRoomNum;
//        String[] estateDescription;
//        String[] sellerContact;
//}
//    hardcode-dal írt termékek/lakások a strings.xml-ben, nem Firestore

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.estate_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {

        if(menu.getItemId() == R.id.log_out_button) {
            Log.d(LOG_TAG, "Log out clicked!");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }else if(menu.getItemId() == R.id.setting_button) {
            Log.d(LOG_TAG, "Settings clicked!");
            return true;
        } else {
            return super.onOptionsItemSelected(menu);
        }
    }

    public void addEstate(View view) {
        //todo: open new activity to add estate
//        Intent intent = new Intent(this, AddEstateActivity.class);
//        startActivity(intent);
    }
}