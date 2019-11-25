package server.model;

import common.Notification;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Base64;

public class SerializeableNotification implements Serializable {

  private Notification notification;

  public SerializeableNotification(Notification notification) {
    this.notification = notification;
  }

  public Notification getNotification() {
    return this.notification;
  }

  public String toString() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = null;
      oos = new ObjectOutputStream(baos);
      oos.writeObject(this);
      oos.close();
      return Base64.getEncoder().encodeToString(baos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static SerializeableNotification fromString(String serializedNotification) {
    try {
      byte[] data = Base64.getDecoder().decode(serializedNotification);
      ObjectInputStream ois = null;
      ois = new ObjectInputStream(new ByteArrayInputStream(data));
      SerializeableNotification back = (SerializeableNotification) ois.readObject();
      ois.close();
      return back;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
