package com.mewcom.backend.rest.web.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

  private static final SimpleDateFormat DATE_ONLY_FORMATTER =
      new SimpleDateFormat("yyyy-MM-dd");

  public String toDateOnlyFormat(Date date) {
    return DATE_ONLY_FORMATTER.format(date);
  }
}
