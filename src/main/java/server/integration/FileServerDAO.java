package server.integration;

import common.FileDTO;
import common.Notification;
import common.UserDTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Base64;
import server.model.File;
import server.model.SerializeableNotification;

public class FileServerDAO {

  private static final String USER_TABLE_NAME = "USER";
  private static final String USERNAME_COLUMN_NAME = "USERNAME";
  private static final String PASSWORD_COLUMN_NAME = "PASSWORD";

  private static final String FILE_TABLE_NAME = "FILE";
  private static final String SIZE_COLUMN_NAME = "SIZE";
  private static final String OWNER_COLUMN_NAME = "OWNER";
  private static final String FILENAME_COLUMN_NAME = "NAME";
  private static final String WRITEABLE_COLUMN_NAME = "WRITEABLE";
  private static final String NOTIFICATION_COLUMN_NAME = "NOTIFICATION";


  private PreparedStatement newFileStatement;
  private PreparedStatement getFileStatement;
  private PreparedStatement deleteFileStatement;
  private PreparedStatement listFilesStatement;
  private PreparedStatement countFilesStatement;
  private PreparedStatement newUserStatement;
  private PreparedStatement loginStatement;
  private PreparedStatement setNotificationStatement;
  private PreparedStatement getNotificationStatement;

  public FileServerDAO(String datasource, String dbms) {
    try {
      Connection connection = createDatasource(dbms, datasource);
      prepareStatements(connection);
    } catch (Exception e) {
      System.err.println("Could not connecto to db");
      e.printStackTrace();
    }
  }

  private Connection createDatasource(String dbms, String datasource)
      throws SQLException, ClassNotFoundException {
    Connection connection = connectToFileServerDB(dbms, datasource);
    if (!fileTableExists(connection)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE " + FILE_TABLE_NAME + " (" + FILENAME_COLUMN_NAME +
          " VARCHAR(32) PRIMARY KEY, " + SIZE_COLUMN_NAME + " FLOAT, " + OWNER_COLUMN_NAME
          + " VARCHAR(32), " + WRITEABLE_COLUMN_NAME + " VARCHAR(32))");
    }
    if (!userTableExists(connection)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE " + USER_TABLE_NAME + " (" + USERNAME_COLUMN_NAME +
          " VARCHAR(32) PRIMARY KEY, " + PASSWORD_COLUMN_NAME + " VARCHAR(32), "
          + NOTIFICATION_COLUMN_NAME + " VARCHAR(1024))");
    }
    return connection;
  }

  private boolean fileTableExists(Connection connection) throws SQLException {
    int tableNameColumn = 3;
    DatabaseMetaData dbm = connection.getMetaData();
    try (ResultSet rs = dbm.getTables(null, null, null, null)) {
      for (; rs.next(); ) {
        if (rs.getString(tableNameColumn).equals(FILE_TABLE_NAME)) {
          return true;
        }
      }
      return false;
    }
  }

  private boolean userTableExists(Connection connection) throws SQLException {
    int tableNameColumn = 3;
    DatabaseMetaData dbm = connection.getMetaData();
    try (ResultSet rs = dbm.getTables(null, null, null, null)) {
      for (; rs.next(); ) {
        if (rs.getString(tableNameColumn).equals(USER_TABLE_NAME)) {
          return true;
        }
      }
      return false;
    }
  }

  private Connection connectToFileServerDB(String dbms, String datasource)
      throws SQLException, ClassNotFoundException {
    if (dbms.equalsIgnoreCase("derby")) {
      Class.forName("org.apache.derby.jdbc.clientXADataSource");
      return DriverManager
          .getConnection("jdbc:derby://localhost:1337/" + datasource + ";create=true");
    } else if (dbms.equalsIgnoreCase("mysql")) {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager
          .getConnection("jdbc:mysql://localhost:3306/fileserver", "root", "rootroot");
    } else {
      throw new SQLException("Unable to create datasource, uknown dbms");
    }
  }

