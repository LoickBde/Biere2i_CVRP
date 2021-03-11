package instance.reseau;

import java.util.Objects;

public class Route {
    private Point depart;
    private Point arrive;
    private int cout;

    public Route(Point depart, Point arrive) {
        this.depart = depart;
        this.arrive = arrive;
        this.cout = calculCout(this.depart, this.arrive);
    }

    private int calculCout(Point depart, Point arrive){
        int distX = arrive.getAbscisse() - depart.getAbscisse();
        int distY = arrive.getOrdonnee() - depart.getOrdonnee();

        return (int)Math.round(Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)));
    }

    public Point getDepart() {
        return depart;
    }

    public Point getArrive() {
        return arrive;
    }

    public int getCout() {
        return cout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return  Objects.equals(depart, route.depart) && Objects.equals(arrive, route.arrive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depart, arrive);
    }

    @Override
    public String toString() {
        return "Route { " +
                "depart= " + depart +
                ", arrive= " + arrive +
                ", cout= " + cout +
                " }";
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 1 ,1, 5);
        Client c2 = new Client(2, 5, 5, 6);
        Client c3 = new Client(3, 8, 6, 5);

        Route r1 = new Route(c1, c2);
        Route r2 = new Route(c2, c3);

        System.out.println(r1.getCout());
        System.out.println(r2.getCout());

        System.out.println(r1.getCout());
    }
}
