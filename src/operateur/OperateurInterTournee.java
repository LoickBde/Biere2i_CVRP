package operateur;

import solution.Tournee;

public abstract class OperateurInterTournee extends OperateurLocal {
    private Tournee autreTournee;
    private int deltaCoutTournee;
    private int deltaCoutAutreTournee;

    public OperateurInterTournee() {
        super();
    }

    public OperateurInterTournee(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        this.cout = this.evalDeltaCout();
        this.setClientJ(autreTournee.getClient(positionJ));
    }

    @Override
    protected int evalDeltaCout() {
        return this.evalDeltacCoutTournee() + this.evalDeltaCoutAutreTournee();
    }

    public abstract int evalDeltacCoutTournee();

    public abstract int evalDeltaCoutAutreTournee();

    @Override
    public String toString() {
        return "OperateurInterTournee { " +
                "tournee= " + tournee +
                ", cout= " + cout +
                ", autreTournee= " + autreTournee +
                ", deltaCoutTournee= " + deltaCoutTournee +
                ", deltaCoutAutreTournee= " + deltaCoutAutreTournee +
                " } ";
    }
}
