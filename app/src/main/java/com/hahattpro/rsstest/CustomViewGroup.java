package com.hahattpro.rsstest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by TT on 12/31/2014.
 */
public class CustomViewGroup extends LinearLayout {

    public TextView TitleContent;
    public TextView DescriptionContent;



    public CustomViewGroup(Context context) {
        super(context);

        //use LayoutInflater
        LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.list_item,this,true);//bind CustomViewGroup with List_item layout

        //bind
        TitleContent = (TextView) findViewById(R.id.Title_TextView);
        DescriptionContent = (TextView) findViewById(R.id.Description_TextView);

    }




}
