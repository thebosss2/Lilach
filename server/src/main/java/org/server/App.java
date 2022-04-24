package org.server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    private static Server server;
    public static void main( String[] args ) throws IOException
    {
        server = new Server(3000);
        server.listen();
    }
}
