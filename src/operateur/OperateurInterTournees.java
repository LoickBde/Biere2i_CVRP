package operateur;

import solution.Tournee;

public abstract class OperateurInterTournees extends OperateurLocal {
    protected Tournee autreTournee;
    private int deltaCoutTournee;
    private int deltaCoutAutreTournee;

    public OperateurInterTournees() {
        super();
    }

    public OperateurInterTournees(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        this.setClientJ(autreTournee.getClient(positionJ));
        this.deltaCoutTournee = this.evalDeltaCoutTournee();
        this.deltaCoutAutreTournee = this.evalDeltaCoutAutreTournee();
        this.cout = this.evalDeltaCout();
    }

    @Override
    protected int evalDeltaCout() {
        int cout1 = this.deltaCoutTournee;
        int cout2 = this.deltaCoutAutreTournee;
        if(cout1 == Integer.MAX_VALUE || cout2 == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return cout1 + cout2;
    }

    public abstract int evalDeltaCoutTournee();

    public abstract int evalDeltaCoutAutreTournee();

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
    }

    public Tournee getAutreTournee() {
        return autreTournee;
    }

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
