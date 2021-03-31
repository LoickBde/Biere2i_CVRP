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
    public boolean isTabou(OperateurLocal operateur) {
        if(!(operateur instanceof IntraDeplacement))
            return false;
        if(!this.tournee.equals(operateur.tournee))
            return false;
        if(!this.clientI.equals(operateur.clientI))
            return false;
        ListeTabou listeTabou = ListeTabou.getInstance();
        if(operateur.getDeltaCout() < listeTabou.getDeltaAspiration())
            return false;
        return true;
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
