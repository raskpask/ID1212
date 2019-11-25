package client.startup;

import client.view.ServerNotification;
import client.view.Terminal;
import common.Notification;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main {

  public static void main(String[] args) {
    try {
      Notification notification = startRMIServant();
      Terminal terminal = new Terminal(notification);
      terminal.start();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private static Notification startRMIServant() throws RemoteException {
    /*try {
      //LocateRegistry.getRegistry().list();
    } catch (RemoteException re) {
      //LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      //re.printStackTrace();
    }*/
    try {
      ServerNotification serverNotification = new ServerNotification();
      Naming.rebind(Notification.CLIENT_NAME, serverNotification);
      Notification notification = (Notification) Naming.lookup(Notification.CLIENT_NAME);
      return notification;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
