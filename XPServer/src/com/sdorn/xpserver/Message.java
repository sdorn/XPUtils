/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.xpserver;

/**
 *
 * @author siegf
 */
public class Message {

    String Header = "";
    int element = 0;
    float f1 = 0;
    float f2 = 0;
    float f3 = 0;
    float f4 = 0;
    float f5 = 0;
    float f6 = 0;
    float f7 = 0;
    float f8 = 0;

    public Message() {
    }

    public Message(String Header, int element, float f1, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.Header = Header;
        this.element = element;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.f6 = f5;
        this.f7 = f6;
        this.f8 = f7;
    }

    public Message(String Header, int element, float f1, float f2, float f3, float f4) {
        this.Header = Header;
        this.element = element;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String Header) {
        this.Header = Header;
    }

    public float getF1() {
        return f1;
    }

    public void setF1(float f1) {
        this.f1 = f1;
    }

    public float getF2() {
        return f2;
    }

    public void setF2(float f2) {
        this.f2 = f2;
    }

    public float getF3() {
        return f3;
    }

    public void setF3(float f3) {
        this.f3 = f3;
    }

    public float getF4() {
        return f4;
    }

    public void setF4(float f4) {
        this.f4 = f4;
    }

    public float getF5() {
        return f5;
    }

    public void setF5(float f5) {
        this.f5 = f5;
    }

    public float getF6() {
        return f6;
    }

    public void setF6(float f6) {
        this.f6 = f6;
    }

    public float getF7() {
        return f7;
    }

    public void setF7(float f7) {
        this.f7 = f7;
    }

    public float getF8() {
        return f8;
    }

    public void setF8(float f8) {
        this.f8 = f8;
    }  
    
    
    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    @Override
    public String toString() {
        String data = this.Header + "," 
                + this.element + ","
                + Float.toString(this.f1) + ","
                + Float.toString(this.f2) + ","
                + Float.toString(this.f3) + ","
                + Float.toString(this.f4) + ","
                + Float.toString(this.f5) + ","
                + Float.toString(this.f6) + ","
                + Float.toString(this.f7) + ","
                + Float.toString(this.f8);

        return data;
    }

}
