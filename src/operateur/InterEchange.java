package operateur;

import solution.Tournee;

public class InterEchange extends OperateurInterTournees {

    public InterEchange() {
        super();
    }

    public InterEchange(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, autreTournee, positionI, positionJ);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doEchange(this);
    }

    @Override
    public int evalDeltaCoutTournee() {
        return this.tournee.deltaCoutRemplacementInter(positionI, this.clientI, this.clientJ);
    }

    @Override
    public int evalDeltaCoutAutreTournee() {
        return this.autreTournee.deltaCoutRemplacementInter(positionJ, this.clientJ, this.clientI);
    }
}
