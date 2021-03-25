package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import operateur.IntraDeplacement;
import operateur.OperateurIntraTournee;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Tournee;

public class TestIntraDeplacement {

    public static void main(String[] args) {
        int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(id++, 10, 0, 5);
        Client c2 = new Client(id++, 20, 0, 10);
        Client c3 = new Client(id++, 30, 0, 15);
        Client c4 = new Client(id++, 40, 0, 20);
        Client c5 = new Client(id, 50, 0, 25);
        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);
        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c3);
        t.ajouterClient(c4);
        t.ajouterClient(c5);

        System.out.println("Cout total tournée : " + t.getCoutTotal());

        OperateurIntraTournee idep = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT, t, 0, 4);

        assert idep != null;
        System.out.println(idep.getDeltaCout());

        System.out.println("Dep 0 vers 4 : " + t.deltaCoutDeplacement(0, 4));
        IntraDeplacement id1 = new IntraDeplacement(t, 0, 4);
        System.out.println(id1.toString() + " Delta cout : " + id1.getDeltaCout());
        System.out.println(id1.isMouvementRealisable());
        System.out.println(id1.isMouvementAmeliorant());

        System.out.println("Dep 2 vers 5 : " + t.deltaCoutDeplacement(2, 5));
        IntraDeplacement id2 = new IntraDeplacement(t, 2, 5);
        System.out.println(id2.toString() + " Delta cout : " + id2.getDeltaCout());
        System.out.println(id2.isMouvementRealisable());
        System.out.println(id2.isMouvementAmeliorant());

        System.out.println("Dep 3 vers 3 : " + t.deltaCoutDeplacement(3, 3));
        IntraDeplacement id3 = new IntraDeplacement(t, 3, 3);
        System.out.println(id3.toString()  + " Delta cout : " + id3.getDeltaCout());
        System.out.println(id3.isMouvementRealisable());
        System.out.println(id3.isMouvementAmeliorant());

        System.out.println("Dep 4 vers 2 : " + t.deltaCoutDeplacement(4, 2));
        IntraDeplacement id4 = new IntraDeplacement(t, 4, 2);
        System.out.println(id4.toString()  + " Delta cout : " + id4.getDeltaCout());

        System.out.println(id4.isMouvementRealisable());
        System.out.println(id4.isMouvementAmeliorant());

        System.out.println(id1.isMeilleur(id2));
        System.out.println(id2.isMeilleur(id1));
        System.out.println(id4.isMeilleur(id2));

        id4.doMouvementIfRealisable();
        id3.doMouvementIfRealisable();

        System.out.println("Cout total tournée : " + t.getCoutTotal());
    }
}
