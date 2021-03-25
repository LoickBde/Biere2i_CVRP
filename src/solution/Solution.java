package solution;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.*;

import java.util.*;

public class Solution {
    private int coutTotal;
    private Instance instance;
    private List<Tournee> tournees;

    public Solution(Instance instance) {
        this.instance = instance;
        this.tournees = new LinkedList<>();
    }

    public boolean ajouterClientNvTournee(Client clientToAdd) {
        if(clientToAdd == null) return false;
        Tournee tournee = new Tournee(instance);
        if(!tournee.ajouterClient(clientToAdd)) return false;

        this.tournees.add(tournee);
        this.coutTotal += tournee.getCoutTotal();
        return true;
    }

    public boolean ajouterClientTournee(Client clientToAdd) {
        if(clientToAdd == null) return false;
        for(Tournee t : tournees){
            if(ajouterClient(clientToAdd, t))
                return true;
        }
        return false;
    }

    public boolean ajouterClientLastTournee(Client clientToAdd){
        if(clientToAdd == null) return false;
        if(this.tournees.size() == 0) return false;
        return ajouterClient(clientToAdd, this.tournees.get(this.tournees.size()-1));
    }

    private boolean ajouterClient(Client c, Tournee t){
        int coutTemp = t.getCoutTotal();

        if(t.ajouterClient(c)){
            this.coutTotal +=  t.getCoutTotal() - coutTemp;
            return true;
        }

        return false;
    }

    public InsertionClient getMeilleureInsertion(Client clientToAdd){
        if(this.tournees.isEmpty()) return null;

        InsertionClient best = this.tournees.get(0).getMeilleureInsertion(clientToAdd);

        for(int i=1; i<this.tournees.size(); i++){
            InsertionClient test = this.tournees.get(i).getMeilleureInsertion(clientToAdd);
            if(best == null || (test != null && test.isMeilleur(best))){
                best = test;
            }

        }
        return best;
    }

    public FusionTournees getMeilleureFusion() {
        if(this.tournees.isEmpty()) return null;

        FusionTournees best = null, test;

        for (Tournee t: this.tournees) {
            test = getMeilleureFusion(t);
            if(best == null || (test!= null && test.isMeilleur(best))){
                best = test;
            }
        }

        return best;
    }

    private FusionTournees getMeilleureFusion(Tournee mTournee){
        if(mTournee.getDemandeTotal() == 0) return null;

        FusionTournees best = null, test = null;

        for (Tournee t: this.tournees) {
            if(!mTournee.equals(t) && t.getDemandeTotal() != 0 && mTournee.getDemandeTotal() + t.getDemandeTotal() <= mTournee.getCapacite()){
                test = new FusionTournees(mTournee, t);
            }
            if (best == null || test.isMeilleur(best)) {
                best = test;
            }
        }

        return best;
    }

    public OperateurLocal getMeilleurOperateurLocal(TypeOperateurLocal type){
        if(OperateurLocal.getOperateur(type) instanceof OperateurIntraTournee){
            return getMeilleurOperateurIntra(type);
        }
        if(OperateurLocal.getOperateur(type) instanceof OperateurInterTournees){
            return getMeilleurOperateurInter(type);
        }
        return null;
    }

    private OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type){
        if(this.tournees.isEmpty()) return null;

        OperateurLocal best = new IntraDeplacement();

        for (Tournee t: this.tournees) {
            OperateurLocal op = t.getMeilleurOperateurIntra(type);
            if(op != null && op.isMeilleur(best)) {
                best = op;
            }
        }

        return best;
    }

    private OperateurLocal getMeilleurOperateurInter(TypeOperateurLocal type){
        if(this.tournees.isEmpty()) return null;

        OperateurLocal best = new InterDeplacement();

        for (Tournee t: this.tournees) {
            for(Tournee autreTournee: this.tournees){
                OperateurLocal op = t.getMeilleurOperateurInter(autreTournee, type);
                if(op != null && op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
    }

    public boolean doInsertion(InsertionClient infos){
        if(!infos.doMouvementIfRealisable()) return false;
        this.coutTotal+=infos.getDeltaCout();
        return true;
    }

    public boolean doFusion(FusionTournees infos){
        if(!infos.doMouvementIfRealisable() || !infos.isMouvementAmeliorant()) return false;
        this.tournees.remove(infos.getaFusionner());
        this.coutTotal+=infos.getDeltaCout();
        return true;
    }

    public boolean doMouvementRechercheLocale(OperateurLocal infos){
//        if(!infos.isMouvementAmeliorant())
//            return false;
        if(!infos.doMouvementIfRealisable())
            return false;
        this.coutTotal+= infos.getDeltaCout();
        return check();
    }

    public boolean check() {
        for(Tournee t : tournees) {
            if(!t.check())
                return false;
        }

        if(checkCout() != this.coutTotal)
            return false;

        if(!checkClients()) return false;

        return true;
    }

    private int checkCout(){
        int checkCout = 0;

        for(Tournee t : tournees){
            checkCout += t.getCoutTotal();
        }

        return  checkCout;
    }

    private boolean checkClients() {
        Map<Integer, Integer> map = new HashMap<>();

        for(Client c : instance.getClients()) {
            map.put(c.getId(), 0);
        }

        this.tournees.forEach(tournee -> {
            tournee.getClients().forEach(client -> {
                int clientId = client.getId();
                int count = map.get(clientId);
                map.put(clientId, count+1);
            });
        });

        Collection<Integer> values = map.values();
        for(int i: values){
            if(i > 1) return  false;
        }

        return true;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Objects.equals(instance, solution.instance) && Objects.equals(tournees, solution.tournees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance, tournees);
    }

    @Override
    public String toString() {
        return "Solution {" +
                "coutTotal= " + coutTotal +
                ", instance= " + instance +
                ", tournees= " + tournees +
                " }";
    }

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            System.out.println("Instance lue avec success !");
            Instance instance = reader.readInstance();
            Solution solution = new Solution(instance);
            instance.getClients().forEach(client -> {
               if(!solution.ajouterClientLastTournee(client)){
                   if(!solution.ajouterClientNvTournee(client))
                       System.out.println("Client non ajouté !");
                   else
                       System.out.println("Client ajouté à la nouvelle tournée !");
               } else
                   System.out.println("Client ajouté a tournée existante !");
            });
            System.out.println(solution.getCoutTotal());
            // solution.tournees.forEach(tournee -> System.out.println(tournee.getCoutTotal()));
            solution.tournees.forEach(tournee -> {
                System.out.println(tournee.getDemandeTotal());
                System.out.println(tournee.getClients());
            });
            if(solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
