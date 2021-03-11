package solveur;

import instance.Instance;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.OperateurIntraTournee;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

public class RechercheLocale implements Solveur {
    @Override
    public String getNom() {
        return "Recherche Locale";
    }

    @Override
    public Solution solve(Instance instance) {
        InsertionSimple is = new InsertionSimple();
        Solution s = is.solve(instance);

        boolean improve = true;

        while(improve){
            improve = false;
            OperateurIntraTournee best = s.getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE); //INTRA_DEPLACEMENT
            if(best.isMouvementAmeliorant()){
                if(s.doMouvementRechercheLocale(best))
                    improve = true;
            }
        }
        return s;
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
            RechercheLocale rc = new RechercheLocale();
            Solution solution = rc.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if(solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}
