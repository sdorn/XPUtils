/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.xpserver;

/**
 * The Class Message.
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

    /**
     * Instantiates a new message.
     *
     * @param Header the header
     * @param element the element
     * @param f1 the f1
     * @param f2 the f2
     * @param f3 the f3
     * @param f4 the f4
     * @param f5 the f5
     * @param f6 the f6
     * @param f7 the f7
     * @param f8 the f8
     */
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

    /**
     * Instantiates a new message.
     *
     * @param Header the header
     * @param element the element
     * @param f1 the f1
     * @param f2 the f2
     * @param f3 the f3
     * @param f4 the f4
     */
    public Message(String Header, int element, float f1, float f2, float f3, float f4) {
        this.Header = Header;
        this.element = element;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
    }

    /**
     * Gets the header.
     *
     * @return the header
     */
    public String getHeader() {
        return Header;
    }

    /**
     * Sets the header.
     *
     * @param Header the new header
     */
    public void setHeader(String Header) {
        this.Header = Header;
    }

    /**
     * Gets the f1.
     *
     * @return the f1
     */
    public float getF1() {
        return f1;
    }

    /**
     * Sets the f1.
     *
     * @param f1 the new f1
     */
    public void setF1(float f1) {
        this.f1 = f1;
    }

    /**
     * Gets the f2.
     *
     * @return the f2
     */
    public float getF2() {
        return f2;
    }

    /**
     * Sets the f2.
     *
     * @param f2 the new f2
     */
    public void setF2(float f2) {
        this.f2 = f2;
    }

    /**
     * Gets the f3.
     *
     * @return the f3
     */
    public float getF3() {
        return f3;
    }

    /**
     * Sets the f3.
     *
     * @param f3 the new f3
     */
    public void setF3(float f3) {
        this.f3 = f3;
    }

    /**
     * Gets the f4.
     *
     * @return the f4
     */
    public float getF4() {
        return f4;
    }

    /**
     * Sets the f4.
     *
     * @param f4 the new f4
     */
    public void setF4(float f4) {
        this.f4 = f4;
    }

    /**
     * Gets the f5.
     *
     * @return the f5
     */
    public float getF5() {
        return f5;
    }

    /**
     * Sets the f5.
     *
     * @param f5 the new f5
     */
    public void setF5(float f5) {
        this.f5 = f5;
    }

    /**
     * Gets the f6.
     *
     * @return the f6
     */
    public float getF6() {
        return f6;
    }

    /**
     * Sets the f6.
     *
     * @param f6 the new f6
     */
    public void setF6(float f6) {
        this.f6 = f6;
    }

    /**
     * Gets the f7.
     *
     * @return the f7
     */
    public float getF7() {
        return f7;
    }

    /**
     * Sets the f7.
     *
     * @param f7 the new f7
     */
    public void setF7(float f7) {
        this.f7 = f7;
    }

    /**
     * Gets the f8.
     *
     * @return the f8
     */
    public float getF8() {
        return f8;
    }

    /**
     * Sets the f8.
     *
     * @param f8 the new f8
     */
    public void setF8(float f8) {
        this.f8 = f8;
    }  
    
    
    /**
     * Gets the element.
     *
     * @return the element
     */
    public int getElement() {
        return element;
    }

    /**
     * Sets the element.
     *
     * @param element the new element
     */
    public void setElement(int element) {
        this.element = element;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
