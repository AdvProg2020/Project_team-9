package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.ui.LargeImageViewActivity;
import com.sasp.saspstore.ui.VideoActivity;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EachProductActivity extends AppCompatActivity {

    ImageView eachProductImageView;
    TextView eachProductTitle;
    TextView eachProductBrand;
    TextView eachProductPriceAndDiscountPercent;
    TextView eachProductNumberAvailable;
    TextView eachProductDescription;
    TextView eachProductDateCreated;
    TextView eachProductScore;
    EditText eachProductCompareProductID;
    Button makeAuctionButton;
    Button getFileButton;

    Product currentProduct;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_product);

        eachProductImageView = findViewById(R.id.eachProductImageView);
        eachProductTitle = findViewById(R.id.eachProductTitle);
        eachProductBrand = (TextView) findViewById(R.id.eachProductBrand);
        eachProductPriceAndDiscountPercent = (TextView) findViewById(R.id.eachProductPriceAndDiscountPercent);
        eachProductNumberAvailable = (TextView) findViewById(R.id.eachProductNumberAvailable);
        eachProductDescription = (TextView) findViewById(R.id.eachProductDescription);
        eachProductDateCreated = (TextView) findViewById(R.id.eachProductDateCreated);
        eachProductScore = (TextView) findViewById(R.id.eachProductScore);
        eachProductCompareProductID = findViewById(R.id.eachProductCompareProductID);
        makeAuctionButton = findViewById(R.id.eachProduct_makeAuctionButton);
        getFileButton = findViewById(R.id.get_file);

        Intent intent = getIntent();
        String productID = intent.getStringExtra("productID");
        if (productID == null || productID.equals("")) return;
        currentProduct = DataManager.shared().getProductWithId(productID);

