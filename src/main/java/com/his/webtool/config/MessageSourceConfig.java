package com.his.webtool.config;

import java.time.Duration;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;


/**
 * Yaml形式のメッセージプロパティからのメッセージ取得クラス
 * application.ymlにて設定
 * spring.messages
 * basename:メッセージ用yamlファイルの読み取り先ファイルパス
 * encoding:エンコーディング
 */
@Configuration
public class MessageSourceConfig {

  /**
   * MessageSourcePropertiesを生成します。
   *
   * @return MessageSourceProperties
   */
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.messages")
  public MessageSourceProperties messageSourceProperties() {
    return new MessageSourceProperties();
  }

  /**
   * MessageSourcePropertiesに従って、MessageSourceを生成します。
   * 
   * @param properties メッセージソースのプロパティ
   * @return MessageSource
   */
  @Bean("messageSource")
  public MessageSource messageSource(MessageSourceProperties properties) {
    YamlMessageSource messageSource = new YamlMessageSource();
    if (StringUtils.hasText(properties.getBasename())) {
      messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(
        StringUtils.trimAllWhitespace(properties.getBasename())));
    }
    if (properties.getEncoding() != null) {
      messageSource.setDefaultEncoding(properties.getEncoding().name());
    }
    messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
    Duration cacheDuration = properties.getCacheDuration();
    if (cacheDuration != null) {
      messageSource.setCacheMillis(cacheDuration.toMillis());
    }
    messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
    messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
    return messageSource;
  }

  /**
   * Yaml形式のプロパティからResourceBundleを生成するクラス
   */
  private static class YamlMessageSource extends ResourceBundleMessageSource {
    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) {
      return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
    }
  }
}
