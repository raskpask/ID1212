package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {

  public static final String FILESERVER_NAME = "fileserver";


  public void newFile(String credentials, String file) throws RemoteException;
  public void login(String credentials) throws RemoteException;


  public void removeFile(String credentials, String file) throws RemoteException;


}
