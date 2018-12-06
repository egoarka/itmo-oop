package com;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class DataSource {
  private static HikariDataSource ds;

  static {
    Dotenv env = Config.getEnv();
    //@formatter:off
    String url = String.format("jdbc:%s://%s:%s;database=%s", 
		  env.get("DB_CONNECTION"),
		  env.get("DB_HOST"), 
		  env.get("DB_PORT"), 
  		env.get("DB_DATABASE")
    );
    //@formatter:on
    ds = new HikariDataSource();
    ds.setJdbcUrl(url);
    ds.setUsername(env.get("DB_USERNAME"));
    ds.setPassword(env.get("DB_PASSWORD"));
  }

  private DataSource() {
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}