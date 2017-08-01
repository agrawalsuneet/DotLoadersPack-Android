package com.agrawalsuneet.loaders

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.agrawalsuneet.dotsloader.ui.CircularDotsLoader
import com.agrawalsuneet.dotsloader.ui.LinearDotsLoader
import com.agrawalsuneet.loaders.dialog.DotsLoaderDialog

class MainActivity : AppCompatActivity() {

    private val mDialog: DotsLoaderDialog? = null
    private val containerLL: LinearLayout? = null

    private var colorSwitch = false

    lateinit var loader: CircularDotsLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val containerLL = findViewById(R.id.container) as LinearLayout

        var loader = LinearDotsLoader(this)
        loader.defaultColor = ContextCompat.getColor(this, R.color.loader_defalut)
        loader.selectedColor = ContextCompat.getColor(this, R.color.loader_selected)
        loader.isSingleDir = false
        //loader.firstShadowColor = ContextCompat.getColor(this, R.color.pink_selected)
        //loader.secondShadowColor = ContextCompat.getColor(this, R.color.purple_selected)
        loader.noOfDots = 5
        loader.selRadius = 60
        loader.expandOnSelect = false
        loader.radius = 40
        loader.dotsDistance = 20
        loader.animDur = 100
        containerLL.addView(loader)


        /*loader = CircularDotsLoader(this@MainActivity)
        loader.defaultColor = ContextCompat.getColor(this, R.color.blue_delfault)
        loader.selectedColor = ContextCompat.getColor(this, R.color.blue_selected)
        loader.bigCircleRadius = 116
        loader.radius = 40
        loader.animDur = 1000*/
        // loader.setSecondShadowColor(ContextCompat.getColor(this, R.color.pink_selected));
        //loader.setFirstShadowColor(ContextCompat.getColor(this, R.color.purple_selected));
        //loader.setShowRunningShadow(false);

        //containerLL.addView(loader)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_dialog -> {
                /*if (colorSwitch) {
                    loader.firstShadowColor = ContextCompat.getColor(this, R.color.pink_selected)
                    loader.secondShadowColor = ContextCompat.getColor(this, R.color.pink_default)
                } else {
                    loader.firstShadowColor = ContextCompat.getColor(this, R.color.purple_selected)
                    loader.firstShadowColor = ContextCompat.getColor(this, R.color.purple_default)
                }

                colorSwitch = !colorSwitch*/
                showAlertDialog();
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog() {
        val dotsDialog = DotsLoaderDialog.Builder(this)
                .setTextColor(R.color.white)
                .setMessage("Loading...")
                .setTextSize(24f)
                .setDotsDefaultColor(ContextCompat.getColor(this, R.color.loader_defalut))
                .setDotsSelectedColor(ContextCompat.getColor(this, R.color.loader_selected))
                .setAnimDuration(800)
                .setDotsDistance(28)
                .setDotsRadius(28)
                .setDotsSelectedRadius(40)
                .setExpandOnSelect(false)
                .setNoOfDots(5)
                .setIsLoadingSingleDirection(true)
                .create()

        //dotsDialog.setCancelable(false);
        dotsDialog.show(supportFragmentManager, "dotsDialog")
    }
}
