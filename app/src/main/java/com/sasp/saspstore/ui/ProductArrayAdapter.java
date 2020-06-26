package com.sasp.saspstore.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.sasp.saspstore.R;
import com.sasp.saspstore.model.Product;

import java.util.ArrayList;

public class ProductArrayAdapter extends ArrayAdapter<Product> {
    private final ArrayList<Product> products;
    private final Context context;

    public ProductArrayAdapter(Context context, ArrayList<Product> products) {
        super(context, -1, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.product_list_item, parent, false);
        ImageView imageView = rowView.findViewById(R.id.product_list_item_image);
        TextView txtTitle = rowView.findViewById(R.id.product_list_item_title);
        TextView txtPrice = rowView.findViewById(R.id.product_list_item_price);
        RatingBar ratingBar = rowView.findViewById(R.id.product_list_item_score);

        Product product = products.get(position);
        Glide.with(context).load(product.getImageURL()).into(imageView);
        txtTitle.setText(product.getName());
        txtPrice.setText(product.getPrice());
        ratingBar.setRating((float) product.getAverageScore());
        return rowView;
    }
}
