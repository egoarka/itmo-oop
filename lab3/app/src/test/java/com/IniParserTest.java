package com;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Test;

public class IniParserTest {
  String projectDir = Paths.get("").toFile().getAbsolutePath();
  String filePath = String.join("/", projectDir, "input.ini");

  @Test
  public void simpleTest() {
    IniParser p = IniParser.fromFile(filePath);
    assertTrue(p.get("COMMON", "DiskCachePath").equals("/sata/panorama"));
    assertTrue(p.get("LEGACY_XML", "ListenTcpPort").equals("1976"));
    assertTrue(p.get("DEBUG", "PlentySockMaxQSize").equals("126"));
  }
}
