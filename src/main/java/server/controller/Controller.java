package server.controller;

import common.FileDTO;
import common.FileServer;
import common.Notification;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.integration.FileServerDAO;
import server.model.File;
import server.model.User;

public class Controller extends UnicastRemoteObject implements FileServer {

  private final FileServerDAO fileServerDb;

  public Controller(String datasource, String dbms) throws RemoteException {
    super();
    fileServerDb = new FileServerDAO(datasource, dbms);
  }


  @Override
  public synchronized FileDTO[] listFiles(String credentials, Notification notification)
      throws RemoteException {
    if (login(credentials, notification)) {
      FileDTO[] files = fileServerDb.listFiles();
      for (FileDTO file : files) {
        notifyOwner(file, getUsername(credentials));
      }
      return fileServerDb.listFiles();
    }
    return null;
  }

  private String getUsername(String credentials) {
    return extractCredentials(credentials)[0];
  }

  private String getPassword(String credentials) {
    return extractCredentials(credentials)[1];
  }


  @Override
  public synchronized void newFile(String fileParams, String credentials, Notification notification)
      throws RemoteException {
    if (login(credentials, notification)) {
      String[] params = fileParams.split(":");
      String filename = params[0];
      String owner = getUsername(credentials);
      int size = Integer.parseInt(params[1]);
      File file = new File(filename, size, owner);
      fileServerDb.newFile(file);
      System.out.println("NEW FILE: " + filename + "    FOR USER: " + owner);
      requestResponse(getUsername(credentials), "NEW 1");
      return;
    }
    requestResponse(getUsername(credentials), "NEW 0");
  }

  @Override
  public synchronized FileDTO getFile(String filename, String credentials, Notification notification) throws RemoteException {
    if (login(credentials, notification)) {
      FileDTO gFile = fileServerDb.getFile(filename);
      notifyOwner(gFile, getUsername(credentials));
      return gFile;
    }
    return null;
  }


  private boolean login(String credentials, Notification notification) {
    String username = getUsername(credentials);
    String password = getPassword(credentials);
    User user = new User(username, password);
    if (fileServerDb.login(user, notification) != null) {
      System.out.println("NEW LOGIN FROM: " + username);
      return true;
    }
    return false;
  }


  public synchronized void register(String credentials, Notification notification) {
    try {
      String username = getUsername(credentials);
      String password = getPassword(credentials);
      User user = new User(username, password);
      fileServerDb.register(user, notification);
      requestResponse(username, "REGISTER 1");
    } catch (Exception e) {
      requestResponse(getUsername(credentials), "REGISTER 0");
    }
  }

  private String[] extractCredentials(String credentials) {
    return credentials.split(":");
  }

  private void requestResponse(String username, String response) {
    try {
      Notification notification = (Notification) fileServerDb.getNotification(username);
      notification.print(response);
    } catch (Exception e) {
      System.err.println("Could not send response to client: " + username + ".");
    }
  }


  private void notifyOwner(FileDTO file, String username) throws RemoteException {
    try {
      if (!username.equals(file.getOwner())) {
        Notification notification = (Notification) fileServerDb.getNotification(file.getOwner());
        notification.print("Your file: " + file.getName() + " was read by " + username);
      }
    } catch (Exception e) {
      System.err.println("Client " + file.getOwner() + " disconnected. No notification sent.");
    }
  }
}
