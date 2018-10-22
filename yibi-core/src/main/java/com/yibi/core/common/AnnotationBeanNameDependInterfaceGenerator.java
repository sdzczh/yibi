package com.yibi.core.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/15 version: 1.0
 */
public class AnnotationBeanNameDependInterfaceGenerator extends AnnotationBeanNameGenerator {
  @Override
  protected String buildDefaultBeanName(BeanDefinition definition) {
    if (definition instanceof ScannedGenericBeanDefinition) {
      ScannedGenericBeanDefinition scannedGenericBeanDefinition =
          (ScannedGenericBeanDefinition) definition;
      AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
      String[] interfaceNames = metadata.getInterfaceNames();
      if (interfaceNames == null) {
      //throw new RuntimeException("interface not found");
        throw new RuntimeException("interface not found");
      }
      if (interfaceNames.length == 1) {
        String shortClassName = ClassUtils.getShortName(interfaceNames[0]);
        return Introspector.decapitalize(shortClassName);
      }
      throw new RuntimeException("too many interfaces found");
    }
    throw new RuntimeException("no support BeanDefinition");

  }
}
