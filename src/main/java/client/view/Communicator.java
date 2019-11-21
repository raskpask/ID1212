package client.view;

import common.FileServer;

public class Communicator implements Runnable {

  FileServer fileServer;

  public Communicator(FileServer fileServer){
    this.fileServer = fileServer;
  }

  public void run(){

  }

  public void newFile(String credentials, String file){
    fileServer.newFile(credentials, file);
  }

}
