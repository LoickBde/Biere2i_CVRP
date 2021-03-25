package solution;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import instance.reseau.Point;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.*;

import java.rmi.MarshalException;
import java.util.LinkedList;
import java.util.Objects;

public class Tournee {
    private final int capacite;
    private int demandeTotal;
    private int coutTotal;
    private final Depot depot;
    private LinkedList<Client> clients;

    public Tournee(Instance instance) {
        this.capacite = instance.getCapacite();
        this.depot = instance.getDepot();
        this.coutTotal = this.demandeTotal = 0;
        this.clients = new LinkedList<>();
    }

    public boolean ajouterClient(Client clientToAdd) {
        if(clientToAdd == null)
            return false;

        if(this.demandeTotal + clientToAdd.getDemande() > this.capacite)
            return false;

        if(!majCoutTotal(clientToAdd)) return false;

        this.demandeTotal += clientToAdd.getDemande();
        this.clients.add(clientToAdd);
        return true;
    }

    private boolean majCoutTotal(Client clientToAdd) {
        int coutTemp = deltaCoutInsertion(this.clients.size(), clientToAdd);
        if(coutTemp == Integer.MAX_VALUE)
            return false;
        coutTotal += coutTemp;
        return true;
    }

    private boolean isPositionInsertionValide(int position){
        return position >= 0 && position <= this.clients.size();
    }

    private boolean isPositionValide(int position){
        return position >= 0 && position < this.clients.size();
    }

    private boolean isEchangeValide(Client clientToRemove, Client clientToAdd){
        if(clientToRemove == null || clientToAdd == null)
            return false;
        return this.demandeTotal - clientToRemove.getDemande() + clientToAdd.getDemande() <= this.capacite;
    }

