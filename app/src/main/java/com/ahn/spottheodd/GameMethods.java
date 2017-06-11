package com.ahn.spottheodd;

import android.graphics.Color;

/**
 * Created by Ali on 4/7/2016.
 */
public class GameMethods {
    /*floatMax=0.5f, floatMin=0.4f;*/


    private int color(int rand) {
        int color;
        switch (rand) {
            case 0:
                color = 0xFFBB0000;
                break;
            case 1:
                color = 0xFF0000BB;
                break;
            case 2:
                color = 0xFF00BB00;
                break;
            case 3:
                color = 0xFF00BBBB;
                break;
            case 4:
                color = 0xFFBB00BB;
                break;
            case 5:
                color = 0xFFCCCC00;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        return color;
    }

    public static int colorLight(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /*public static int colorDark(int color, float factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }
*/
    public String[] changeColor(float factor) {
        int changeInt;
        //int darkOrLight = (int) ((Math.random()*2));
        int randomNum = (int) (Math.random() * 6);
        int oldInt = randomNum;
        while(oldInt==randomNum){
            randomNum = (int) (Math.random() * 6);
        }
        int randomColorInt = color(randomNum);

//        float factor = (float) ((Math.random()*floatMax)+floatMin);

        /*if(darkOrLight==0)
            changeInt = colorDark(randomColorInt,1-factor);
        else*/
        changeInt = colorLight(randomColorInt, factor);

        while (changeInt == randomColorInt){
            /*if(darkOrLight==0)
                changeInt = colorDark(randomColorInt,1-factor);
            else*/ changeInt = colorLight(randomColorInt, factor);
        }

        String randomColor = Integer.toHexString(randomColorInt), change = Integer.toHexString(changeInt);

        String[] values = new String[2];
        values[0] = randomColor;
        values[1] = change;
        return values;
    }

}
