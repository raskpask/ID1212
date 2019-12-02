package server.model;

import common.FileDTO;

public class File implements FileDTO {

  private String name;
  private int size;
  private String owner;
  private boolean writable;

  public File(String name, int size, String owner, boolean writable){
    this.name = name;
    this.size = size;
    this.owner = owner;
    this.writable = writable;
  }

  public int getSize() {
    return this.size;
  }

  @Override
  public boolean isWritable() {
    return this.writable;
  }

  public String getOwner() {
    return this.owner;
  }

  public String getName() {
    return this.name;
  }

  public String toString(){
    return "File: " + this.name + " Size: " + this.size + " Owner: " + this.owner + " Writeable: " + this.writable;
  }

}
