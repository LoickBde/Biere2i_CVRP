package operateur;

import solution.Tournee;

public abstract class OperateurIntraTournee extends OperateurLocal{

    public OperateurIntraTournee() {
        super();
    }

    public OperateurIntraTournee(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.cout = this.evalDeltaCout();
    }
}
