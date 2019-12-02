package client.controller;

import common.FileDTO;
import common.FileServer;
import common.Notification;
import java.rmi.RemoteException;

public class Communicator {

  FileServer fileServer;
  FileDownloader downloader;
  FileUploader uploader;

  public Communicator(FileServer fileServer){
    this.fileServer = fileServer;
    this.downloader = new FileDownloader();
    this.uploader = new FileUploader();
  }

  public FileDTO[] listFiles(String credentials, Notification notification) throws RemoteException{
    FileDTO[] files = fileServer.listFiles(credentials, notification);
    return files;
  }

  public FileDTO getFile(String filename, String credentials, Notification notification){
    try{
    FileDTO gFile = fileServer.getFile(filename, credentials, notification);
    int port = fileServer.sendFileToClient(filename);
    downloader.downloadFile(port, filename);
    return gFile;
    }catch(Exception e){

    }
    return null;
  }

  public void newFile(String file, String credentials, Notification notification) throws RemoteException{
    fileServer.newFile(file, credentials, notification);
    String filename = file.split(":")[0];
    uploader.setFilename(filename);
    new Thread(uploader).start();
    fileServer.sendFileToServer(FileUploader.PORT, filename);
  }

  public void register(String credentials, Notification notification) throws RemoteException {
    fileServer.register(credentials, notification);
  }

  public void deleteFile(String credentials, String filename, Notification notification) throws RemoteException {
    fileServer.deleteFile(credentials, filename, notification);
  }

}
