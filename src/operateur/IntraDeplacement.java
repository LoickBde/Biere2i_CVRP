package operateur;

import solution.Tournee;

public class IntraDeplacement extends OperateurIntraTournee {

    public IntraDeplacement() {
        super();
    }

    public IntraDeplacement(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
    }

    @Override
    protected int evalDeltaCout() {
        return tournee.deltaCoutDeplacement(positionI, positionJ);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doDeplacement(this);
    }
}