    public int deltaCoutInsertion(int position, Client clientToAdd){
        if(!this.isPositionInsertionValide(position) || clientToAdd == null) return Integer.MAX_VALUE;

        Point prec = this.getPrec(position);
        Point current = this.getCurrent(position);

        if(prec.getCoutVers(clientToAdd) == Integer.MAX_VALUE || clientToAdd.getCoutVers(current) == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        if(prec.equals(current))
            return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(prec);
        return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(current) - prec.getCoutVers(current);
    }

    public int deltaCoutFusion(Tournee aFusionner){
        Client first = aFusionner.getClients().getFirst();
        Client last = this.clients.getLast();

        return last.getCoutVers(first) - last.getCoutVers(this.depot) - aFusionner.depot.getCoutVers(first);
    }

    public int deltaCoutSuppression(int position){
        if(!this.isPositionValide(position) || this.clients.isEmpty()) return Integer.MAX_VALUE;

        Point prec = this.getPrec(position);
        Point current = this.getCurrent(position);
        Point next = this.getNext(position);

        if(this.clients.size() == 1)
            return - prec.getCoutVers(current) - current.getCoutVers(next);
        return prec.getCoutVers(next) - prec.getCoutVers(current) - current.getCoutVers(next);
    }

    public int deltaCoutDeplacement(int positionI, int positionJ){
        if(!isDataDeplacmentValide(positionI, positionJ)) return Integer.MAX_VALUE;

        return deltaCoutSuppression(positionI) + deltaCoutInsertion(positionJ, this.clients.get(positionI));
    }

    public int deltaCoutEchange(int positionI, int positionJ){
        if(!isPositionValide(positionI) || !isPositionValide(positionJ) || positionI >= positionJ) return Integer.MAX_VALUE;

        if(positionJ == positionI+1){
            return this.deltaCoutEchangeConsecutif(positionI);
        } else {
            return this.deltaCoutRemplacement(positionI, this.getClient(positionJ)) + this.deltaCoutRemplacement(positionJ, this.getClient(positionI));
        }
    }

    private int deltaCoutEchangeConsecutif(int positionI){
        // attention peut etre bug lorsque liste size vaut 1
        Point prec = this.getPrec(positionI);
        Point pointI = this.getCurrent(positionI);
        Point pointJ = this.getCurrent(positionI+1); //attention getnext / getcurrent
        Point next = this.getNext(positionI+1);

        int coutToRemove = prec.getCoutVers(pointI) + pointI.getCoutVers(pointJ) + pointJ.getCoutVers(next);
        int coutToAdd = prec.getCoutVers(pointJ) + pointJ.getCoutVers(pointI) + pointI.getCoutVers(next);

        return -coutToRemove + coutToAdd;
    }

    public int deltaCoutRemplacement(int position, Client client){
        if(client == null) return Integer.MAX_VALUE;

        Point precPointI = this.getPrec(position);
        Point pointI = this.getCurrent(position);
        Point nextPointI = this.getNext(position);

        int coutToRemove = precPointI.getCoutVers(pointI) + pointI.getCoutVers(nextPointI);
        int coutToAdd = precPointI.getCoutVers(client) + client.getCoutVers(nextPointI);

        return  -coutToRemove + coutToAdd;
    }

    public int deltaCoutInsertionInter(int position, Client clientToAdd){
        if(!isAjoutRealisable(clientToAdd)) return Integer.MAX_VALUE;
        return deltaCoutInsertion(position, clientToAdd);
    }

    public int deltaCoutRemplacementInter(int position, Client clientToRemove, Client clientToAdd){
        if(!isEchangeValide(clientToRemove, clientToAdd)) return Integer.MAX_VALUE;
        if(!isPositionValide(position)) return Integer.MAX_VALUE;
        return deltaCoutRemplacement(position, clientToAdd);
    }

    private boolean isDataDeplacmentValide(int positionI, int positionJ){
        if(positionI == positionJ) return false;

        if(Math.abs(positionI - positionJ) == 1) return false;

        if(!isPositionValide(positionI)) return false;

        if(!isPositionInsertionValide(positionJ)) return false;

        return true;
    }

    private boolean isAjoutRealisable(Client clientToAdd){
        return clientToAdd != null && this.demandeTotal + clientToAdd.getDemande() <= this.capacite;
    }

    public boolean doInsertion(InsertionClient infos){
        if(infos == null || this.clients.contains(infos.getClient())) return false;

        this.clients.add(infos.getPosition(), infos.getClient());
        this.coutTotal += infos.getDeltaCout();
        this.demandeTotal += infos.getClient().getDemande();

        return check();
    }

    public boolean doFusion(FusionTournees infos){
        if(infos == null) return false;
        this.clients.addAll(infos.getaFusionner().getClients());
        this.demandeTotal += infos.getaFusionner().getDemandeTotal();
        this.coutTotal += infos.getaFusionner().getCoutTotal() + infos.getDeltaCout();

        return check();
    }

    public boolean doDeplacement(IntraDeplacement infos){
        if(infos == null) return false;

        int posI = infos.getPositionI();
        int posJ = infos.getPositionJ();

        if(deltaCoutDeplacement(posI, posJ) == Integer.MAX_VALUE) return false;

        Client toMove = this.clients.get(posI);
        this.clients.remove(posI);

        if(posI > posJ)
            this.clients.add(posJ, toMove);
        else
            this.clients.add(posJ-1, toMove);

        this.coutTotal += infos.getDeltaCout();

        return check();
    }

    public boolean doDeplacement(InterDeplacement infos){
        if(infos == null) return false;

        int posI = infos.getPositionI();
        int posJ = infos.getPositionJ();


        Client toMove = this.clients.get(posI);
        if(toMove == null) return false;

        this.clients.remove(posI);
        this.coutTotal+=infos.getDeltaCoutTournee();
        this.demandeTotal-=toMove.getDemande();

        infos.getAutreTournee().clients.add(posJ, toMove);
        infos.getAutreTournee().coutTotal+=infos.getDeltaCoutAutreTournee();
        infos.getAutreTournee().demandeTotal+=toMove.getDemande();

        return check();
    }

    public boolean doEchange(IntraEchange infos){
        if(infos == null) return false;

        int positionI = infos.getPositionI();
        int positionJ = infos.getPositionJ();

        Client clientI = this.clients.get(positionI);
        Client clientJ = this.clients.get(positionJ);

        if(clientI == null || clientJ == null) return false;

        this.clients.remove(positionJ);
        this.clients.add(positionJ, clientI);

        this.clients.remove(positionI);
        this.clients.add(positionI, clientJ);

        this.coutTotal += infos.getDeltaCout();
        return check();
    }

    public boolean doEchange(InterEchange infos){
        if(infos == null) return false;

        int positionI = infos.getPositionI();
        int positionJ = infos.getPositionJ();

        Client clientI = this.clients.get(positionI);
        Client clientJ = infos.getAutreTournee().clients.get(positionJ);

        if(clientI == null || clientJ == null) return false;

        this.clients.remove(positionI);
        this.clients.add(positionI, clientJ);
        this.coutTotal+=infos.getDeltaCoutTournee();
        this.demandeTotal= this.demandeTotal - clientI.getDemande() + clientJ.getDemande();

        infos.getAutreTournee().clients.remove(positionJ);
        infos.getAutreTournee().clients.add(positionJ, clientI);
        infos.getAutreTournee().coutTotal+=infos.getDeltaCoutAutreTournee();
        infos.getAutreTournee().demandeTotal= infos.getAutreTournee().demandeTotal - clientJ.getDemande() + clientI.getDemande();

        return check();
    }

    public boolean check() {
        int checkDemande = checkDemande();
        int checkCout = checkCout();

        return checkDemande == this.demandeTotal && checkDemande <= this.capacite && checkCout == this.coutTotal;
    }

    private int checkDemande() {
        int checkDemande = 0;

        for(Client c: this.clients){
            checkDemande += c.getDemande();
        }

        return checkDemande;
    }

    private int checkCout() {
        if(this.clients.isEmpty()) return 0;

        int checkCout = 0;

        checkCout += this.depot.getCoutVers(this.clients.getFirst());
        for(int i=0; i<this.clients.size()-1; i++){
            checkCout += this.clients.get(i).getCoutVers(this.clients.get(i+1));
        }
        checkCout += this.clients.getLast().getCoutVers(this.depot);

        return checkCout;
    }

    private Point getPrec(int position){
        if(position == 0 || this.clients.size() == 0)
            return this.depot;
        return this.clients.get(position-1);
    }

    private Point getCurrent(int position){
        if(position == this.clients.size())
            return this.depot;
        return this.clients.get(position);
    }

    private Point getNext(int position){
        if(position == this.clients.size()-1)
            return this.depot;
        return this.clients.get(position+1);
    }

    public Client getClient(int position){
        if(!this.isPositionInsertionValide(position) || position == this.clients.size()) return null;
        return this.clients.get(position);
    }

    public InsertionClient getMeilleureInsertion(Client clientToAdd){
        if(isAjoutRealisable(clientToAdd)){
            InsertionClient best = new InsertionClient(this,clientToAdd,0);
            for(int i=1;i<=this.clients.size();i++){
                InsertionClient test = new InsertionClient(this,clientToAdd,i);
                if(test.isMouvementRealisable() && test.isMeilleur(best)){
                    best = test;
                }
            }
            return best;
        }
        return null;
    }

    public OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type){
        OperateurIntraTournee best = new IntraDeplacement();
        for(int i=0; i<clients.size(); i++) {
            for(int j=0; j<=clients.size(); j++) {
                OperateurIntraTournee op = OperateurLocal.getOperateurIntra(type, this, i, j);
                if(op != null && op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
    }

    public OperateurLocal getMeilleurOperateurInter(Tournee autreTournee, TypeOperateurLocal type){
        if(this.equals(autreTournee)) return this.getMeilleurOperateurIntra(type);

        OperateurInterTournees best = new InterDeplacement();
        for(int i=0; i<clients.size(); i++) {
            for(int j=0; j<=autreTournee.getClients().size(); j++) {
                OperateurInterTournees op = OperateurLocal.getOperateurInter(type, this, autreTournee, i, j);
                if(op != null && op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getDemandeTotal() {
        return demandeTotal;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public LinkedList<Client> getClients() {
        return clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournee tournee = (Tournee) o;
        return capacite == tournee.capacite && Objects.equals(depot, tournee.depot) && Objects.equals(clients, tournee.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacite, depot, clients);
    }

    @Override
    public String toString() {
        return "Tournee { " +
                "capacite = " + capacite +
                ", demandeTotal = " + demandeTotal +
                ", coutTotal = " + coutTotal +
                ", depot = " + depot +
                ", client = " + clients +
                '}';
    }

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            System.out.println("Instance lue avec success !");
            Instance instance = reader.readInstance();
            Tournee tournee = new Tournee(instance);
            instance.getClients().forEach(client -> {
                tournee.ajouterClient(client);
            });
            if(tournee.check()) System.out.println("Calcul OK");
            else System.out.println("Calcul KO");
            System.out.println(tournee.getDemandeTotal());
            System.out.println(tournee.getClients());
            int position = 5;
            if(tournee.isPositionInsertionValide(position)){
                System.out.println(tournee.getCurrent(position));
                System.out.println(tournee.getPrec(position));
            }
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
