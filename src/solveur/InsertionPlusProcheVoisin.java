package solveur;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Route;
import io.InstanceReader;
import io.exception.ReaderException;
import solution.Solution;

import java.util.LinkedList;

public class InsertionPlusProcheVoisin implements Solveur {
    @Override
    public String getNom() {
        return "Insertion plus proche voisin";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);
        LinkedList<Client> clients = instance.getClients();
        Client client = clients.getFirst();

        while(clients.size() > 0) {

            if(!solution.ajouterClientLastTournee(client))
                solution.ajouterClientNvTournee(client);

            clients.remove(client);
            client = plusProcheVoisin(client, clients);
        }
        return solution;
    }

    private Client plusProcheVoisin(Client lastClientAdded, LinkedList<Client> clients){
        int cout = Integer.MAX_VALUE;
        Client nextClient = null;

        for(Client client: clients){
            int coutTemp = lastClientAdded.getCoutVers(client);
            if(coutTemp < cout){
                cout = coutTemp;
                nextClient = client;
            }
        };
        return  nextClient;
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
            InsertionPlusProcheVoisin insertionPlusProcheVoisin = new InsertionPlusProcheVoisin();
            insertionPlusProcheVoisin.getNom();
            Solution solution = insertionPlusProcheVoisin.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if(solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}
