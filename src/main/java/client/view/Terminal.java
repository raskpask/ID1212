package client.view;

import common.FileDTO;
import common.FileServer;
import common.Notification;
import java.util.Scanner;
import java.rmi.*;

public class Terminal {

  private Communicator communicator;
  private Notification notification;
  private Scanner in;

  public Terminal(Notification notification) {
    this.notification = notification;
    //new Thread(com).start();
  }

  public void start() {
    try {
      FileServer fileServer = (FileServer) Naming.lookup(FileServer.FILESERVER_NAME);

      this.communicator = new Communicator(fileServer);
      in = new Scanner(System.in);
      while (true) {
        handleCommand(in.nextLine());
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleCommand(String command) {
    switch (command) {
      case "NEW":
        String file = inputFile();
        newFile(file);
        break;
      case "GET":
        getFile(inputFilename());
        break;
      case "LIST":
        listFiles();
        break;
      case "LOGIN":
        login(inputCredentials());
        break;
      case "LOGOUT":
        logout();
        break;
      case "REGISTER":
        register(inputCredentials());
        break;

      default:
        break;
    }
  }

  private void register(String credentials) {
    try {
      communicator.register(credentials, this.notification);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listFiles(){
    try {
      FileDTO[] files = communicator.listFiles();
      if(files == null){
        System.out.println("LOGIN first");
        return;
      }
      for(FileDTO file : files){
        System.out.println("name: " + file.getName() +  " size: " + file.getSize() + " owner: " + file.getOwner());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getFile(String filename) {
    try {
      FileDTO gFile = communicator.getFile(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void login(String credentials) {
    try {
      communicator.login(credentials, notification);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void logout() {
    try {
      communicator.logout();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void newFile(String file) {
    try {
      communicator.newFile(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String inputFilename() {
    System.out.println("File name:");
    return in.nextLine();
  }

  private String inputFile() {
    System.out.println("File name:");
    String filename = in.nextLine();
    System.out.println("File Size:");
    String size = in.nextLine();
    return filename + ":" + size;
  }

  private String inputCredentials() {
    System.out.println("Username:");
    String username = in.nextLine();
    System.out.println("Password:");
    String password = in.nextLine();
    return username + ":" + password;
  }

}
