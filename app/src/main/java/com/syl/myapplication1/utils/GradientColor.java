package com.syl.myapplication1.utils;

/**
 * Created by Bright on 2018/10/6.
 *
 * @Describe
 * @Called
 */
public class GradientColor {
    private int startColor;
    private int endColor;

    public GradientColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }
}
