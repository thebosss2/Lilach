package org.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {

            App.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
