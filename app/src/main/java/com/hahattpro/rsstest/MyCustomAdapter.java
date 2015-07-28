package com.hahattpro.rsstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hahattpro.simplistic_rss.RssItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TT on 12/31/2014.
 */
public class MyCustomAdapter extends ArrayAdapter<RssItem> {
    private Context context;
    private int resource;
    private List<RssItem> objects;

    //constructor
    public MyCustomAdapter(Context context, int resource, List<RssItem> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    @Override //getView
    public View getView(int position, View convertView, ViewGroup parent) {
        View Item_view = convertView;
        if (Item_view==null)
        Item_view = new CustomViewGroup(getContext());

        //the data, which contain Title and Description
        final RssItem myData = objects.get(position);

        //bind TextView
        TextView Title = ((CustomViewGroup)Item_view).TitleContent;
        TextView Description = ((CustomViewGroup)Item_view).DescriptionContent;



        //Set text fo TextView
        Title.setText(myData.getTitle());
        Description.setText(myData.getDescription());

        return Item_view;//return

    }
}
