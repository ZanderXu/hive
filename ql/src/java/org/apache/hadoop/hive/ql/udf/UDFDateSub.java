/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;


public class UDFDateSub extends UDF {

  private static Log LOG = LogFactory.getLog(UDFDateSub.class.getName());

  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

  public UDFDateSub() {
  }

  /**
   * Subtract a number of days to the date. 
   * The time part of the string will be ignored.
   * 
   * NOTE: This is a subset of what MySQL offers as:
   * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date-sub
   * 
   * @param date1 the date string in the format of "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd".
   * @return the date in the format of "yyyy-MM-dd".
   */
  public String evaluate(String dateString1, int days)  {
    
    try {
      calendar.setTime(formatter.parse(dateString1));
      calendar.add(Calendar.DAY_OF_MONTH, -days);
      Date newDate = calendar.getTime();
      return formatter.format(newDate);
    } catch (ParseException e) {
      return null;
    }
  }

}
