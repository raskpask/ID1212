package server.integration;

import common.FileDTO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileServerDAO {

  private static final String TABLE_NAME = "FILE";
  private static final String SIZE_COLUMN_NAME = "SIZE";
  private static final String OWNER_COLUMN_NAME = "NAME";


  private PreparedStatement newFileStatement;

  public FileServerDAO(String datasource, String dbms) {
    try {
      Connection connection = createDatasource(dbms, datasource);
      prepareStatements(connection);
    } catch (Exception e) {
      System.err.println("Could not connecto to db");
      e.printStackTrace();
    }
  }

  private Connection createDatasource(String dbms, String datasource) throws SQLException, ClassNotFoundException {
    Connection connection = connectToFileServerDB(dbms, datasource);
    if (!fileServerTableExists(connection)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE " + TABLE_NAME + " (" + OWNER_COLUMN_NAME +
          " VARCHAR(32) PRIMARY KEY, " + SIZE_COLUMN_NAME + " FLOAT)");
    }
    return connection;
  }

  private boolean fileServerTableExists(Connection connection) throws SQLException{
    int tableNameColumn = 3;
    DatabaseMetaData dbm = connection.getMetaData();
    try(ResultSet rs = dbm.getTables(null,null,null,null)){
      for(; rs.next();){
        if(rs.getString(tableNameColumn).equals(TABLE_NAME)){
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
    } else if(dbms.equalsIgnoreCase("mysql")) {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/fileserver", "root", "rootroot");
    }else {
      throw new SQLException("Unable to create datasource, uknown dbms");
    }
  }

  public void newFile(FileDTO file) {
    try {
      newFileStatement.setString(1, Integer.toString(file.getSize()));
      newFileStatement.setString(2, file.getOwner());
      int rows = newFileStatement.executeUpdate();
      if (rows != 1) {
        throw new Exception("Did not update row m8");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void prepareStatements(Connection connection) throws SQLException {
    newFileStatement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?)");
  }

}
