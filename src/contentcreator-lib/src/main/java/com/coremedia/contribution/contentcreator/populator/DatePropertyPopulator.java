package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple property populator that fills the property field with the given calendar value.
 * If no date is specified, the current date will be used.
 */
public class DatePropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Calendar calendarValue = Calendar.getInstance();

  public Calendar getCalendarValue() {
    return calendarValue;
  }

  public void setCalendarValue(Calendar calendarValue) {
    this.calendarValue = calendarValue;
  }

  public String getValue() {
    return dateFormat.format(getCalendarValue().getTime());
  }

  public void setValue(String value) throws ParseException {
    Date newDate = dateFormat.parse(value);
    this.calendarValue.setTime(newDate);
  }


  public void populateProperty(String propertyName, Content content) {
    populateDateProperty(propertyName, getCalendarValue(), content);
  }
}
