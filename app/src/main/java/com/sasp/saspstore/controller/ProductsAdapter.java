package com.sasp.saspstore.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasp.saspstore.R;
import com.sasp.saspstore.model.Product;

import java.util.ArrayList;

public class ProductsAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Product> products;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = products.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_product, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.textview_product_price);
        final ImageView imageViewSelected = (ImageView)convertView.findViewById(R.id.imageview_selected);
        // TODO: The next line...!
        // imageView.setImageResource(product.getImageURL());
        nameTextView.setText(product.getName());
        priceTextView.setText(Integer.toString(product.getPrice()));
        if (product.isSelected()) imageViewSelected.setImageResource(R.drawable.star_enabled);
        else imageViewSelected.setImageResource(0);
        return convertView;
    }

}
