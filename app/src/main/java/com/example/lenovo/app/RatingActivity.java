package com.example.lenovo.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lenovo.app.DB.RatingUserDao;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.hyphenate.chat.EMClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

@SuppressWarnings("ResourceType")
public class RatingActivity extends Activity implements RatingBar.OnRatingBarChangeListener {
    private ImageButton ib_talk_back;
    private String photo;
    private ImageView iv_pic;
    private TextView id_ping;
    private RatingBar rabartwoBar;
    private Bitmap[] bitmaps;
    private int attitudeRating;
    private RadioButton rb_niming;
    private TextView tv_niming;
    private TextView btn_send;
    private RatingUserDao dbHelper;
    private EditText ed_word;
    private LinearLayout ll_see;
    private String see = "yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rating);
        dbHelper = new RatingUserDao(getApplication());
        Intent intent = getIntent();
        photo = intent.getStringExtra("photo");
        see = intent.getStringExtra("see");
        initListener();


        bitmaps = new Bitmap[3];
        bitmaps[0] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.emoji_normal)));
        bitmaps[1] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.emoji_normal)));
        bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_n)));
        getMap(rabartwoBar, bitmaps);
    }

    private void initListener() {
        iv_pic = findViewById(R.id.iv_pic);
        setView();
        id_ping = findViewById(R.id.id_ping);
        ll_see = findViewById(R.id.ll_see);
        ib_talk_back = findViewById(R.id.ib_talk_back);
        rb_niming = findViewById(R.id.rb_niming);
        tv_niming = findViewById(R.id.tv_niming);
        btn_send = findViewById(R.id.btn_send);
        ed_word = findViewById(R.id.ed_word);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = ed_word.getText().toString().trim();
                String name = "***";
                if (rb_niming.isSelected()) {
                    name  = "***";
                } else {
                   name = EMClient.getInstance().getCurrentUser();
                }
                dbHelper.addMes(trim, name);
                finish();

            }
        });
        rb_niming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_niming.isSelected()) {
                    rb_niming.setSelected(false);
                    rb_niming.setChecked(false);
                    tv_niming.setText("您的评价能帮助到其他小伙伴哟");
                } else {
                    rb_niming.setSelected(true);
                    rb_niming.setChecked(true);
                    tv_niming.setText("您的评价将会以匿名方式展出");
                }
            }
        });

        ib_talk_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rabartwoBar = ((RatingBar) findViewById(R.id.rbTwo));
        rabartwoBar.setOnRatingBarChangeListener(this);

            if (see.equalsIgnoreCase("no")) {
                ll_see.setVisibility(View.GONE);
            }

    }

    private void setView() {
        try {
            Picasso.with(RatingActivity.this)
                    .load(Constants.BASE_URL_IMAGE + photo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
                    .into(iv_pic);
        } catch (Exception e) {

        }

    }

    //如果想要改变星星大小，可以把传进去的bitmap文件放大。给个函数可以直接用
    private Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public void getMap(RatingBar ratingBar,Bitmap[] images) {
        int[] requiredIds = {android.R.id.background, android.R.id.secondaryProgress, android.R.id.progress};
        float[] roundedCorners = new float[]{5, 5, 5, 5, 5, 5, 5, 5};  //一个矩形
        Drawable[] pieces = new Drawable[3];
        for (int i = 0; i < 3; i++) {
            ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));
            BitmapShader bitmapShader = new BitmapShader(images[i], Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);  //平铺  填满星星
            sd.getPaint().setShader(bitmapShader);
            ClipDrawable cd = new ClipDrawable(sd, Gravity.LEFT,
                    ClipDrawable.HORIZONTAL);
            if (i == 0) {
                pieces[i] = sd;
            } else {
                pieces[i] = cd;
            }
        }
        LayerDrawable ld = new LayerDrawable(pieces);
        for (int i = 0; i < 3; i++) {
            ld.setId(i, requiredIds[i]);
        }
        ratingBar.setProgressDrawable(ld);
        LinearLayout.LayoutParams lpr= new LinearLayout.LayoutParams((images[0].getWidth())*5,images[0].getHeight());
        ratingBar.setLayoutParams(lpr);
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        // getNumStars
        // Returns the number of stars shown.
        final int numStars = ratingBar.getNumStars();
        if (rating == 1) {
            id_ping.setText("非常差");
        } else if (rating == 2) {
            id_ping.setText("差");
        } else if (rating == 3) {
            id_ping.setText("中等");
        } else if (rating == 4) {
            id_ping.setText("好");
        } else if (rating == 5) {
            id_ping.setText("非常好");
        }

        attitudeRating = (int) ratingBar.getRating();
        if (attitudeRating < 2) {
            bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_vb)));
            getMap(rabartwoBar, bitmaps);
        } else if (attitudeRating < 3) {
            bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_b)));
            getMap(rabartwoBar, bitmaps);
        } else if (attitudeRating < 4) {
            bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_n)));
            getMap(rabartwoBar, bitmaps);
        } else if (attitudeRating < 5) {
            bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_g)));
            getMap(rabartwoBar, bitmaps);
        } else {
            bitmaps[2] = big(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.em_vg)));
            getMap(rabartwoBar, bitmaps);
        }


      /*  if (indicatorRatingBar.getNumStars() != numStars) {
            indicatorRatingBar.setNumStars(numStars);
            smallRatingBar.setNumStars(numStars);
        }
        // getRating
        // Gets the current rating 评级(number of stars filled).
        if (indicatorRatingBar.getRating() != rating) {
            Log.d("sxp", "rating " + rating);
            indicatorRatingBar.setRating(rating);
            smallRatingBar.setRating(rating);
        }
        // getStepSize
        // Gets the step size of this rating bar.
        final float ratingBarStepSize = ratingBar.getStepSize();
        if (indicatorRatingBar.getStepSize() != ratingBarStepSize) {
            Log.d("sxp", "ratingBarStepSize " + ratingBarStepSize);
            indicatorRatingBar.setStepSize(ratingBarStepSize);
            smallRatingBar.setStepSize(ratingBarStepSize);
        }*/
    }
}
