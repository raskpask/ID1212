package client.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileDownloader {

  private static final String FILE_DIR = System.getProperty("user.dir") + "/files/";

  public void downloadFile(int port, String filename) {
    try {
      Socket socket = new Socket("localhost", port);
      InputStream in = socket.getInputStream();
      OutputStream out = new FileOutputStream(FILE_DIR + filename);

      byte[] bytes = new byte[16 * 1024];

      int count;
      while ((count = in.read(bytes)) > 0) {
        out.write(bytes, 0, count);
      }

      out.close();
      in.close();
      socket.close();
    } catch (Exception ex) {
      System.out.println("Can't setup server on this port number. ");
    }
  }

}
