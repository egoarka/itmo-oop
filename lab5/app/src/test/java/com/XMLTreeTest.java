package com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class XMLTreeTest {
  @Test
  public void test() {
    Person p = new Person();
    Element xmlTreeRoot = XMLTree.computeTree(p);
    String actual = XMLTree.toString(xmlTreeRoot);
    String expected = "<com.Person><foo>30</foo><wtf><wtf-field>3333333</wtf-field></wtf></com.Person>";
    assertEquals(expected, actual);
  }
}
