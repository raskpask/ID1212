package client.view;

public class UserInterface {

  public void serverMessage(String message) {

    String op = getOperation(message);
    String status = getStatus(message);

    switch (op) {
      case "NEW":
        if (status.equals("1")) {
          System.out.println("The file was created.");
        } else {
          System.out.println("The file was not created");
        }
        break;
      case "REGISTER":
        if (status.equals("1")) {
          System.out.println("The user was registered.");
        } else {
          System.out.println("The user was not registered, try another username");
        }
        break;
      default:
        System.out.println(message);
        break;
    }
  }

  public static void startupMessage() {
    System.out.println("AVAILABLE COMMANDS");
    System.out.println("REGISTER - Register a new user");
    System.out.println("NEW - Create new file");
    System.out.println("GET - Get a specific file");
    System.out.println("LIST - List all available files");

  }

  private String getOperation(String message) {
    return message.split(" ")[0];
  }

  private String getStatus(String message) {
    return message.split(" ")[1];
  }

}
