package com.agrawalsuneet.loaders.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.agrawalsuneet.dotsloader.ui.LinearDotsLoader
import com.agrawalsuneet.loaders.R

/**
 * Created by Suneet on 13/01/17.
 */

class DotsLoaderDialog : DialogFragment() {

    private var mView: View? = null
    private var mMessageTextView: TextView? = null
    private var mContainerLL: LinearLayout? = null
    private var mLoader: LinearDotsLoader? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mView = LayoutInflater.from(activity).inflate(R.layout.dialog_loader, null)

        val builder = AlertDialog.Builder(activity)
                .setView(mView)

        val dialog = builder.create()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initViews()
        return dialog
    }


    private fun initViews() {
        mContainerLL = mView!!.findViewById(R.id.dialog_container) as LinearLayout
        mMessageTextView = mView!!.findViewById(R.id.dialog_message_tv) as TextView
        mLoader = mView!!.findViewById(R.id.dialog_loader) as LinearDotsLoader

        mLoader?.radius = 30
        mLoader?.dotsDistance = 20
    }

}
