package com.gn.translateseas.Chat;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gn.translateseas.Chat.AddFriend.Amigo;
import com.gn.translateseas.Chat.AddFriend.VolleyRequestProfile;
import com.gn.translateseas.Chat.QR.BottomSheetQR;
import com.gn.translateseas.Chat.Recycler.RvAmigosAdapter;
import com.gn.translateseas.R;
import com.gn.translateseas.SQLite.Database;
import com.gn.translateseas.databinding.ActivityChatBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class ActivityChat extends AppCompatActivity {
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();

    }

    // Inicializamos los componentes
    private void initComponents(){
        binding.btnLectorQR.setImageTintList(null);
        binding.btnLectorQR.setOnClickListener(clickLectorQR);

        binding.btnQR.setOnClickListener(clickQR);

        RvAmigosAdapter amigosAdapter = new RvAmigosAdapter(getAmigoList(), this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        binding.rvFriends.setLayoutManager(l);
        binding.rvFriends.setAdapter(amigosAdapter);
    }

    // Consultamos a la base de datos todos los usuarios que hemos agregado
    private List<Amigo> getAmigoList(){
        List<Amigo> amigoList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new Database(this).getWritableDatabase();

        try {
            String select_friends = "SELECT * FROM friends;";

            Cursor cursor = sqLiteDatabase.rawQuery(select_friends, null);
            if (cursor.moveToFirst()){
                do {
                    amigoList.add(new Amigo(cursor.getInt(1), cursor.getString(3),cursor.getString(2)));
                }while (cursor.moveToNext());
            }
        }catch (java.lang.RuntimeException e){

        }
        return amigoList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_qr:
                BottomSheetQR bottomSheetQR = new BottomSheetQR(this);
                bottomSheetQR.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final View.OnClickListener clickQR = (view -> {
        BottomSheetQR bottomSheetQR = new BottomSheetQR(this);
        bottomSheetQR.show();
    });

    private final View.OnClickListener clickLectorQR = (view -> {
        onButtonLector(view);
    });


    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    VolleyRequestProfile volleyRequestProfile = new VolleyRequestProfile(result.getContents(), ActivityChat.this);
                    volleyRequestProfile.newRequest();
                }
            });

    // Launch
    public void onButtonLector(View view) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Coloque un código QR en el interior del rectángulo del visor para escanear");
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }

}