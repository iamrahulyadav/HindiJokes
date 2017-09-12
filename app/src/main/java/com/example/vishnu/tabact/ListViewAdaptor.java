package com.example.vishnu.tabact;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Vishnu on 31-Aug-17.
 */
public class ListViewAdaptor extends ArrayAdapter<String> {

    Context context;
    String[] text_Array;
    int img_Array;
    String color_Array;

    public ListViewAdaptor(Context c,String[] list_text,int list_img,String list_color) {
        super(c, R.layout.single_list_item,R.id.textView_list,list_text);
        this.text_Array=list_text;
        this.img_Array=list_img;
        this.color_Array=list_color;
        this.context=c;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = convertView;
        ListViewHolder holder = null;
        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_list_item,parent,false);
            holder=new ListViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder= (ListViewHolder) row.getTag();
        }

        holder.text_holder.setText(text_Array[i]);
        holder.img_holder.setImageResource(img_Array);
        Drawable background=holder.img_holder.getBackground();
        GradientDrawable gradientDrawable=(GradientDrawable)background;
        gradientDrawable.setColor(Color.parseColor(color_Array));
        return row;
    }
}
class ListViewHolder {
    TextView text_holder;
    ImageButton img_holder;
    ListViewHolder(View v){
        text_holder= (TextView) v.findViewById(R.id.textView_list);
        img_holder= (ImageButton) v.findViewById(R.id.imageButton_list);
    }
}

