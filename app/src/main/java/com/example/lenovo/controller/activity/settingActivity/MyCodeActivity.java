package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.lenovo.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class MyCodeActivity extends Activity {

    private ImageView iv_code;
    private ImageButton ib_code_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_my_code);

        Intent intent = getIntent();
        String context = intent.getStringExtra("context");

         iv_code = findViewById(R.id.iv_code1);
        ib_code_back = findViewById(R.id.ib_code_back);
        generate("我是浙师大的"+ context);
        clickListener();
    }

    private void clickListener() {
        ib_code_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    public void generate(String Content) {
        Bitmap qrBitmap = generateBitmap(Content,400, 400);
        /*加logo*/
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.atguigu_logo_1);
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        iv_code.setImageBitmap(bitmap);
    }


    /*这三个参数分别表示生成二维码的文本内容（你要把哪一个文本用二维码图片表示出来），第二个和第三个参数分别表示生成的二维码图片的宽和高。*/
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            /*第一个参数表示生成二维码的文本内容，第二个参数表示编码格式，第三个参数表示生成的二维码的宽度，第四个参数表示生成的二维码的高度，第五个参数可选，可以用来设置文本的编码*/
            /*encode方法的返回值是一个BitMatrix，你可以把BitMatrix理解成一个二维数组，这个二维数组的每一个元素都表示一个像素点是否有数据。*/

            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }

}

/*接下来我们需要定义一个int数组用来存放Bitmap中所有像素点的颜色，
可是我们又怎么知道每一个像素点是什么颜色呢？
这个时候就需要我们遍历BitMatrix了，
如果BitMatrix上的点表示 该点有数据，
那么对应在Bitmap上的像素点就是黑色，
否则就是白色。BitMatrix中的get方法的返回值为一个boolean类型，
true表示该点有数据，false表示该点没有数据。
通过两个嵌套的for循环将BitMatrix遍历一遍，然后给pixels数组都赋上值，
OK，pixels数组有值之后，接下来调用Bitmap的createBitmap方法创建一个Bitmap出来就可以了，
createBitmap方法共接收6个参数，
第一个参数表示Bitmap中所有像素点的颜色，第二个参数表示像素点的偏移量，
第三个参数表示Bitmap每行有多少个像素点，第四个参数表示生成的Bitmap的宽度，
第五个参数表示生成的Bitmap的高度，第六个参数表示生成的Bitmap的色彩模式，
因为二维码只有黑白两种颜色，所以我们可以不用考虑透明度，直接使用RGB_565即可*/