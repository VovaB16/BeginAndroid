package com.example.beginandroid;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beginandroid.category.CategoryCreateActivity;
import com.example.beginandroid.MainActivity;

public class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select = item.getItemId();
        if(select==R.id.menu_main_activity) {
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(select==R.id.menu_category_activity) {
            Intent intent = new Intent(BaseActivity.this, CategoryActivity.class);
            startActivity(intent);
            return true;
        }

        else if(select==R.id.menu_category_create_activity) {
            Intent intent = new Intent(BaseActivity.this, CategoryCreateActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
