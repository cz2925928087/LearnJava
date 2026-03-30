package utils;

import java.util.Scanner;


public class ScannerSingleton {
    //懒汉式单例方法
    private static Scanner scanner;

    private ScannerSingleton() {

    }

    public static Scanner getScannerSingleton() {
        if(scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
