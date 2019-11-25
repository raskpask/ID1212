package client.view;

import common.FileDTO;
import common.FileServer;
import common.Notification;
import java.rmi.RemoteException;

public class Communicator implements Runnable {

  FileServer fileServer;

  public Communicator(FileServer fileServer){
    this.fileServer = fileServer;
  }

  public void run(){

  }

  public FileDTO[] listFiles() throws RemoteException{
    FileDTO[] files = fileServer.listFiles();
    return files;
  }

  public FileDTO getFile(String filename) throws RemoteException{
    FileDTO gFile = fileServer.getFile(filename);
    return gFile;
  }

  public void newFile(String file) throws RemoteException{
    fileServer.newFile(file);
  }

  public void login(String credentials, Notification notification) throws RemoteException{
    fileServer.login(credentials, notification);
  }

  public void logout() throws RemoteException {
    fileServer.logout();
  }

  public void register(String credentials, Notification notification) throws RemoteException {
    fileServer.register(credentials, notification);
  }

}