  public void newFile(FileDTO file) {
    try {
      newFileStatement.setString(1, file.getName());
      newFileStatement.setString(2, Integer.toString(file.getSize()));
      newFileStatement.setString(3, file.getOwner());
      newFileStatement.setString(4, Boolean.toString(file.isWritable()));

      int rows = newFileStatement.executeUpdate();
      if (rows != 1) {
        throw new Exception("Did not update row m8");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public FileDTO getFile(String filename) {
    File gFile = null;
    try {
      getFileStatement.setString(1, filename);
      ResultSet rs = getFileStatement.executeQuery();
      rs.next();
      gFile = extractFile(rs);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gFile;
  }

  public FileDTO[] listFiles() {
    File[] files = null;
    try {
      ResultSet countRs = countFilesStatement.executeQuery();
      int numberOfFiles = 0;
      if (countRs != null) {
        countRs.next();
        numberOfFiles = countRs.getInt(1);
      }
      ResultSet rs = listFilesStatement.executeQuery();
      files = new File[numberOfFiles];
      for (int i = 0; rs.next(); i++) {
        files[i] = extractFile(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return files;
  }

  private File extractFile(ResultSet rs) throws SQLException {
    String gFilename = rs.getString(1);
    int gFilesize = rs.getInt(2);
    String gFileowner = rs.getString(3);
    boolean writeable = rs.getBoolean(4);
    return new File(gFilename, gFilesize, gFileowner, writeable);
  }

  public UserDTO login(UserDTO user, Notification notification) {
    try {

      loginStatement.setString(1, user.getUsername());
      loginStatement.setString(2, user.getPassword());
      ResultSet rs = loginStatement.executeQuery();
      if (rs.next()) {
        setNotificationStatement.setString(1, new SerializeableNotification(notification).toString());
        setNotificationStatement.setString(2, user.getUsername());
        int rows = setNotificationStatement.executeUpdate();
        if(rows != 1){
          return null;
        }
        return user;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public void register(UserDTO user, Notification notification) {
    try {
      String serializedNotification = new SerializeableNotification(notification).toString();
      newUserStatement.setString(1, user.getUsername());
      newUserStatement.setString(2, user.getPassword());
      newUserStatement.setString(3, serializedNotification);

      int rows = newUserStatement.executeUpdate();
      if (rows != 1) {
        throw new Exception("Did not update row m8");
      }
    } catch (SQLIntegrityConstraintViolationException sqle) {
      System.err.println("new user: Username already exists");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteFile(String filename) {
    try {
      deleteFileStatement.setString(1, filename);
      int rows = deleteFileStatement.executeUpdate();
      if(rows != 1){
        throw new Exception("Failed to delete file from DB");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public Object getNotification(String owner) {
    String notificationString = null;
    try {
      getNotificationStatement.setString(1, owner);
      ResultSet rs = getNotificationStatement.executeQuery();
      if (rs.next()) {
        notificationString = rs.getString(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return SerializeableNotification.fromString(notificationString).getNotification();
  }

  private void prepareStatements(Connection connection) throws SQLException {
    newFileStatement = connection
        .prepareStatement("INSERT INTO " + FILE_TABLE_NAME + " VALUES (?, ?, ?, ?)");

    deleteFileStatement = connection.prepareStatement("DELETE FROM " + FILE_TABLE_NAME + " WHERE " + FILENAME_COLUMN_NAME + " = ?");

    getFileStatement = connection
        .prepareStatement(
            "SELECT * FROM " + FILE_TABLE_NAME + " WHERE " + FILENAME_COLUMN_NAME + " = (?)");

    listFilesStatement = connection.prepareStatement("SELECT * FROM " + FILE_TABLE_NAME);

    countFilesStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + FILE_TABLE_NAME);

    newUserStatement = connection
        .prepareStatement("INSERT INTO " + USER_TABLE_NAME + " VALUES (?, ?, ?)");

    loginStatement = connection.prepareStatement(
        "SELECT " + USERNAME_COLUMN_NAME + " FROM " + USER_TABLE_NAME + " WHERE EXISTS "
            + "(SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_COLUMN_NAME + " = (?) AND "
            + PASSWORD_COLUMN_NAME + " = (?))");

    setNotificationStatement = connection.prepareStatement("UPDATE " + USER_TABLE_NAME + " SET " + NOTIFICATION_COLUMN_NAME + " = (?) WHERE " + USERNAME_COLUMN_NAME + " = (?)");

    getNotificationStatement = connection.prepareStatement(
        "SELECT " + NOTIFICATION_COLUMN_NAME + " FROM " + USER_TABLE_NAME + " WHERE "
            + USERNAME_COLUMN_NAME + " = (?)");
  }

}
