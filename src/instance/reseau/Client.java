package instance.reseau;

import java.util.Objects;

public class Client extends Point {
    private int demande;

    public Client(int id, int abscisse, int ordonnee, int demande) {
        super(id, abscisse, ordonnee);
        this.demande = demande;
    }

    public int getDemande() {
        return demande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return this.getId() == client.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Client { " +
                "demande= " + demande + " " +
                super.toString() +
                " }";
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 1 ,1, 5);
        Client c2 = new Client(2, 5, 5, 6);
        Client c3 = new Client(3, 1, 1, 5);

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());

        System.out.println(c1.equals(c2));
        System.out.println(c1.equals(c3));

        System.out.println(c1.getAbscisse());
        System.out.println(c1.getOrdonnee());
    }
}
