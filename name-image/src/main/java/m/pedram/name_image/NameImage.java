package m.pedram.name_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameImage {
    private List<Integer> images;
    private Context context;
    private String firstName;
    private String lastName;
    private int width;
    private int height;
    private Typeface font;
    private int textSize;
    private int textColor;


    private NameImage(Builder builder) {
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

    private ImageCreator createRandomUserImage() {
        boolean hasName = firstName != null && !firstName.equals("");
        boolean hasLastName = lastName != null && !lastName.equals("");
        String text = "";
        if (hasName)
            text = firstName.substring(0, 1);
        if (hasLastName)
            text = text + " " + lastName.substring(0, 1);
        Random random = new Random();
        int low = 0;
        int high = images.size();
        int result = random.nextInt(high - low) + low;
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (images == null)
            throw new NullPointerException("Image resource can not be null");
        if (result > images.size() - 1)
            result -= 1;
        if (result < 0)
            result = 0;
        int selectedImage = images.get(0);

        for (int i = 0; i < images.size(); i++) {
            if (i == result) {
                selectedImage = images.get(result);
                break;
            }
        }
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
        return new ImageCreator(context, bitmap);
    }

    public static class Builder {
        private List<Integer> images;
        private final Context context;
        private String firstName;
        private String lastName;
        private int width;
        private int height;
        private Typeface font;
        private int textSize;
        private int textColor;


        private void setImages() {
            images = new ArrayList<>();
            images.add(R.drawable.user_image_1);
            images.add(R.drawable.user_image_2);
            images.add(R.drawable.user_image_3);
            images.add(R.drawable.user_image_4);
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
            setImages();
        }


        public Builder withDrawables(List<Integer> images) {
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

        public ImageCreator build() {
            return new NameImage(this).createRandomUserImage();
        }
    }
}
