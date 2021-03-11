package operateur;

import solution.Tournee;

public abstract class Operateur {
    protected Tournee tournee;
    protected int cout;

    public Operateur() {
        tournee = null;
        this.cout = Integer.MAX_VALUE;
    }

    public Operateur(Tournee tournee) {
        this();
        if(tournee != null) this.tournee = tournee;
    }

    public boolean isMouvementRealisable(){
        return this.cout != Integer.MAX_VALUE;
    }

    public boolean isMeilleur(Operateur op){
        return this.cout < op.cout;
    }

    protected abstract int evalDeltaCout();

    protected abstract boolean doMouvement();

    public boolean doMouvementIfRealisable(){
        if(!isMouvementRealisable()) return false;
        return doMouvement();
    }

    public boolean isMouvementAmeliorant(){
        return this.cout < 0;
    }

    public int getDeltaCout(){
        return cout;
    }

    @Override
    public String toString() {
        return "Operateur { " +
                "tournee= " + tournee +
                ", cout= " + cout +
                " }";
    }
}
