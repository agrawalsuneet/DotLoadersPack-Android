package com.agrawalsuneet.loaders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.agrawalsuneet.dotsloader.ui.CircularDotsLoader;
import com.agrawalsuneet.loaders.dialog.DotsLoaderDialog;
import com.agrawalsuneet.dotsloader.ui.LinearDotsLoader;

public class MainActivity extends AppCompatActivity {

    private DotsLoaderDialog mDialog;
    private LinearLayout containerLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initView();
    }

    private void initView() {
        LinearLayout containerLL = (LinearLayout) findViewById(R.id.container);

        /*LinearDotsLoader loader = new LinearDotsLoader(MainActivity.this);
        loader.setDefaultColor(R.color.loader_defalut);
        loader.setSelectedColor(R.color.loader_selected);
        loader.setIsSingleDir(true);
        loader.setNoOfDots(5);
        loader.setSelRadius(40);
        loader.setExpandOnSelect(true);
        loader.setRadius(30);
        loader.setDotsDist(20);
        loader.setAnimDur(500);
        containerLL.addView(loader);*/


        CircularDotsLoader loader = new CircularDotsLoader(MainActivity.this);
        loader.setDefaultColor(R.color.blue_delfault);
        loader.setSelectedColor(R.color.blue_selected);
        loader.setBigCircleRadius(80);
        loader.setRadius(24);
        loader.setAnimDur(300);

        containerLL.addView(loader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_dialog:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog() {
        DotsLoaderDialog dotsDialog = new DotsLoaderDialog.Builder(this)
                .setTextColor(R.color.white)
                .setMessage("Loading...")
                .setTextSize(24)
                .setDotsDefaultColor(R.color.loader_defalut)
                .setDotsSelectedColor(R.color.loader_selected)
                .setAnimDuration(800)
                .setDotsDistance(28)
                .setDotsRadius(28)
                .setDotsSelectedRadius(40)
                .setExpandOnSelect(false)
                .setNoOfDots(5)
                .setIsLoadingSingleDirection(true)
                .create();

        //dotsDialog.setCancelable(false);
        dotsDialog.show(getSupportFragmentManager(), "dotsDialog");
    }
}
