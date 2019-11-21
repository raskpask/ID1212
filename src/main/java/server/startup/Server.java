package server.startup;

import common.FileServer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import server.controller.Controller;

public class Server {

  private String fileServerName = FileServer.FILESERVER_NAME;
  private String dataSource = "fileserver";
  private String dbms = "mysql";

  public static void main(String[] args) {
    try {
      Server server = new Server();
      server.startRMIServant();
      System.out.println("Server started");
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  private void startRMIServant() throws RemoteException {
    try {
      LocateRegistry.getRegistry().list();
    } catch (RemoteException re) {
      LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      //re.printStackTrace();
    }
    try {
      Controller controller = new Controller(dataSource, dbms);
      Naming.rebind(fileServerName, controller);
      //String[] hej = Naming.list(fileServerName);
      //int i = 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
