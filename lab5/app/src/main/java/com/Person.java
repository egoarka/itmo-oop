package com;

import com.attributes.XmlObject;
import com.attributes.XmlTag;

@XmlObject
public class Person {

  // @XmlTag(name = "fullname")
  // private final String name;

  // @XmlAttribute(tag = "fullname")
  // private final String lang;
  @XmlTag(name = "lmao")
  private final int foo = 30;
  @XmlTag
  private final int bar = 1;

  // public Person(String name, String lang, int age) {
  // this.name = name;
  // this.lang = lang;
  // this.age = age;
  // }

  // public String getName() {
  // return name;
  // }

  // public int getAge() {
  // return 1;
  // }

  @XmlTag
  public String getName() {
    return "si";
  }

  @XmlTag
  public String asdasd() {
    return "123213";
  }

  @XmlTag
  public void lalla() {
  }
}