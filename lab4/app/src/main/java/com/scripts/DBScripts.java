package com.scripts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.sql.Connection;

import com.Config;
import com.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DBScripts {

  private static Path dbScriptsDir = Config.getResourceDir()
      .resolve("db-scripts");

  private static void exec(String scriptPath) {
    try {
      Connection connect = DataSource.getConnection();
      Reader reader = new BufferedReader(new FileReader(scriptPath));

      ScriptRunner sr = new ScriptRunner(connect);
      sr.runScript(reader);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void dropTables() {
    String scriptPath = dbScriptsDir.resolve("drop-tables.sql").toString();
    exec(scriptPath);
  }

  public static void createTables() {
    String scriptPath = dbScriptsDir.resolve("create-tables.sql").toString();
    exec(scriptPath);
  }

  public static void fillDBDao() {
    String scriptPath = dbScriptsDir.resolve("fill-db-dao.sql").toString();
    exec(scriptPath);
  }

  public static void fillDBServices() {
    String scriptPath = dbScriptsDir.resolve("fill-db-services.sql").toString();
    exec(scriptPath);
  }

  public static void deleteTablesRows() {
    String scriptPath = dbScriptsDir.resolve("delete-tables-rows.sql")
        .toString();
    exec(scriptPath);
  }
}