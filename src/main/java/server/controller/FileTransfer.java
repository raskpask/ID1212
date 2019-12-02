package server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransfer implements Runnable {

  private static final String FILE_DIR = System.getProperty("user.dir") + "/serverfiles/";
  static final int PORT = 2456;

  private Socket s;
  private ServerSocket ss;
  private String filename;

  public FileTransfer() {
    try {
      ss = new ServerSocket(PORT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    uploadFile();
  }

  private void uploadFile() {
    try {
      s = ss.accept();
      System.out.println("Uploading file...");

      File file = new File(FILE_DIR + this.filename);
      byte[] bytes = new byte[1024];
      InputStream in = new FileInputStream(file);
      OutputStream out = s.getOutputStream();
      int count;
      while ((count = in.read(bytes)) > 0) {
        out.write(bytes, 0, count);
      }
      out.close();
      in.close();
      s.close();
      System.out.println("Done.");

    } catch (Exception e) {
      System.out.println("closing connection");
    }
  }


  public void sendFileToServer(int port, String filename) {
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

  public void setFilename(String filename) {
    this.filename = filename;
  }

}
