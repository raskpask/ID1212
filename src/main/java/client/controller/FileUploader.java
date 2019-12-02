package client.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUploader implements Runnable {

  private static final String FILE_DIR = System.getProperty("user.dir") + "/files/";
  public static final int PORT = 1456;

  private Socket s;
  public ServerSocket ss;
  private String filename;

  public FileUploader() {
    try {
      ss = new ServerSocket(PORT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setFilename(String filename){
    this.filename = filename;
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

  @Override
  public void run() {
    uploadFile();
  }
}
