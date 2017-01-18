package com.agrawalsuneet.loaders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.agrawalsuneet.dotsloader.dialog.DotsLoaderDialog;
import com.agrawalsuneet.dotsloader.ui.ThreeDotsLoader;

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
        containerLL = (LinearLayout) findViewById(R.id.container);

        ThreeDotsLoader loader = new ThreeDotsLoader(MainActivity.this);
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
        mDialog = new DotsLoaderDialog.Builder(this)
                .setTextColor(R.color.white)
                .setBackground(R.color.blue_delfault)
                .setMessage("Loading...")
                .setTextSize(24)
                .setDotsDefaultColor(R.color.loader_defalut)
                .setDotsSelectedColor(R.color.loader_selected)
                .setAnimDuration(800)
                .setDotsDistance(28)
                .setDotsRadius(28)
                .setIsLoadingSingleDirection(true)
                .create();

        //mDialog.setCancelable(false);
        mDialog.show(getSupportFragmentManager(), "dialog");
    }
}
