package operateur;

import instance.reseau.Client;
import solution.Tournee;

public abstract class OperateurLocal extends Operateur {
    protected Client clientI;
    protected Client clientJ;
    protected int positionI;
    protected int positionJ;

    public OperateurLocal() {
        super();
        this.clientI = null;
        this.clientJ = null;
        positionI = -1;
        positionJ = -1;
    }

    public OperateurLocal(Tournee tournee, int positionI, int positionJ) {
        super(tournee);
        this.positionI = positionI;
        this.positionJ = positionJ;
        this.clientI = tournee.getClient(positionI);
        this.clientJ = tournee.getClient(positionJ);
    }

    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch (type){
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement();
            case INTRA_ECHANGE:
                return new IntraEchange();
            case INTER_DEPLACEMENT:
                return new InterDeplacement();
            case INTER_ECHANGE:
                return new InterEchange();
            default:
                return null;
        }
    }

    public static OperateurIntraTournee getOperateurIntra(TypeOperateurLocal type, Tournee tournee, int positionI, int positionJ) {
        switch (type){
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement(tournee, positionI, positionJ);
            case INTRA_ECHANGE:
                return new IntraEchange(tournee, positionI, positionJ);
            default:
                return null;
        }
    }

    public static OperateurInterTournees getOperateurInter(TypeOperateurLocal type,Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        switch (type){
            case INTER_DEPLACEMENT:
                return new InterDeplacement(tournee, autreTournee, positionI, positionJ);
            case INTER_ECHANGE:
                return new InterEchange(tournee, autreTournee, positionI, positionJ);
            default:
                return null;
        }
    }

    public abstract boolean isTabou(OperateurLocal operateur);

    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    protected void setClientJ(Client clientJ) {
        if(clientJ != null)
            this.clientJ = clientJ;
    }

    @Override
    public String toString() {
        return "OperateurLocal { " +
                "clientI= " + clientI +
                ", clientJ= " + clientJ +
                ", positionI= " + positionI +
                ", positionJ= " + positionJ +
                " }";
    }
}
