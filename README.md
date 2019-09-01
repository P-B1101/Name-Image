# Name-Image
create bitmap or drawable that have solid color with the first letter of your name on it

## Examples

![Alt text](/images/temp%20image%201.jpg?raw=true)             

![Alt text](/images/temp%20image%202.jpg?raw=true)                   ![Alt text](/images/temp%20image%203.jpg?raw=true)


## Getting Started

### Installing
add code below to project level build.gradle
```
maven { url 'https://jitpack.io' }
```

and this line to app level build.gradle
```
implementation 'com.github.P-B1101:Name-Image:1.0.1'
```
### usage

create bitmap first
```
Bitmap bitmap = new NameImage.Builder(context)
                        .withFirstName(name)
                        .withLastName(lastName)
                        .withFont(font)
                        .withTextSize(textSize) // font size in px use getDimens for better result on difrent screens
                        .withDrawables(drawables) // list of drawables. use R.drawable and add it to the list
                        .withTextColor(textColor) // use ContextCompat.getColor
                        .withWidth(width) // size in px
                        .withHeight(height) // size in px
                        .build()
                        .makeCircle()
                        .createBitmap();
```

or drawable
```
Drawable drawable = new NameImage.Builder(context)
                        .withFirstName(name)
                        .withLastName(lastName)
                        .withFont(font)
                        .withTextSize(textSize) // font size in px use getDimens for better result on difrent screens
                        .withDrawables(drawables) // list of drawables. use R.drawable and add it to the list
                        .withTextColor(textColor) // use ContextCompat.getColor
                        .withWidth(width) // size in px
                        .withHeight(height) // size in px
                        .build()
                        .makeCircle()
                        .createDrawable();
```

and then use it on image view
```
imageView.setBackground(drawable);
```
or
```
imageView.setImageBitmap(bitmap);
```

well done :)
