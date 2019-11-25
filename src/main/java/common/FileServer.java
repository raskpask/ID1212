package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {

  public static final String FILESERVER_NAME = "fileserver";

  public FileDTO[] listFiles() throws RemoteException;

  public void newFile(String file) throws RemoteException;

  public FileDTO getFile(String filename) throws RemoteException;

  public void login(String credentials, Notification notification) throws RemoteException;

  public void logout() throws RemoteException;

  public void register(String credentials, Notification notification) throws RemoteException;

  public void removeFile(String credentials, String file) throws RemoteException;


}
