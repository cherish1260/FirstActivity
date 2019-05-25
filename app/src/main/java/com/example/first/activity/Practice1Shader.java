package com.example.first.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.first.R;

/**
 * Created by sxiaoxia on 2018/4/17.
 */

public class Practice1Shader extends View {

    public Practice1Shader(Context context) {
        super(context);
    }

    public Practice1Shader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice1Shader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
//        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(300,300,200, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawCircle(500, 500, 400, paint);
    }
}
