package server.controller;

import common.FileServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.integration.FileServerDAO;
import server.model.File;

public class Controller extends UnicastRemoteObject implements FileServer {

  private final FileServerDAO fileServerDb;

  public Controller(String datasource, String dbms) throws RemoteException {
    super();
    fileServerDb = new FileServerDAO(datasource, dbms);
  }


  @Override
  public void newFile(String credentials, String fileParams) {
    System.out.println("New File! :P");
    String[] params = fileParams.split(":");
    String filename = params[0];
    String owner = credentials.split(":")[0];
    int size = Integer.parseInt(params[1]);
    File file = new File(filename, size, owner);
    fileServerDb.newFile(file);
  }

  @Override
  public void login(String credentials) throws RemoteException {
    String[] params = 
    String username =
  }

  @Override
  public void removeFile(String credentials, String file) throws RemoteException {

  }
}
