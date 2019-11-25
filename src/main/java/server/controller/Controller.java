package server.controller;

import common.FileDTO;
import common.FileServer;
import common.UserDTO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.integration.FileServerDAO;
import server.model.File;
import server.model.User;

public class Controller extends UnicastRemoteObject implements FileServer {

  private final FileServerDAO fileServerDb;
  private UserDTO currentUser;

  public Controller(String datasource, String dbms) throws RemoteException {
    super();
    fileServerDb = new FileServerDAO(datasource, dbms);
  }


  @Override
  public FileDTO[] listFiles() throws RemoteException {
    return fileServerDb.listFiles();
  }

  @Override
  public void newFile(String fileParams) throws RemoteException{
    if (loggedin()) {
      String[] params = fileParams.split(":");
      String filename = params[0];
      String owner = currentUser.getUsername();
      int size = Integer.parseInt(params[1]);
      File file = new File(filename, size, owner);
      fileServerDb.newFile(file);
      System.out.println("NEW FILE: " + filename + "    FOR USER: " + owner);
    }
  }

  @Override
  public FileDTO getFile(String filename) throws RemoteException {
    if(loggedin()){
      FileDTO gFile = fileServerDb.getFile(filename);
      return gFile;
    }
    return null;
  }


  @Override
  public void login(String credentials) throws RemoteException {
    if (!loggedin()) {
      String username = extractCredentials(credentials)[0];
      String password = extractCredentials(credentials)[1];
      User user = new User(username, password);
      this.currentUser = fileServerDb.login(user);
      System.out.println("NEW LOGIN FROM: " + username);
    }
  }

  @Override
  public void logout() throws RemoteException {
    if (loggedin()) {
      currentUser = null;
    }
  }

  public void register(String credentials) throws RemoteException {
    if (!loggedin()) {
      String username = extractCredentials(credentials)[0];
      String password = extractCredentials(credentials)[1];
      User user = new User(username, password);
      fileServerDb.register(user);
    }
  }

  @Override
  public void removeFile(String credentials, String file) throws RemoteException {

  }

  private String[] extractCredentials(String credentials) {
    return credentials.split(":");
  }

  private boolean loggedin() {
    return currentUser != null;
  }
}
