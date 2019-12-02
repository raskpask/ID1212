package client.view;

import client.controller.Communicator;
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
  }

  public void start() {
    try {
      FileServer fileServer = (FileServer) Naming.lookup(FileServer.FILESERVER_NAME);

      this.communicator = new Communicator(fileServer);
      in = new Scanner(System.in);
      UserInterface.startupMessage();
      while (true) {
        handleCommand(in.nextLine());
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleCommand(String command) {
    switch (command) {
      case "HELP":
        UserInterface.startupMessage();
        break;
      case "NEW":
        newFile(inputCredentials(), inputFile());
        break;
      case "DELETE":
        deleteFile(inputCredentials(), inputFilename());
        break;
      case "GET":
        getFile(inputCredentials(), inputFilename());
        break;
      case "LIST":
        listFiles(inputCredentials());
        break;
      case "REGISTER":
        register(inputCredentials());
        break;
      default:
        break;
    }
  }

  private void deleteFile(String credentials, String filename) {
    try {
      communicator.deleteFile(credentials, filename, this.notification);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void register(String credentials) {
    try {
      communicator.register(credentials, this.notification);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listFiles(String credentials) {
    try {
      FileDTO[] files = communicator.listFiles(credentials, notification);
      if (files == null) {
        System.out.println("LOGIN first");
        return;
      }
      for (FileDTO file : files) {
        System.out.println(file.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getFile(String credentials, String filename) {
    try {
      FileDTO gFile = communicator.getFile(filename, credentials, notification);
      System.out.println(gFile.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void newFile(String credentials, String file) {
    try {
      communicator.newFile(file, credentials, notification);
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
    System.out.println("Is writeable?:");
    String writable = in.nextLine().toLowerCase();
    boolean isWriteable = writable.equals("yes");
    return filename + ":" + size + ":" + isWriteable;
  }

  private String inputCredentials() {
    System.out.println("Username:");
    String username = in.nextLine();
    System.out.println("Password:");
    String password = in.nextLine();
    return username + ":" + password;
  }

}
