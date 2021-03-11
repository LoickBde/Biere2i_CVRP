package instance.reseau;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Point {
    private final int id;
    private int abscisse;
    private int ordonnee;
    private Map<Point, Route> routes;

    public Point(int id, int abscisse, int ordonnee) {
        this.id = id;
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.routes = new HashMap<>();
    }

    public boolean ajouterRoute(Point destination) {
        if(destination == null) return false;
        Route route = new Route(this, destination);
        this.routes.put(destination, route);
        return true;
    }

    public int getCoutVers(Point destination) {
        Route route = this.routes.get(destination);
        if(route == null) return Integer.MAX_VALUE;
        else return route.getCout();
    }

    public int getId() {
        return id;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id == point.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Point { " +
                " id= " + id +
                ", abscisse= " + abscisse +
                ", ordonnee= " + ordonnee +
                " }";
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 2 ,1, 5);
        Client c2 = new Client(2, 9, 5, 6);
        Client c3 = new Client(3, 2, 6, 5);
        Client c4 = new Client(4, 2, 6, 9);

        c1.ajouterRoute(c2);
        c1.ajouterRoute(c3);

        System.out.println(c1.getCoutVers(c2));
        System.out.println(c1.getCoutVers(c3));
        System.out.println(c1.getCoutVers(c4));
    }
}
