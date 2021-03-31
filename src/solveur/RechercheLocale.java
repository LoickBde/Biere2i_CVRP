package solveur;

import instance.Instance;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.ListeTabou;
import operateur.OperateurIntraTournee;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RechercheLocale implements Solveur {
    @Override
    public String getNom() {
        return "Recherche Locale";
    }

    @Override
    public Solution solve(Instance instance) {
        ListeTabou listeTabou = ListeTabou.getInstance();
        listeTabou.vider();

        ClarkeAndWright caw = new ClarkeAndWright();
        Solution s = caw.solve(instance);

        List<TypeOperateurLocal> typeOperateurLocalList = new ArrayList<>();
        typeOperateurLocalList.add(TypeOperateurLocal.INTRA_DEPLACEMENT); //1
        typeOperateurLocalList.add(TypeOperateurLocal.INTER_DEPLACEMENT); //2
        typeOperateurLocalList.add(TypeOperateurLocal.INTER_ECHANGE); //3
        typeOperateurLocalList.add(TypeOperateurLocal.INTRA_ECHANGE); //4

        boolean improve = true;

        while(improve){
            improve = false;
            for(TypeOperateurLocal type : typeOperateurLocalList){
                OperateurLocal best = s.getMeilleurOperateurLocal(type);
                if(best.isMouvementAmeliorant()){
                    if(s.doMouvementRechercheLocale(best))
                        improve = true;
                }
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
