package com;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.attributes.XmlTag;
import com.spencerwi.either.Either;

import org.dom4j.DocumentHelper;
import org.javatuples.Pair;

//@formatter:off
public class XMLTree {
  private static final HashMap<Class, List<Method>> methodCache = new HashMap();
  private  static final HashMap<Class, List<Field>> fieldCache = new HashMap();

  public static Element computeTree(Object o) {
    return Element.createElement(
      o.getClass().getName(),
      new HashMap<>(),
      process(o)
    );
  }

  public static org.dom4j.Element toDom(Element xmlTreeRoot, org.dom4j.Element root) {
    Either<String, List<Element>> children = xmlTreeRoot.getChildren();
    children
      .fold(
        left -> {
          root.setText(left);
          return null;
        },
        right -> {
          right.stream()
            .forEach(element -> {
              Either<String, List<Element>> child = element.getChildren();
              org.dom4j.Element domElement = DocumentHelper.createElement(element.getTag());
              if (child.getClass() == Either.Left.class) {
                domElement.setText(child.getLeft());
              } else {
                child.getRight()
                  .stream()
                  .forEach((nestedElement) -> {
                    domElement.add(toDom(nestedElement));
                  });
              }
              root.add(domElement);
            });
          return null;
        }
      );
    return root;
  }

  public static org.dom4j.Element toDom(Element xmlTreeRoot) {
    org.dom4j.Document document = DocumentHelper.createDocument();
    org.dom4j.Element root = document.addElement(xmlTreeRoot.getTag());
    return toDom(xmlTreeRoot, root);
  }

  public static String toString(Element xmlTreeRoot) {
    org.dom4j.Element root = toDom(xmlTreeRoot);
    return root.asXML();
  }

  public static String getXmlTagName(Object instance, Either<Field, Method> from) throws Exception {
    // TODO: check for dublicate tag
    Pair<String, Annotation> folded = from
      .fold(field -> Pair.with(
          field.getName(),
          field.getAnnotation(XmlTag.class)
        ), method -> Pair.with(
          method.getName().replaceFirst("^get", "").toLowerCase(),
          method.getAnnotation(XmlTag.class)
        )
      );
    String namedTag = "";
    if (folded.getValue1() instanceof XmlTag) { 
      XmlTag xmlTag = (XmlTag)(folded.getValue1());
      namedTag = xmlTag.name();
    }
    return Optional
      .ofNullable(namedTag)
      .filter(t -> t.length() > 0)
      .orElseGet(() -> folded.getValue0());
  }

  private static Boolean isPrimitive(Class<?> clazz) {
    return clazz == (Integer.class) || clazz == (String.class);
  }

  public static Either<String, List<Element>> process(Object instance) {
    if (isPrimitive(instance.getClass())) {
      return Either.left(instance.toString());
    }

    List<Method> methods = methodCache.computeIfAbsent(instance.getClass(),
        (clazz) -> Arrays.asList(clazz.getDeclaredMethods()));
    List<Field> fields = fieldCache.computeIfAbsent(instance.getClass(),
        (clazz) -> Arrays.asList(clazz.getDeclaredFields()));

    List<Element> fieldElements = fields
      .stream()
      .filter(field -> {
        Boolean annotation = field.isAnnotationPresent(XmlTag.class);
        return annotation;
      })
      .map(field -> {
        String tag = "";
        Either<String, List<Element>> children = null;
        field.setAccessible(true);
        try {
          tag = getXmlTagName(instance, Either.left(field));
          Object fieldInstance = field.get(instance);
          children = process(fieldInstance);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return Element.createElement(tag, new HashMap<>(), children);
      })
      .collect(Collectors.toList());

    List<Element> methodElements = methods
      .stream()
      .filter(method -> {
        Boolean zeroParams = method.getParameterTypes().length == 0;
        Boolean notVoid = !method.getReturnType().equals(Void.TYPE);
        Boolean annotation = method.isAnnotationPresent(XmlTag.class);
        return zeroParams && notVoid && annotation;
      })
      .map(method -> {
        String tag = "";
        String children = "";
        try {
          tag = getXmlTagName(instance, Either.right(method));
          // TODO: if returned is class
          children = method.invoke(instance).toString();
        } catch (Exception e) {
          e.printStackTrace();
        }
        return Element.createElement(tag, new HashMap<>(), Either.left(children));
      })
      .collect(Collectors.toList());

    List<Element> elements = Stream
      .of(fieldElements, methodElements)
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
	  return elements.size() > 0 ? Either.right(elements) :  Either.left("");
  }
}