package client.view;

import common.FileDTO;
import common.FileServer;
import common.Notification;
import java.rmi.RemoteException;

public class Communicator {

  FileServer fileServer;

  public Communicator(FileServer fileServer){
    this.fileServer = fileServer;
  }

  public FileDTO[] listFiles(String credentials, Notification notification) throws RemoteException{
    FileDTO[] files = fileServer.listFiles(credentials, notification);
    return files;
  }

  public FileDTO getFile(String filename, String credentials, Notification notification) throws RemoteException{
    FileDTO gFile = fileServer.getFile(filename, credentials, notification);
    return gFile;
  }

  public void newFile(String file, String credentials, Notification notification) throws RemoteException{
    fileServer.newFile(file, credentials, notification);
  }

  public void register(String credentials, Notification notification) throws RemoteException {
    fileServer.register(credentials, notification);
  }

}
