package solveur;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.InsertionClient;
import operateur.Operateur;
import solution.Solution;

import java.util.LinkedList;
import java.util.List;

public class MeilleureInsertion implements Solveur {
    @Override
    public String getNom() {
        return "Meilleure insertion";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);
        LinkedList<Client> clients = instance.getClients();

        while(!clients.isEmpty()){
            InsertionClient best = this.getMeilleurOperateur(clients, solution);
            if(best == null || !solution.doInsertion(best)) {
                Client c = clients.getFirst();
                solution.ajouterClientNvTournee(c);
                clients.remove(c);
            } else {
                clients.remove(best.getClient());
            }
        }

        return solution;
    }

    private InsertionClient getMeilleurOperateur(List<Client> clients, Solution solution){
        InsertionClient best = solution.getMeilleureInsertion(clients.get(0));
        for (int i = 1; i < clients.size(); i++) {
            InsertionClient test = solution.getMeilleureInsertion(clients.get(i));
            if(best == null || (test != null && test.isMeilleur(best))){
                best = test;
            }
        }
        return best;
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
            MeilleureInsertion meilleureInsertion = new MeilleureInsertion();
            Solution solution = meilleureInsertion.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if (solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}