//        Bitmap bitmap = new ImageSaver(this).setFileName(productID + ".png").setDirectoryName("images").load();
        byte[] decodedString = Base64.decode(currentProduct.getImageBase64(), Base64.URL_SAFE);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        eachProductImageView.setImageBitmap(bitmap);
        //TODO: color overlay. Is this working?
        if (currentProduct.getNumberAvailable() == 0)
            findViewById(R.id.eachProductImageViewOverlay).setBackground(getDrawable(R.color.grey_overlay));
        else if (currentProduct.getDiscountPercent() != 0)
            findViewById(R.id.eachProductImageViewOverlay).setBackground(getDrawable(R.color.red_overlay));
        else
            findViewById(R.id.eachProductImageViewOverlay).setBackground(null);
        eachProductImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LargeImageViewActivity.class);
                intent.putExtra("bitmap", productID + ".png");
                intent.putExtra("mode", "bitmap");
                startActivity(intent);
            }
        });
        eachProductTitle.setText(currentProduct.getName() + (currentProduct.getNumberAvailable() <= 0 ? " (تمام شده)" : ""));
        eachProductBrand.setText("برند: " + currentProduct.getBrand());
        if (currentProduct.getDiscountPercent() != 0) {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان، با " +
                    currentProduct.getDiscountPercent() + " درصد تخفیف، " +
                    (currentProduct.getPrice() * (100 - currentProduct.getDiscountPercent()) / 100) + " تومان");
        } else {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان");
        }
        eachProductNumberAvailable.setText(Integer.toString(currentProduct.getNumberAvailable()) + " عدد موجود است");
        eachProductDescription.setText(currentProduct.getDescription());
        eachProductDateCreated.setText("اضافه شده در " + currentProduct.getDateCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        starRating((int) currentProduct.getAverageScore());

        findViewById(R.id.show_video_button).setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), VideoActivity.class);
            startActivity(intent1);
        });
    }

    public void getFileTapped() {

    }

    private void starRating(int score) {
        switch (score) {
            case 1:
                eachProductScore.setText("★☆☆☆☆");
                break;
            case 2:
                eachProductScore.setText("★★☆☆☆");
                break;
            case 3:
                eachProductScore.setText("★★★☆☆");
                break;
            case 4:
                eachProductScore.setText("★★★★☆");
                break;
            case 5:
                eachProductScore.setText("★★★★★");
                break;
            default:
                eachProductScore.setText("☆☆☆☆☆");
                break;
        }
    }

    public void showCommentsTapped(View view) {
        Intent intent = new Intent(this, CommentsListActivity.class);
        intent.putExtra("productID", currentProduct.getProductId());
        startActivity(intent);
    }

    public void submitScoreTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Customer) {
            Customer customer = (Customer) account;
            if (customer.hasPurchasedProduct(currentProduct)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("لطفا امتیاز مورد نظر خود را از صفر تا پنج وارد نمایید");
                // TODO: Wrong format or out of range number??
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("ثبت", (dialog, whichButton) -> {
                    currentProduct.addScore(Integer.parseInt(input.getText().toString()), customer);
                    DataManager.shared().syncProducts();
                    starRating((int) currentProduct.getAverageScore());
                });
                alert.setNegativeButton("بازگشت", null);
                alert.show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("خطا")
                        .setMessage("برای ثبت امتیاز ابتدا باید کالا را خریداری کرده باشید")
                        .setNeutralButton("بازگشت", null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("خطا")
                    .setMessage("ابتدا توسط حساب کاربری خریدار وارد شوید")
                    .setNeutralButton("بازگشت", null)
                    .show();
        }
    }

    public void submitCommentTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Customer) {
            Customer customer = (Customer) account;
            if (customer.hasPurchasedProduct(currentProduct)) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
                dialogBuilder.setView(dialogView);
                final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
                final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);
                firstTextView.setText("لطفا نظر خود را وارد نمایید");
                secondTextView.setText("");
                final EditText firstEditText = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
                final EditText secondEditText = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
                dialogBuilder.setTitle("ثبت نظر").setMessage("").setPositiveButton("ثبت نظر", (dialog, whichButton) -> {
                    String title = firstEditText.getText().toString();
                    String text = secondEditText.getText().toString();
                    Comment comment = new Comment(customer, currentProduct, title, text);
                    currentProduct.addComment(comment);
                    DataManager.shared().syncProducts();
                }).setNeutralButton("بازگشت", null);
                AlertDialog b = dialogBuilder.create();
                b.show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("خطا")
                        .setMessage("برای ثبت نظر ابتدا باید کالا را خریداری کرده باشید")
                        .setNeutralButton("بازگشت", null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("خطا")
                    .setMessage("ابتدا توسط حساب کاربری خریدار وارد شوید")
                    .setNeutralButton("بازگشت", null)
                    .show();
        }
    }

    public void addToCartTapped(View view) {
        if (currentProduct.getSellers().size() < 2) {
            addToCart();
            return;
        }
        final EditText editText = new EditText(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("انتخاب فروشنده");
        StringBuilder stringBuilder = new StringBuilder("از میان فروشنده‌های زیر، فروشنده مطلوب را انتخاب کنید: (در صورت اشتباه بودن نام فروشنده، به تصادف انتخاب می‌شود) ");
        for (Seller seller : currentProduct.getSellers()) {
            stringBuilder.append("\n").append(seller.getUsername());
        }
        alertDialogBuilder.setMessage(stringBuilder.toString());
        alertDialogBuilder.setView(editText);
        alertDialogBuilder.setPositiveButton("افزودن به سبد خرید", (dialogInterface, i) -> {
            Account account = DataManager.shared().getAccountWithGivenUsername(editText.getText().toString());
            if (account instanceof Seller) currentProduct.setCurrentSeller((Seller) account);
            addToCart();
        });
        alertDialogBuilder.setNeutralButton("بازگشت", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void addToCart() {
        Account account = DataManager.shared().getLoggedInAccount();
        Cart cart;
        if (account instanceof Customer) cart = ((Customer) account).getCart();
        else cart = DataManager.shared().getTemporaryCart();
        cart.addProduct(currentProduct);
        if (!(account instanceof Customer)) {
            ContentValues cv = new ContentValues();
            cv.put("action", "setTemporaryCart");
            cv.put("cart", new Gson().toJson(DataManager.shared().getTemporaryCart()));
//            Gonnect.sendRequest("http://10.0.2.2:8111/", cv, (b, s) -> {
//            });
            Gonnect.getData(DataManager.IP_SERVER + "req?action=setTemporaryCart&cart="
                    + DataManager.encode(new Gson().toJson(DataManager.shared().getTemporaryCart())), (b, s) -> {
            });
            DataManager.shared().syncCartForUser();
        }
        Toast.makeText(this, "کالا با موفقیت به سبد خرید افزوده شد", Toast.LENGTH_LONG).show();
    }


    public void compareTapped(View view) {
        if (eachProductCompareProductID.getText().toString().equals("")) return;
        Product product = DataManager.shared().getProductWithName(eachProductCompareProductID.getText().toString());
        if (product == null) return;
        Category category = product.getCategory();
        if (!category.getId().equals(currentProduct.getCategory().getId())) {
            new AlertDialog.Builder(EachProductActivity.this).setTitle("خطا")
                    .setMessage("تنها دو محصول از یک گروه با یک‌دیگر قابل مقایسه‌اند")
                    .setNeutralButton("بازگشت", null).show();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("نام: ").append("\n");
        stringBuilder.append(currentProduct.getName()).append("\n");
        stringBuilder.append(product.getName()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("برند: ").append("\n");
        stringBuilder.append(currentProduct.getBrand()).append("\n");
        stringBuilder.append(product.getBrand()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("قیمت: ").append("\n");
        stringBuilder.append(currentProduct.getPrice()).append("\n");
        stringBuilder.append(product.getPrice()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("درصد تخفیف").append("\n");
        stringBuilder.append(currentProduct.getDiscountPercent()).append("\n");
        stringBuilder.append(product.getDiscountPercent()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("تعداد موجود: ").append("\n");
        stringBuilder.append(currentProduct.getNumberAvailable()).append("\n");
        stringBuilder.append(product.getNumberAvailable()).append("\n");
        stringBuilder.append("\n");
        for (String feature : category.getUniqueFeatures()) {
            stringBuilder.append(feature).append(": ").append("\n");
            stringBuilder.append(currentProduct.getFeatures().get(feature)).append("\n");
            stringBuilder.append(product.getFeatures().get(feature)).append("\n").append("\n");
        }
        stringBuilder.append("توضیحات: ").append("\n");
        stringBuilder.append(currentProduct.getDescription()).append("\n");
        stringBuilder.append(product.getDescription());
        new AlertDialog.Builder(EachProductActivity.this).setTitle("مقایسه محصول")
                .setMessage(stringBuilder.toString())
                .setNeutralButton("بازگشت", null).show();
    }

    public void seeFeaturesTapped(View view) {
        Category category = currentProduct.getCategory();
        if (category.getUniqueFeatures().size() != 2 || currentProduct.getFeatures().keySet().size() != 2) {
            Toast.makeText(this, "ویژگی‌های دسته‌بندی موجود نیست", Toast.LENGTH_LONG).show();
            return;
        }
        new AlertDialog.Builder(EachProductActivity.this).setTitle("ویژگی‌های دسته‌بندی")
                .setMessage(category.getUniqueFeatures().get(0) + ":\n" + currentProduct.getFeatures().get(category.getUniqueFeatures().get(0))
                        + "\n\n" + category.getUniqueFeatures().get(1) + ":\n" + currentProduct.getFeatures().get(category.getUniqueFeatures().get(1)))
                .setNeutralButton("بازگشت", null).show();
    }

    public void seeSimilarsTapped(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        StringBuilder productIDs = new StringBuilder();
        ArrayList<Product> allProducts = DataManager.shared().getAllProducts();
        for (int i = 0, allProductsSize = allProducts.size(); i < allProductsSize; i++) {
            Product product = allProducts.get(i);
            if (getLevenshteinDistance(product.getName(), currentProduct.getName()) < 5 ||
                    getLevenshteinDistance(product.getBrand(), currentProduct.getBrand()) < 5) {
                productIDs.append(product.getProductId());
                if (i != allProductsSize - 1) productIDs.append(";;;;");
            }
        }
        intent.putExtra("openOrSelect", "open");
        intent.putExtra("productIDs", productIDs.toString());
        intent.putExtra("categoryID", currentProduct.getCategory().getId());
        startActivity(intent);
    }

    // Code snippet from the Internet
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int[] p = new int[n + 1]; //'previous' cost array, horizontally
        int[] d = new int[n + 1]; // cost array, horizontally
        int[] _d; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }

    // TODO: Not tested

    public void makeAuctionTapped(View view) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(view1 -> {
            DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getHour(),
                    timePicker.getMinute());

            TimeZone tz = calendar.getTimeZone();
            ZoneId zid = tz.toZoneId();
            LocalDateTime endDate = LocalDateTime.ofInstant(calendar.toInstant(), zid);
            Auction auction = new Auction(DataManager.getNewId(), currentProduct);
            auction.setEndTime(endDate);
            DataManager.shared().addAuction(auction);
            DataManager.shared().syncAuctions();
            Toast.makeText(EachProductActivity.this, "کالا با موفقیت به مزایده گذاشته شد", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}