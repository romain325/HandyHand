package DataBase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnexion {

    private static Properties getProperties(){
        var props = new Properties();

        try{
            var bf = Files.newBufferedReader(Paths.get("src/main/resources/db/database.properties"), StandardCharsets.UTF_8);
            props.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private Connection connection;

    public static DataBaseConnexion dataBaseConnectionFactory(boolean isDebug) throws SQLException {
        var db = new DataBaseConnexion();
        var props = getProperties();
        db.connection = DriverManager.getConnection(isDebug ? props.getProperty("db.dsnLocal") : props.getProperty("db.dsn"),
                props.getProperty("db.username"), props.getProperty("db.password"));
        return db;
    }

    public static DataBaseConnexion dataBaseConnectionFactory() throws SQLException {
        return DataBaseConnexion.dataBaseConnectionFactory(false);
    }

    //todo CHANGE TO SECURE VERSION
    public ResultSet executeQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

}
