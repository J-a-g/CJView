package com.jag.sj.cjview;

/**
 * Created by Administrator on 2017.5.3.
 */

public class HelloWorld {

    static {
        System.out.print("test..111");
        System.loadLibrary("hello");
    }

    public static void main(String[] args) {
        System.out.print("test..");
        displayHelloWorld();
    }

    public static native void displayHelloWorld();
}
