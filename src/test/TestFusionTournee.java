package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import operateur.FusionTournees;
import operateur.InsertionClient;
import solution.Solution;
import solution.Tournee;

import java.util.LinkedList;

public class TestFusionTournee {

    public static void main(String[] args) {
        int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Tournee t1, t2, t3, t4;
        FusionTournees ft1, ft2;

        Solution solution = new Solution(inst);

        Client c1 = new Client(id++,10, 0, 5);
        Client c2 = new Client(id++,20,  0, 10);
        Client c3 = new Client(id++,30,  0, 15);
        Client c4 = new Client(id++,40, 0, 5);
        Client c5 = new Client(id++,50,  0, 10);
        Client c6 = new Client(id,60,  0, 15);
        Client c7 = new Client(id++,70, 0, 5);
        Client c8 = new Client(id++,80,  0, 10);
        Client c9 = new Client(id,100,  0, 15);
        Client c10 = new Client(id++,110, 0, 5);
        Client c11 = new Client(id++,120,  0, 10);
        Client c12 = new Client(id,130,  0, 15);

        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);
        inst.ajouterClient(c6);
        inst.ajouterClient(c7);
        inst.ajouterClient(c8);
        inst.ajouterClient(c9);
        inst.ajouterClient(c10);
        inst.ajouterClient(c11);
        inst.ajouterClient(c12);

        t1 = new Tournee(inst);
        t2 = new Tournee(inst);
        t3 = new Tournee(inst);
        t4 = new Tournee(inst);

//        t1.ajouterClient(c1);
//        t1.ajouterClient(c2);
//        t1.ajouterClient(c3);
//        t2.ajouterClient(c4);
//        t2.ajouterClient(c5);
//        t2.ajouterClient(c6);
//        t3.ajouterClient(c7);
//        t3.ajouterClient(c8);
//        t3.ajouterClient(c9);
//        t4.ajouterClient(c10);
//        t4.ajouterClient(c11);
//        t4.ajouterClient(c12);

        solution.ajouterClientNvTournee(c1);
        solution.ajouterClientNvTournee(c2);
        solution.ajouterClientNvTournee(c3);
        solution.ajouterClientNvTournee(c4);
        solution.ajouterClientNvTournee(c5);
        solution.ajouterClientNvTournee(c6);
        solution.ajouterClientNvTournee(c7);
        solution.ajouterClientNvTournee(c8);
        solution.ajouterClientNvTournee(c9);
        solution.ajouterClientNvTournee(c10);
        solution.ajouterClientNvTournee(c11);
        solution.ajouterClientNvTournee(c12);

        System.out.println(solution);

        FusionTournees ft = solution.getMeilleureFusion();

        ft.doMouvementIfRealisable();

//        System.out.println(t1.getCoutTotal());
//        System.out.println(t2.getCoutTotal());
//        System.out.println(t1.deltaCoutFusion(t2));
//        System.out.println(t2.deltaCoutFusion(t1));
//
//        ft1 = new FusionTournees(t1, t2);
//        ft2 = new FusionTournees(t2, t1);

//
//        System.out.println(ft1.getDeltaCout());
//        System.out.println(ft2.getDeltaCout());
//
//        System.out.println(ft1.isMeilleur(ft2));

//        ft1.doMouvementIfRealisable();
    }

}
