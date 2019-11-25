package client.view;

import common.Notification;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerNotification extends UnicastRemoteObject implements Notification {

  UserInterface userInterface;

  public ServerNotification() throws RemoteException {
    super();
    userInterface = new UserInterface();
  }

  @Override
  public void print(String message) throws RemoteException {
    userInterface.serverMessage(message);
  }
}
