package solveur;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import solution.Solution;

public class InsertionSimple implements Solveur{
    @Override
    public String getNom() {
        return "Insertion simple";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);

        instance.getClients().forEach(client -> {
            if(!solution.ajouterClientTournee(client))
                solution.ajouterClientNvTournee(client);
        });

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

        if(instance != null){
            InsertionSimple insertionSimple = new InsertionSimple();
            insertionSimple.getNom();
            Solution solution = insertionSimple.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if(solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}
