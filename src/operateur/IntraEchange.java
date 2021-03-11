package operateur;

import solution.Tournee;

public class IntraEchange extends OperateurIntraTournee {

    public IntraEchange() {
        super();
    }

    public IntraEchange(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
    }

    @Override
    protected int evalDeltaCout() {
        return this.tournee.deltaCoutEchange(positionI, positionJ);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doEchange(this);
    }

    @Override
    public String toString() {
        return "IntraEchange { " +
                "tournee= " + tournee +
                ", cout= " + cout +
                ", positionI= " + positionI +
                ", positionJ= " + positionJ +
                " }";
    }
}
