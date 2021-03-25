package operateur;

import solution.Tournee;

public class InterDeplacement extends OperateurInterTournees {

    public InterDeplacement() {
        super();
    }

    public InterDeplacement(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, autreTournee, positionI, positionJ);
    }

    @Override
    public int evalDeltaCoutTournee() {
        return this.tournee.deltaCoutSuppression(positionI);
    }

    @Override
    public int evalDeltaCoutAutreTournee() {
        return this.autreTournee.deltaCoutInsertionInter(positionJ, this.tournee.getClient(positionI));
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doDeplacement(this);
    }

    @Override
    public String toString() {
        return "InterDeplacement { " +
                "tournee= " + tournee +
                ", cout= " + cout +
                ", autreTournee= " + autreTournee +
                ", positionI= " + positionI +
                ", positionJ= " + positionJ +
                " }";
    }
}
