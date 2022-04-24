package org.client;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    private Client client;

    public static void main( String[] args ) throws IOException
    {
        Client client = new Client("localhost", 3000);
        client.openConnection();
        Scanner s = new Scanner(System.in);
        String str;
        while(true){
            str = s.nextLine();
            client.sendToServer(str);

        }


    }
}
