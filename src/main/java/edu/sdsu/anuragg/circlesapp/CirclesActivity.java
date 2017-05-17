package edu.sdsu.anuragg.circlesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CirclesActivity extends AppCompatActivity{
    public static boolean isDrawMode = true, isDeleteMode = false, isMoveMode = false;
    public static String selectedCircleColor = "Black";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.circle_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.draw_circle:
                isDrawMode = true;
                isDeleteMode = false;
                isMoveMode = false;
                item.setChecked(true);
                return true;
            case R.id.delete_circle:
                isDrawMode = false;
                isDeleteMode = true;
                isMoveMode = false;
                item.setChecked(true);
                return true;
            case R.id.move_circle:
                isDrawMode = false;
                isDeleteMode = false;
                isMoveMode = true;
                item.setChecked(true);
                return true;
            case R.id.blackColor:
                item.setChecked(true);
                if(isDrawMode) {
                    selectedCircleColor = "Black";
                }
                else{
                    Toast.makeText(this,"Please Select DRAW Mode", Toast.LENGTH_SHORT).show();

                }
                return true;
            case R.id.redColor:
                item.setChecked(true);
                if(isDrawMode) {
                    selectedCircleColor = "Red";
                }
                else{
                    selectedCircleColor = "Red";
                    Toast.makeText(this,"Please Select DRAW Mode", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.greenColor:
                item.setChecked(true);
                if(isDrawMode) {
                    selectedCircleColor = "Green";
                }
                else{
                    selectedCircleColor = "Green";
                    Toast.makeText(this,"Please Select DRAW Mode", Toast.LENGTH_SHORT).show();

                }
                return true;
            case R.id.blueColor:
                item.setChecked(true);
                if(isDrawMode) {
                    selectedCircleColor = "Blue";
                }
                else{
                    selectedCircleColor = "Blue";
                    Toast.makeText(this,"Please Select DRAW Mode", Toast.LENGTH_SHORT).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


