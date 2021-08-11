package sg.edu.rp.c346.id20004713.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class modifier extends AppCompatActivity {
    Button btnUpdate, btnDelete, btnCancel;
    EditText etID, etTitle, etSinger, etYear;
    RatingBar ratingBar;

    DBHelper db = new DBHelper(modifier.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        ratingBar = findViewById(R.id.ratingBar);

        Intent i = getIntent();
        Song data = (Song) i.getSerializableExtra("data");

        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(("" + data.getYear()));
        etID.setText(("" + data.get_id()));
        ratingBar.setRating(data.getStars());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(modifier.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes");
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton(Html.fromHtml("<font color='#ffffff'>Discard</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                myBuilder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>Do not Discard</font>"), null);
                AlertDialog dialog = myBuilder.create();
                dialog.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(modifier.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to Delete the item: " + data.getTitle());
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton(Html.fromHtml("<font color='#ffffff'>Delete</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteNote(data.get_id());
                        finish();
                    }
                });

                myBuilder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>Cancel</font>"), null);
                AlertDialog dialog = myBuilder.create();
                dialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                int star = (int) ratingBar.getRating();

                Song updated = new Song(title, singer, year, star);
                updated.set_id(data.get_id());

                int inserted_id = db.updateSong(updated);

                if (inserted_id != -1){
                    Toast.makeText(modifier.this, "Update successful",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(modifier.this, "Update FAILED",
                            Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}