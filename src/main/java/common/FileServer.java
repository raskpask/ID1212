package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {

  public static final String FILESERVER_NAME = "fileserver";

  public FileDTO[] listFiles(String credentials, Notification notification) throws RemoteException;

  public void newFile(String file, String credentials, Notification notification) throws RemoteException;

  public FileDTO getFile(String filename, String credentials, Notification notification) throws RemoteException;

  public void register(String credentials, Notification notification) throws RemoteException;

  public void deleteFile(String credentials, String filename, Notification notification) throws RemoteException;

  public int sendFileToClient(String filename) throws RemoteException;

  public void sendFileToServer(int port, String filename) throws RemoteException;

}
