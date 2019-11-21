package client.view;

import common.FileServer;

public class Communicator implements Runnable {

  FileServer fileServer;

  public Communicator(FileServer fileServer){
    this.fileServer = fileServer;
  }

  public void run(){

  }

  public void newFile(String credentials, String file) throws Exception{
    fileServer.newFile(credentials, file);
  }

  public void login(String credentials) throws Exception{
    fileServer.login(credentials);
  }

}
