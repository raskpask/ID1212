package server.model;

import common.FileDTO;

public class File implements FileDTO {

  private String name;
  private int size;
  private String owner;

  public File(String name, int size, String owner){
    this.name = name;
    this.size = size;
    this.owner = owner;
  }

  public int getSize() {
    return this.size;
  }

  public String getOwner() {
    return this.owner;
  }

  public String getName() {
    return this.name;
  }

  public String toString(){
    return "File: " + this.name + " Size: " + this.size + " Owner: " + this.owner;
  }

}
