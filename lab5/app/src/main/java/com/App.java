package com;

// Files.write(Paths.get("./output.xml"), s.getBytes());
public class App {

  public static void main(String[] args)
      throws NoSuchFieldException, SecurityException {
  //@formatter:off
    Person p = new Person();
    Element xmlTreeRoot = XMLTree.computeTree(p);
    String s = XMLTree.toString(xmlTreeRoot);
    System.out.println(s);
  }
}
