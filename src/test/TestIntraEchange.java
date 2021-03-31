package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import operateur.*;
import solution.Solution;
import solution.Tournee;

public class TestIntraEchange {

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

        System.out.println(t.deltaCoutEchange(0,1));
        System.out.println(t.deltaCoutEchange(3,4));
        System.out.println(t.deltaCoutEchange(0,4));
        System.out.println(t.deltaCoutEchange(1,3));
        System.out.println(t.deltaCoutEchange(4,3));
        System.out.println(t.deltaCoutEchange(4,5));

        OperateurIntraTournee opIntraEch =  OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,0,1);
        OperateurIntraTournee opIntraEch2 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,3,4);
        OperateurIntraTournee opIntraEch3 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,0,4);
        OperateurIntraTournee opIntraEch4 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,1,3);
        OperateurIntraTournee opIntraEch5 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,4,3);
        OperateurIntraTournee opIntraEch6 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,4,5);

        OperateurIntraTournee opIntraEch7 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,0,4);
        OperateurIntraTournee opIntraEch8 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,4,0);
        OperateurIntraTournee opIntraEch9 = OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,3,1);

        assert opIntraEch != null;
        assert opIntraEch2 != null;
        assert opIntraEch3 != null;
        assert opIntraEch4 != null;
        assert opIntraEch5 != null;
        assert opIntraEch6 != null;

        System.out.println(opIntraEch.getDeltaCout());
        System.out.println(opIntraEch.isMouvementRealisable());
        System.out.println(opIntraEch.isMouvementAmeliorant());

        System.out.println(opIntraEch2.getDeltaCout());
        System.out.println(opIntraEch2.isMouvementRealisable());
        System.out.println(opIntraEch2.isMouvementAmeliorant());

        System.out.println(opIntraEch3.getDeltaCout());
        System.out.println(opIntraEch3.isMouvementRealisable());
        System.out.println(opIntraEch3.isMouvementAmeliorant());

        System.out.println(opIntraEch4.getDeltaCout());
        System.out.println(opIntraEch4.isMouvementRealisable());
        System.out.println(opIntraEch4.isMouvementAmeliorant());

        System.out.println(opIntraEch5.getDeltaCout());
        System.out.println(opIntraEch5.isMouvementRealisable());
        System.out.println(opIntraEch5.isMouvementAmeliorant());

        System.out.println(opIntraEch6.getDeltaCout());
        System.out.println(opIntraEch6.isMouvementRealisable());
        System.out.println(opIntraEch6.isMouvementAmeliorant());

        System.out.println(opIntraEch.isMeilleur(opIntraEch3));
        System.out.println(opIntraEch.isMeilleur(opIntraEch2));

        OperateurLocal best = t.getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE);

        System.out.println(best);
        System.out.println(best.getDeltaCout());
        System.out.println(best.isMouvementRealisable());
        System.out.println(best.isMouvementAmeliorant());

        System.out.println("----");

        System.out.println(opIntraEch7.isTabou(opIntraEch3));
        System.out.println(opIntraEch8.isTabou(opIntraEch3));
        System.out.println(opIntraEch9.isTabou(opIntraEch3));
    }

}
