package server.model;

import common.UserDTO;

public class User implements UserDTO {

  private String username;
  private String password;

  public User(String username, String password){
    this.username = username;
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getPassword() {
    return this.password;
  }
}
