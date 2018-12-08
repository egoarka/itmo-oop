package com;

import java.util.HashMap;
import java.util.List;

import com.spencerwi.either.Either;

public class Element {
  private String tag;
  private HashMap<String, String> attributes;
  private Either<String, List<Element>> children;

  public String getTag() {
    return this.tag;
  }

  public Either<String, List<Element>> getChildren() {
    return this.children;
  }

  private Element(String tag, HashMap<String, String> attributes,
      Either<String, List<Element>> children) {
    this.tag = tag;
    this.attributes = attributes;
    this.children = children;
  }

  public static Element createElement(String tag,
      HashMap<String, String> attributes,
      Either<String, List<Element>> children) {
    return new Element(tag, attributes, children);
  }

  public static Element empty() {
    return createElement("", new HashMap<>(), Either.left(""));
  }
}