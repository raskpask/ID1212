package common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notification extends Remote {

  public static final String CLIENT_NAME = "client";

  public void print(String message) throws RemoteException;

}
