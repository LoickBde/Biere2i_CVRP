package solveur;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.FusionTournees;
import solution.Solution;

import java.util.LinkedList;

public class ClarkeAndWright implements Solveur{
    @Override
    public String getNom() {
        return "ClarkeAndWight";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);
        LinkedList<Client> clients = instance.getClients();
        boolean fusion = true;

        for (Client c: clients) {
            solution.ajouterClientNvTournee(c);
        }

        while(fusion){
            FusionTournees bestFt = solution.getMeilleureFusion();
            if(bestFt == null || !solution.doFusion(bestFt)) fusion = false;
        }

        return solution;
    }

    public static void main(String[] args) {
        Instance instance = null;

        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            instance = reader.readInstance();
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }

        if (instance != null) {
            ClarkeAndWright cw = new ClarkeAndWright();
            Solution solution = cw.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if (solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}
