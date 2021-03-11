package operateur;

import solution.Tournee;

public class FusionTournees extends Operateur {
    private Tournee aFusionner;

    public FusionTournees(){
        super();
        this.aFusionner = null;
    }

    public FusionTournees(Tournee tournee, Tournee aFusionner) {
        super(tournee);
        if(aFusionner != null) {
            this.aFusionner = aFusionner;
            this.cout = this.evalDeltaCout();
        }
    }

    public Tournee getaFusionner() {
        return aFusionner;
    }

    @Override
    protected int evalDeltaCout() {
        return this.tournee.deltaCoutFusion(this.aFusionner);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doFusion(this);
    }
}
