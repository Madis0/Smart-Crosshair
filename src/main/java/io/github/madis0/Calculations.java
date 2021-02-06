package io.github.madis0;

import net.minecraft.util.math.MathHelper;

public class Calculations {
    public static int relativeW(int start, int end, int value, int total){
        if(value < total)
            return MathHelper.ceil(start + ((float)(end - start) / total * value));
        else
            return end;
    }

    public static int getPreciseInt(float number){
        float precision = 10000.0F;
        return MathHelper.ceil(number * precision);
    }

    public static String useSubscript(String text){
        text = text.replace('0', '₀');
        text = text.replace('1', '₁');
        text = text.replace('2', '₂');
        text = text.replace('3', '₃');
        text = text.replace('4', '₄');
        text = text.replace('5', '₅');
        text = text.replace('6', '₆');
        text = text.replace('7', '₇');
        text = text.replace('8', '₈');
        text = text.replace('9', '₉');
        text = text.replace('+', '₊');
        text = text.replace('-', '₋');
        text = text.replace('=', '₌');
        text = text.replace('(', '₍');
        text = text.replace(')', '₎');

        return text;
    }
}