package com.mewcom.backend.rest.web.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateUtil {

  private static final SimpleDateFormat DATE_ONLY_FORMATTER =
      new SimpleDateFormat("yyyy-MM-dd");

  private static final long ONE_SECOND_IN_MILLIS = 1000;

  public static String toDateOnlyFormat(Date date) {
    return DATE_ONLY_FORMATTER.format(date);
  }

  public static Date getDateNow() {
    return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public static Date getTimeWithAddedSeconds(int seconds) {
    return new Date(new Date().getTime() + seconds * ONE_SECOND_IN_MILLIS);
  }

  public static Date getTimeNow() {
    return new Date(new Date().getTime());
  }
}
