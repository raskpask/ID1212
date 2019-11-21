package common;

import java.io.Serializable;

public interface FileDTO extends Serializable {

  public int getSize();
  public String getOwner();

}
