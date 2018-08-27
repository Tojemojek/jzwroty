package pl.kostrowski.doka.jzwroty.helpers;

public class SplitterTest {

    public static void main(String[] args) {

        String exampleSalesManCommission = "PL63: (100%), PL68: (100%), PL71: (100%),";

        String[] split = exampleSalesManCommission.split(",");

        String salesman;
        String commision;

        for (String s : split) {
            int pl = s.indexOf("PL");
            int dwukropek = s.indexOf(":");
            int nawias = s.indexOf("(") + 1;
            int procent = s.indexOf("%");

            salesman = s.substring(pl, dwukropek);
            commision = s.substring(nawias, procent);

            System.out.println(salesman);
            System.out.println(commision);
        }
    }
}