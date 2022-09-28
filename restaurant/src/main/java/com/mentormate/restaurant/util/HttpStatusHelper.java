package com.mentormate.restaurant.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpStatusHelper {

    public static final String NOT_FOUND_EXCEPTION_ID =
            "%s object with Id: %d cannot be found!";

    public static final String BAD_REQUEST_EXCEPTION_PAGE =
            "There is not such page! Last page is %d!";

    public static final String BAD_REQUEST_EXCEPTION_DATE =
            "%s objects cannot be found because of incorrect date format!";

    public static final String BAD_REQUEST_EXCEPTION_CREATE =
            "%s object cannot be created, request body must be the right format!";

    public static final String BAD_REQUEST_EXCEPTION_UPDATE =
            "%s object with Id: %d cannot be updated, request body must be the right format!";
}
