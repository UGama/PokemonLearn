package com.example.pokemonlearn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Gama on 18/5/17.
 */

public class Evolve extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    private PercentRelativeLayout Layout_Whole;
    private int Width;
    private int Height;

    private OwnPet E_OwnPet;
    private OwnItem E_PokeMonStone;
    private PokeMon ES_PokeMon;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private PercentRelativeLayout Layout_Up;
    private ImageView Roof1;
    private ImageView Roof2;
    private ImageView PokeMon;
    private ImageView Stone;
    private ImageView Plus;
    private int PWidth;
    private int PHeight;
    private int PTop;
    private int PLeft;
    private int SWidth;
    private int SHeight;
    private int STop;
    private int SLeft;

    private Button Evolve;
    private Button Cancel;
    private Animation Float2;
    private Animation Float3;

    private ImageView Text;
    private TextView Message;
    private ImageView Screen;
    private ImageView Next_Text;
    private Animation Float1;

    private ImageView White;
    private ImageView Black;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evolve);

        Layout_Whole = (PercentRelativeLayout) findViewById(R.id.layout_whole);
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        Width = metrics.widthPixels;
        Height = metrics.heightPixels;
        Log.i("Windows", String.valueOf(Height) + " " + String.valueOf(Width));

        Intent intent = getIntent();
        String Name = intent.getStringExtra("PMName");
        Log.i("Name", Name);
        List<OwnPet> pets = DataSupport.where("Name = ?", Name).find(OwnPet.class);
        E_OwnPet = pets.get(0);
        String PMStone = intent.getStringExtra("PMStone");
        Log.i("PMStone", PMStone);
        List<OwnItem> ownItems = DataSupport.where("Name = ?", PMStone).find(OwnItem.class);
        E_PokeMonStone = ownItems.get(0);
        String SName = intent.getStringExtra("S-PMName");
        Log.i("Senior-Name", SName);
        List<PokeMon> pokeMons = DataSupport.where("Name = ?", SName).find(PokeMon.class);
        ES_PokeMon = pokeMons.get(0);

        Layout_Up = (PercentRelativeLayout) findViewById(R.id.Layout_up);
        Roof1 = (ImageView) findViewById(R.id.roof1);
        Roof1.setVisibility(View.GONE);
        Roof2 = (ImageView) findViewById(R.id.roof2);
        Roof2.setVisibility(View.GONE);
        PokeMon = (ImageView) findViewById(R.id.pokemon);
        PokeMon.setImageResource(E_OwnPet.getImageResourceId());
        PokeMon.setVisibility(View.GONE);
        Stone = (ImageView) findViewById(R.id.stone);
        Stone.setImageResource(E_PokeMonStone.getImageResourceId());
        Stone.setVisibility(View.GONE);
        Plus = (ImageView) findViewById(R.id.plus);
        Plus.setVisibility(View.GONE);

        Evolve = (Button) findViewById(R.id.evolve);
        Evolve.setVisibility(View.GONE);
        Evolve.setOnClickListener(this);
        Evolve.setOnTouchListener(this);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setVisibility(View.GONE);
        Cancel.setOnClickListener(this);
        Cancel.setOnTouchListener(this);
        Float2 = AnimationUtils.loadAnimation(Evolve.this, R.anim.cap_float2);
        Float3 = AnimationUtils.loadAnimation(Evolve.this, R.anim.cap_float3);

        Text = (ImageView) findViewById(R.id.evolve_text);
        Text.setVisibility(View.GONE);
        Message = (TextView) findViewById(R.id.evolve_message);
        Message.setVisibility(View.GONE);
        Screen = (ImageView) findViewById(R.id.screen);
        Screen.setVisibility(View.GONE);
        Next_Text = (ImageView) findViewById(R.id.next_text);
        Next_Text.setVisibility(View.GONE);
        Float1 = AnimationUtils.loadAnimation(Evolve.this, R.anim.cap_float1);
        Float1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Evolve.setVisibility(View.VISIBLE);
                Evolve.startAnimation(Float2);
                Cancel.setVisibility(View.VISIBLE);
                Cancel.startAnimation(Float3);
                String tip = "确定使用 " + E_PokeMonStone.getName() + " 进化 " + E_OwnPet.getName() + " 吗？";
                Message.setText(tip);
                ScreenRun(Screen);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer1.setVisibility(View.VISIBLE);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(Evolve.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(Evolve.this, R.anim.trans_out_down);
        transfer1.startAnimation(trans_out1);
        transfer2.startAnimation(trans_out2);
        trans_out2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                transfer1.setVisibility(View.GONE);
                transfer2.setVisibility(View.GONE);
                InitUI();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Black = (ImageView) findViewById(R.id.black);
        White = (ImageView) findViewById(R.id.white);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.evolve:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Evolve.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Evolve.getBackground().setAlpha(255);
                }
                break;
            case R.id.cancel:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Cancel.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Cancel.getBackground().setAlpha(255);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evolve:
                Plus.setVisibility(View.GONE);
                List<OwnItem> list = DataSupport.where("Name = ?", E_PokeMonStone.getName()).find(OwnItem.class);
                OwnItem ownItem = list.get(0);
                int a = ownItem.getNumber() - 1;
                if (a == 0) {
                    DataSupport.deleteAll(OwnItem.class, "Name = ?", ownItem.getName());
                } else {
                    OwnItem ownItem1 = new OwnItem();
                    ownItem1.setNumber(a);
                    ownItem1.updateAll("Name = ?", E_PokeMonStone.getName());
                }
                Message.setText("");
                Combine();
                break;
            case R.id.cancel:
                Intent intent2 = new Intent();
                setResult(RESULT_CANCELED, intent2);
                finish();
                break;
        }
    }

    public void InitUI() {
        Roof1.setVisibility(View.VISIBLE);
        PokeMon.setVisibility(View.VISIBLE);

        ObjectAnimator Left1 = ObjectAnimator.ofFloat(Roof1, "translationX", -Width, 0);
        Left1.setDuration(300);
        ObjectAnimator Left2 = ObjectAnimator.ofFloat(PokeMon, "translationX", -Width, 0);
        Left2.setDuration(300);
        ObjectAnimator cd = ObjectAnimator.ofFloat(Roof1, "rotation", 0, 0);
        cd.setDuration(300);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(Left1).with(Left2).before(cd);
        animatorSet1.start();
        animatorSet1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Roof2.setVisibility(View.VISIBLE);
                Stone.setVisibility(View.VISIBLE);
                ObjectAnimator Right1 = ObjectAnimator.ofFloat(Roof2, "translationX", Width, 0);
                Right1.setDuration(300);
                ObjectAnimator Right2 = ObjectAnimator.ofFloat(Stone, "translationX", Width, 0);
                Right2.setDuration(300);
                ObjectAnimator cd = ObjectAnimator.ofFloat(Roof2, "rotation", 0, 0);
                cd.setDuration(200);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(Right1).with(Right2).before(cd);
                animatorSet2.start();
                animatorSet2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Plus.setVisibility(View.VISIBLE);
                        ObjectAnimator Plus_Show1 = ObjectAnimator.ofFloat(Plus, "rotation", -90, 0);
                        Plus_Show1.setDuration(800);
                        ObjectAnimator Plus_Show2 = ObjectAnimator.ofFloat(Plus, "Alpha", 0, 1);
                        Plus_Show2.setDuration(800);
                        AnimatorSet animatorSet3 = new AnimatorSet();
                        animatorSet3.play(Plus_Show1).with(Plus_Show2);
                        animatorSet3.start();
                        Text.setVisibility(View.VISIBLE);
                        Text.startAnimation(Float1);
                        Message.setVisibility(View.VISIBLE);
                        Message.startAnimation(Float1);
                        Screen.setVisibility(View.VISIBLE);
                        Screen.startAnimation(Float1);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void ScreenRun(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.0f, 0.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "translationX",
                0, 450);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();

    }

    public void Combine() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Roof1, "Alpha", 1, 0);
        objectAnimator.setDuration(800);
        objectAnimator.start();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(Roof2, "Alpha", 1, 0);
        objectAnimator1.setDuration(800);
        objectAnimator1.start();
        int[] a = new int[2];
        PokeMon.getLocationInWindow(a);
        PWidth = PokeMon.getWidth();
        PHeight = PokeMon.getHeight();
        PTop = PokeMon.getTop();
        PLeft = PokeMon.getLeft();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(1400);
        valueAnimator.setObjectValues(new PointF(PLeft, PTop));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = PLeft - fraction * (PLeft - (Width / 2 - PWidth / 2));
                point.y = PTop + fraction * ((Height * 0.48f / 2) - PHeight / 2 - PTop);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PokeMon.setX(point.x);
                PokeMon.setY(point.y);
            }
        });
        Stone.getLocationInWindow(a);
        SLeft = Stone.getLeft();
        SWidth = Stone.getWidth();
        SHeight = Stone.getHeight();
        STop = Stone.getTop();
        ValueAnimator valueAnimator1 = new ValueAnimator();
        valueAnimator1.setDuration(2000);
        valueAnimator1.setObjectValues(new PointF(SLeft, STop));
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = SLeft + fraction * (Width / 2 - SWidth / 2 - SLeft);
                point.y = STop - fraction * (STop - Height * 0.48f / 2 + SHeight / 2);
                return point;
            }
        });
        valueAnimator1.start();
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Stone.setX(point.x);
                Stone.setY(point.y);
            }
        });
        valueAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Stone.setVisibility(View.GONE);
                ObjectAnimator cd = ObjectAnimator.ofFloat(PokeMon, "rotation", 0, 0);
                cd.setDuration(400);
                cd.start();
                cd.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        PrePare();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1, 0.0f);
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1, 0.0f);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", 0, 180);
        ObjectAnimator.ofPropertyValuesHolder(Stone,
                propertyValuesHolder, propertyValuesHolder1, propertyValuesHolder2).
                setDuration(2000).start();
    }

    public void PrePare() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(Width / 2 - PWidth / 2, Height * 0.48f / 2 - PHeight / 2));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = Width / 2 - PWidth / 2;
                point.y = Height * 0.48f / 2 - PHeight / 2 + fraction * Height * 0.26f;
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PokeMon.setX(point.x);
                PokeMon.setY(point.y);
            }
        });
        Black.setVisibility(View.VISIBLE);
        ObjectAnimator black = ObjectAnimator.ofFloat(Black, "Alpha", 0, 1.0f);
        black.setDuration(2000);
        black.start();
        black.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LightDown();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void LightDown() {
        Light1 light1 = new Light1(Evolve.this);
        Layout_Whole.addView(light1);
    }

    class Light1 extends View {
        public Light1(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.light1);
            Paint p = new Paint();
            canvas.drawBitmap(bitmap, Width / 2 - bitmap.getWidth() / 2, bitmap.getHeight(), p);
        }
    }
}
