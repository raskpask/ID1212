package client.view;

import common.Notification;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerNotification extends UnicastRemoteObject implements Notification {

  public ServerNotification() throws RemoteException {
    super();
  }

  @Override
  public void print(String message) throws RemoteException {
    System.out.println(message);
  }
}
