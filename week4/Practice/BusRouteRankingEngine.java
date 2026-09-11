package week4.Practice;


public class BusRouteRankingEngine {

    static class BusRoute {

        private String routeCode;
        private String routeName;
        private int priority;

        public BusRoute(
                String routeCode,
                String routeName,
                int priority) {

            this.routeCode = routeCode;
            this.routeName = routeName;

            // this resolves the parameter/field naming.
            this.priority = priority;
        }

        // Default priority = 5
        public BusRoute(
                String routeCode,
                String routeName) {

            this(routeCode, routeName, 5);
        }

        public int compareTo(BusRoute other) {

            // Rule 1: lower priority number comes first.
            if (this.priority != other.priority) {

                return Integer.compare(
                        this.priority,
                        other.priority);
            }

            // Rule 2: route code, case-insensitive.
            int codeComparison =
                    this.routeCode.compareToIgnoreCase(
                            other.routeCode);

            if (codeComparison != 0) {
                return codeComparison;
            }

            // Rule 3: route name, case-insensitive.
            int nameComparison =
                    this.routeName.compareToIgnoreCase(
                            other.routeName);

            return nameComparison;
        }

        public String getRouteCode() {
            return routeCode;
        }
    }

    static BusRoute[] rankRoutes(
            BusRoute[] routes) {

        BusRoute[] result =
                new BusRoute[routes.length];

        for (int i = 0; i < routes.length; i++) {
            result[i] = routes[i];
        }

        /*
         * Stable insertion sort.
         *
         * If two routes compare equal, the later route
         * is not moved before the earlier route.
         */
        for (int i = 1; i < result.length; i++) {

            BusRoute current = result[i];

            int j = i - 1;

            while (j >= 0
                    && result[j].compareTo(current) > 0) {

                result[j + 1] = result[j];
                j--;
            }

            result[j + 1] = current;
        }

        return result;
    }

    public static void main(String[] args) {

        BusRoute[] routes = {
            new BusRoute(
                    "RT205L",
                    "Airport Express",
                    3),

            new BusRoute(
                    "rt201j",
                    "City Central",
                    4),

            new BusRoute(
                    "RT299T",
                    "Night Service")
        };

        BusRoute[] ranked =
                rankRoutes(routes);

        System.out.print("Ranked routes: [");

        for (int i = 0; i < ranked.length; i++) {

            System.out.print(
                    "\"" + ranked[i].getRouteCode()
                    + "\"");

            if (i < ranked.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}