package server.controller;

import common.FileServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.integration.FileServerDAO;

public class Controller extends UnicastRemoteObject implements FileServer {

  private final FileServerDAO fileServerDb;

  public Controller(String datasource, String dbms) throws RemoteException {
    super();
    fileServerDb = new FileServerDAO(datasource, dbms);
  }

  public void newFile(String credentials, String file) {

  }
}
