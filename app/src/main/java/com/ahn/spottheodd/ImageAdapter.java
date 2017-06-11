package com.ahn.spottheodd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by Ali on 3/30/2016.
 */
public class ImageAdapter extends ArrayAdapter {
    Context context;
    int uniquePos, size;

    /*public void size(int gridSize){
        size=gridSize;
    }*/


    public ImageAdapter(Context context)
    {
        super(context, 0);
        this.context=context;

    }

    public int getCount()
    {
        return 64;
    }

    public void setUniquePos(int pos){
        uniquePos = pos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;

        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_row, parent, false);


            ImageView imageViewIte = (ImageView) row.findViewById(R.id.imageView);

            if(position==uniquePos)
            {
                imageViewIte.setImageResource(R.drawable.distinct_square);
            }
            else
            {
                imageViewIte.setImageResource(R.drawable.uniform_square);
            }
        }
        else{
            row=convertView;
        }

        return row;

    }
}