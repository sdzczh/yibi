package com.yibi.core.common;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.*;
import org.springframework.util.StringValueResolver;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/11 version: 1.0
 */
@Slf4j
public class CustomPropertyPlaceHoderConfigurer extends PropertyPlaceholderConfigurer
    implements InitializingBean {

  private String beanName;

  private BeanFactory beanFactory;

  private Set<String> secretKeys;

  private Set<String> propertyPlaceClasses;

  public void setPropertyPlaceClasses(Set<Class> propertyPlaceClasses) {
    Set<String> classes = new HashSet<>();
    for (Class cls : propertyPlaceClasses) {
      classes.add(cls.getCanonicalName());
    }
    this.propertyPlaceClasses = classes;
  }

  @Override
  protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
      StringValueResolver valueResolver) {
    BeanDefinitionVisitor visitor =
        new CustomerClassBeanDefinitionVisitor(propertyPlaceClasses, valueResolver);

    String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
    for (String curName : beanNames) {
      // Check that we're not parsing our own bean definition,
      // to avoid failing on unresolvable placeholders in properties file locations.
      if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
        BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
        try {
          visitor.visitBeanDefinition(bd);
        } catch (Exception ex) {
          throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName,
              ex.getMessage(), ex);
        }
      }
    }

    // New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
    beanFactoryToProcess.resolveAliases(valueResolver);

    // New in Spring 3.0: resolve placeholders in embedded values such as annotation attributes.
    beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
  }

  @Override
  protected void convertProperties(Properties props) {
    for (String key : secretKeys) {
      String value = props.getProperty(key);
      if (StringUtils.isNotBlank(value)) {
        String newValue = null;
        try {
          newValue = decrypt(key, value);
        } catch (final Exception e) {
          throw new RuntimeException("[key=" + key + ",value="
              + value + "] decrypt fail", e);
        }
        props.put(key, newValue);
      }
    }
    super.convertProperties(props);
  }

  private byte[] des(int mode, String key, byte[] val)
      throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException,
      InvalidKeySpecException, NoSuchPaddingException, BadPaddingException,
      IllegalBlockSizeException {
    String algo = "DES";
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algo);
    byte[] encBytes = key.getBytes(StandardCharsets.UTF_8);
    DESKeySpec keySpecEncrypt = new DESKeySpec(encBytes);
    SecretKey keyEncrypt = keyFactory.generateSecret(keySpecEncrypt);
    Cipher cipherEncrypt = Cipher.getInstance(algo);

    cipherEncrypt.init(mode, keyEncrypt);
    return cipherEncrypt.doFinal(val);
  }

  protected String decrypt(String key, String val) throws Exception {
    return new String(des(Cipher.DECRYPT_MODE, key, DatatypeConverter.parseHexBinary(val)));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (secretKeys == null) {
      secretKeys = Collections.emptySet();
    }
  }

  protected String encrypt(String key, String value) throws Exception {
    return DatatypeConverter.printHexBinary(
        des(Cipher.ENCRYPT_MODE, key, value.getBytes(StandardCharsets.UTF_8)));
  }

  private static class CustomerClassBeanDefinitionVisitor extends BeanDefinitionVisitor {
    private Set<String> supportClasses;
    private Set resolvedBean = new HashSet();
    private boolean isAccessJavaBean;

    CustomerClassBeanDefinitionVisitor(Set<String> supportClasses,
        StringValueResolver valueResolver) {
      super(valueResolver);
      if (supportClasses == null) {
        supportClasses = Collections.emptySet();
      }
      this.supportClasses = supportClasses;
    }

    private boolean isWrapClass(Class clz) {
      try {
        return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
      } catch (Exception e) {
        return false;
      }
    }

    private void visitJavaBean(Object bean) {
      if (bean == null) {
        return;
      }
      if (resolvedBean.contains(bean)) {
        return;
      }
      Class cls = bean.getClass();
      if (cls.isPrimitive()) {
        return;
      }
      if (isWrapClass(cls)) {
        return;
      }
      Field[] allField = cls.getDeclaredFields();
      for (Field field : allField) {
        if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        if (!field.isAccessible()) {
          field.setAccessible(true);
        }
        try {
          Object fieldValue = field.get(bean);
          Object newFieldValue = resolveValue(fieldValue);
          field.set(bean, newFieldValue);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      resolvedBean.add(bean);
    }

    @Override
    public void visitBeanDefinition(BeanDefinition beanDefinition) {
      if (supportClasses.contains(beanDefinition.getBeanClassName())) {
        isAccessJavaBean = true;
      }
      super.visitBeanDefinition(beanDefinition);
    }

    @Override
    protected Object resolveValue(Object value) {
      if (value instanceof BeanDefinition) {
        visitBeanDefinition((BeanDefinition) value);
      } else if (value instanceof BeanDefinitionHolder) {
        visitBeanDefinition(((BeanDefinitionHolder) value).getBeanDefinition());
      } else if (value instanceof RuntimeBeanReference) {
        RuntimeBeanReference ref = (RuntimeBeanReference) value;
        String newBeanName = resolveStringValue(ref.getBeanName());
        if (!newBeanName.equals(ref.getBeanName())) {
          return new RuntimeBeanReference(newBeanName);
        }
      } else if (value instanceof RuntimeBeanNameReference) {
        RuntimeBeanNameReference ref = (RuntimeBeanNameReference) value;
        String newBeanName = resolveStringValue(ref.getBeanName());
        if (!newBeanName.equals(ref.getBeanName())) {
          return new RuntimeBeanNameReference(newBeanName);
        }
      } else if (value instanceof Object[]) {
        visitArray((Object[]) value);
      } else if (value instanceof List) {
        visitList((List) value);
      } else if (value instanceof Set) {
        visitSet((Set) value);
      } else if (value instanceof Map) {
        visitMap((Map) value);
      } else if (value instanceof TypedStringValue) {
        TypedStringValue typedStringValue = (TypedStringValue) value;
        String stringValue = typedStringValue.getValue();
        if (stringValue != null) {
          String visitedString = resolveStringValue(stringValue);
          typedStringValue.setValue(visitedString);
        }
      } else if (value instanceof String) {
        return resolveStringValue((String) value);
      } else if (isAccessJavaBean) {
        visitJavaBean(value);
      }
      return value;
    }
  }

  @Override
  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public void setSecretKeys(Set<String> secretKeys) {
    this.secretKeys = secretKeys;
  }
}
