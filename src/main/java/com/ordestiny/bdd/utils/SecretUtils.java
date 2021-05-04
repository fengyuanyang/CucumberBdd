package com.ordestiny.bdd.utils;

import com.ordestiny.bdd.provider.ConfigReader;
import org.jasypt.util.text.BasicTextEncryptor;

public class SecretUtils {
  public static String getDecryptedPassword(String password) {
    String jasyptPassword = ConfigReader.getInstance().getJasyptPassword();
    System.out.println(jasyptPassword);
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPassword(jasyptPassword);
    return textEncryptor.decrypt(password);
  }
}
