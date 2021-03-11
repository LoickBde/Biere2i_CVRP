package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.InsertionClient;
import solution.Solution;
import solution.Tournee;
import solveur.MeilleureInsertion;

public class TestMeilleureInsertion {

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            System.out.println("Instance lue avec success !");
            Instance instance = reader.readInstance();

            MeilleureInsertion mi = new MeilleureInsertion();
            Solution solution = mi.solve(instance);

            System.out.println(solution.getCoutTotal());
            if(solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
