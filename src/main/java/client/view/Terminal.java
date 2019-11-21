package client.view;

import common.FileServer;
import java.util.Scanner;
import java.rmi.*;

public class Terminal {

  private Communicator communicator;
  private Scanner in;

  public Terminal() {
    //new Thread(com).start();
  }

  public void start() {
    try {
      String[] hej = Naming.list(FileServer.FILESERVER_NAME);
      FileServer fileServer = (FileServer) Naming.lookup(FileServer.FILESERVER_NAME);
      //FileServer fileServer = null;
      //Remote r = Naming.lookup(FileServer.FILESERVER_NAME);

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
        String credentials = inputCredentials();
        String file = inputFile();
        newFile(credentials, file);
        break;
      case "GET":
        break;
      case "LIST":
        break;
      case "LOGIN":
        login(inputCredentials());
        break;
      case "LOGOUT":
        break;
      case "REGISTER":
        break;

      default:
        break;
    }
  }

  private void login(String credentials){
    try{
      communicator.login(credentials);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void newFile(String credentials, String file) {
    try {
      communicator.newFile(credentials, file);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
