package com.sasp.saspstore.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.sasp.saspstore.ImageSaver;
import com.sasp.saspstore.R;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.CartItem;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;

import java.io.File;
import java.util.ArrayList;

public class CartRowAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CartItem> list;
    private Context context;

    public CartRowAdapter(ArrayList<CartItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        list.removeIf(cartItem -> cartItem.getCount() < 0);
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_row, parent, false);
        }

        CartItem cartItem = list.get(position);
        ImageView imgProduct = view.findViewById(R.id.cart_imgProduct);
        TextView txtProductName = view.findViewById(R.id.cart_txtProductName);
        TextView txtPrice = view.findViewById(R.id.cart_txtPrice);
        TextView txtNumber = view.findViewById(R.id.cart_txtNumber);
        Button increaseButton = view.findViewById(R.id.cart_increaseButton);
        Button decreaseButton = view.findViewById(R.id.cart_decreaseButton);

        Product product = DataManager.shared().getProductWithId(cartItem.getProductId());
        Bitmap bitmap = new ImageSaver(DataManager.context).setFileName(product.getProductId() + ".png").setDirectoryName("images").load();
        imgProduct.setImageBitmap(bitmap);
//        File imgFile = new File(cartItem.getProductId() + ".png");
//        if (imgFile.exists()) {
//            Bitmap bitmap = new ImageSaver(DataManager.context).setFileName(cartItem.getProductId() + ".png").setDirectoryName("images").load();
//            imgProduct.setImageBitmap(bitmap);
//        }
        txtProductName.setText(cartItem.getName());
        txtPrice.setText("قیمت هر قلم: " + cartItem.getPrice() + " - قیمت کل: " + cartItem.getPrice() * cartItem.getCount());
        txtNumber.setText(cartItem.getCount() + " عدد");

        increaseButton.setOnClickListener(v -> {
            cartItem.setCount(cartItem.getCount() + 1);
            Account account = DataManager.shared().getLoggedInAccount();
            Cart cart = (account instanceof Customer) ? ((Customer) account).getCart() : DataManager.shared().getTemporaryCart();
            cart.getRawProducts().put(cartItem.getProductId(), cartItem.getCount());
            DataManager.shared().syncCartForUser();
            notifyDataSetChanged();
        });
        decreaseButton.setOnClickListener(v -> {
            if (cartItem.getCount() != 0) {
                cartItem.setCount(cartItem.getCount() - 1);
                Account account = DataManager.shared().getLoggedInAccount();
                Cart cart = (account instanceof Customer) ? ((Customer) account).getCart() : DataManager.shared().getTemporaryCart();
                cart.getRawProducts().put(cartItem.getProductId(), cartItem.getCount());
                DataManager.shared().syncCartForUser();
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
