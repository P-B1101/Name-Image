package m.pedram.name_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import androidx.core.content.ContextCompat;

import java.util.Random;

public class NameImage {
    private String[] stringColor;
    private int[] images;
    private Context context;
    private String firstName;
    private String lastName;
    private int width;
    private int height;
    private Typeface font;
    private int textSize;
    private int textColor;
    private Bitmap bitmap;


    private NameImage(Builder builder) {
        stringColor = builder.stringColor;
        context = builder.context;
        textSize = builder.textSize;
        images = builder.images;
        firstName = builder.firstName;
        lastName = builder.lastName;
        width = builder.width;
        height = builder.height;
        font = builder.font;
        textColor = builder.textColor;
    }

    public NameImage createRandomUserImage() {
        boolean hasName = firstName != null && !firstName.equals("");
        boolean hasLastName = lastName != null && !lastName.equals("");
        String text = "";
        if (hasName)
            text = firstName.substring(0, 1);
        if (hasLastName)
            text = text + " " + lastName.substring(0, 1);
        Random random = new Random();
        int low = 0;
        int high = stringColor.length;
        int result = random.nextInt(high - low) + low;
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (stringColor == null)
            throw new NullPointerException("Color resource can not be null");
        if (images == null)
            throw new NullPointerException("Image resource can not be null");
        if (result > stringColor.length - 1)
            result -= 1;
        if (result < 0)
            result = 0;
        int selectedImage = images[0];
        String selectedColor = stringColor[0];
        for (int i = 0; i < stringColor.length; i++) {
            if (i == result) {
                selectedColor = stringColor[result];
                break;
            }

        }

        for (int i = 0; i < images.length; i++) {
            if (i == result) {
                selectedImage = images[result];
                break;
            }

        }
        if (selectedColor == null || selectedColor.equals(""))
            throw new NullPointerException("Color resource can not be null");
        p.setColor(Color.parseColor(selectedColor));
        if (selectedImage == 0)
            throw new NullPointerException("Image resource can not be null");
        Drawable drawable = ContextCompat.getDrawable(context, selectedImage);
        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        Bitmap.Config config = bitmap.getConfig();
        if (config == null)
            config = Bitmap.Config.ARGB_8888;
        bitmap = bitmap.copy(config, true);
        Canvas canvas = new Canvas(bitmap);
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(font);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int x = width / 2 - rect.width() / 2;
        int y = height / 2 + rect.height() / 4;
        canvas.drawBitmap(bitmap, rect, rect, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        canvas.drawText(text, x, y, paint);
        this.bitmap = bitmap;
        return this;
    }

    public Drawable buildDrawable() {
        if (bitmap == null)
            throw new NullPointerException("Call this method after createBuilder CreateUserBitmap");
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public Bitmap build() {
        if (bitmap == null)
            throw new NullPointerException("Call this method after createBuilder CreateUserBitmap");
        return bitmap;
    }

    public NameImage makeCircle() {
        if (bitmap == null)
            throw new NullPointerException("Call this method after createBuilder CreateUserBitmap");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        this.bitmap = output;
        return this;
    }

    public static class Builder {
        private String[] stringColor;
        private int[] images;
        private final Context context;
        private String firstName;
        private String lastName;
        private int width;
        private int height;
        private Typeface font;
        private int textSize;
        private int textColor;


        private void setColors() {
            stringColor = new String[4];
            stringColor[0] = "#FF4444";
            stringColor[1] = "#FFA933";
            stringColor[2] = "#2BABFF";
            stringColor[3] = "#A04EE6";
        }

        private void setImages() {
            images = new int[4];
            images[0] = R.drawable.user_image_1;
            images[1] = R.drawable.user_image_2;
            images[2] = R.drawable.user_image_3;
            images[3] = R.drawable.user_image_4;
        }

        private void setFont() {
            font = Typeface.createFromAsset(context.getAssets(), "font/iransfm.ttf");
        }

        private void setWidth() {
            width = context.getResources().getDimensionPixelSize(R.dimen.size);
        }

        private void setHeight() {
            height = context.getResources().getDimensionPixelSize(R.dimen.size);

        }

        private void setTextColor() {
            textColor = ContextCompat.getColor(context, R.color.white);
        }

        private void setTextSize() {
            textSize = context.getResources().getDimensionPixelSize(R.dimen.text_size);
        }

        public Builder(Context context) {
            this.context = context;
            setTextSize();
            setTextColor();
            setWidth();
            setHeight();
            setFont();
            setColors();
            setImages();
        }


        public Builder withStringColor(String[] stringColor) {
            this.stringColor = stringColor;
            return this;
        }

        public Builder withImages(int[] images) {
            this.images = images;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder withHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder withFont(Typeface font) {
            this.font = font;
            return this;
        }

        public Builder withTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder withTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public NameImage createBuilder() {
            return new NameImage(this);
        }
    }
}
