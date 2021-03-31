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
    public boolean isTabou(OperateurLocal operateur) {
        if(!(operateur instanceof IntraEchange))
            return false;
        if(!this.tournee.equals(operateur.tournee))
            return false;
        if(!((clientI.equals(operateur.clientI) && clientJ.equals(operateur.clientJ)) ||
                (clientJ.equals(operateur.clientI) && clientI.equals(operateur.clientJ)))) {
            return false;
        }
        ListeTabou listeTabou = ListeTabou.getInstance();
        if(operateur.getDeltaCout() < listeTabou.getDeltaAspiration())
            return false;
        return true;
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
