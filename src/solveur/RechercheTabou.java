package solveur;

import instance.Instance;
import instance.reseau.Client;
import operateur.InsertionClient;
import operateur.ListeTabou;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RechercheTabou implements Solveur {

    @Override
    public String getNom() {
        return "Recherche tabou";
    }

    @Override
    public Solution solve(Instance instance) {
        RechercheLocale rc = new RechercheLocale();
        Solution solution = rc.solve(instance);
        Solution bestSol = new Solution(solution);


        List<TypeOperateurLocal> typeOperateurLocalList = new ArrayList<>();
        typeOperateurLocalList.add(TypeOperateurLocal.INTRA_DEPLACEMENT); //1
        typeOperateurLocalList.add(TypeOperateurLocal.INTER_DEPLACEMENT); //2
        typeOperateurLocalList.add(TypeOperateurLocal.INTER_ECHANGE); //3
        typeOperateurLocalList.add(TypeOperateurLocal.INTRA_ECHANGE); //4

        final int nbInterMax = 10000;
        int nbIterSansAmelioration = 0;

        ListeTabou listeTabou = ListeTabou.getInstance();
        listeTabou.setDeltaAspiration(bestSol.getCoutTotal() - solution.getCoutTotal());
        listeTabou.vider();

        while(nbIterSansAmelioration < nbInterMax) {
            OperateurLocal best = solution.getMeilleurOperateurLocal(typeOperateurLocalList.get(0));

            for (TypeOperateurLocal type : typeOperateurLocalList) {
                OperateurLocal op = solution.getMeilleurOperateurLocal(type);

                if (op != null && op.isMouvementAmeliorant() && op.isMeilleur(best)) {
                    best = op;
                }
            }

            if (solution.doMouvementRechercheLocale(best)) {
                listeTabou.add(best);
            }

            if (solution.getCoutTotal() < bestSol.getCoutTotal()) {
                bestSol = new Solution(solution);
                listeTabou.setDeltaAspiration(bestSol.getCoutTotal() - solution.getCoutTotal());
                nbIterSansAmelioration = 0;
            } else {
                nbIterSansAmelioration++;
            }
        }
        return bestSol;
    }
}
