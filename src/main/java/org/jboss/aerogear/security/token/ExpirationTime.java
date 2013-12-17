/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.security.token;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Utility class for Token based checks based on expiration time.
 */
public class ExpirationTime {

    private Calendar current;
    private Calendar future;


    public ExpirationTime() {
        current = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        future = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public long getCurrentTime() {
        return current.getTimeInMillis();
    }

    public long add(int hours) {
        future.add(Calendar.HOUR_OF_DAY, hours);
        return future.getTimeInMillis();
    }

    public boolean isExpired(long time) {
        future.setTimeInMillis(time);
        return current.after(future);
    }

}

