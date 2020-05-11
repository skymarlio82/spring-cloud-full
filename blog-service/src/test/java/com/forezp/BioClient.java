
package com.forezp;

import java.io.IOException;
import java.net.Socket;

public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            socket.getOutputStream().write("Hello, world!".getBytes());
            byte[] bytes = new byte[1024];
            socket.getInputStream().read(bytes);
            String returnResult = new String(bytes);
            System.out.println("Return result = " + returnResult);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}