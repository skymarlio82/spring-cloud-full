
package com.forezp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    static int count = 0;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try (
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream()
                    ) {
                        byte[] bytes = new byte[1024];
                        while (inputStream.read(bytes) != -1) {
                            String res = new String(bytes);
                            res = ((++count) + res);
                            outputStream.write(res.getBytes());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}