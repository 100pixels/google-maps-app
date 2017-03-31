package com.example.ivansandoval.googlemaps.database;

import android.provider.BaseColumns;

public class PublicTransitContract {

        public static abstract class CompanyEntry implements BaseColumns {

            public static final String TABLE_NAME="agency";

            public static final String AGENCY_ID="agency_id";
            public static final String NAME="name";
            public static final String CITY="city";

        }

    public static abstract class RouteEntry implements BaseColumns {

        public static final String TABLE_NAME="route";

        public static final String ROUTE_ID="route_id";
        public static final String SHORT_NAME="short_name";
        public static final String LONG_NAME="long_name";
        public static final String COLOR="color";
        public static final String AGENCY_ID="agency_id";

    }

    public static abstract class JourneyEntry implements BaseColumns {

        public static final String TABLE_NAME="journey";

        public static final String JOURNEY_ID="journey_id";
        public static final String DIRECTION="direction";
        public static final String TIME_INTERVAL="time_interval";
        public static final String ROUTE_ID="route_id";

    }

    public static abstract class StopEntry implements BaseColumns {

        public static final String TABLE_NAME="stop";

        public static final String STOP_ID="stop_id";
        public static final String LATITUDE="latitude";
        public static final String LONGITUDE="longitude";
        public static final String DESCRIPTION="description";
        public static final String JOURNEY_ID="journey_id";




    }


}
