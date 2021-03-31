package operateur;

import instance.Instance;

import java.util.concurrent.LinkedBlockingQueue;

public class ListeTabou {
    private static ListeTabou instance;
    private static final int capacite = 80;
    private int deltaAspiration;
    private LinkedBlockingQueue<OperateurLocal> listeOperateurs;

    private ListeTabou() {
        this.listeOperateurs = new LinkedBlockingQueue<>(capacite);
        this.deltaAspiration = 0;
    }

    public static ListeTabou getInstance(){
        if (instance == null) {
            instance = new ListeTabou();
        }
        return instance;
    }

    public boolean add(OperateurLocal operateur){
        if (operateur==null) {
            return false;
        }
        if (!listeOperateurs.offer(operateur)) {
            listeOperateurs.poll();
        }
        return listeOperateurs.offer(operateur);
    }

    public boolean isTabou(OperateurLocal operateur){
        for(OperateurLocal op : this.listeOperateurs){
            if(operateur.isTabou(op)){
                return true;
            }
        }
        return false;
    }

    public void vider(){
        this.listeOperateurs.clear();
    }

    public int getDeltaAspiration() {
        return deltaAspiration;
    }

    public void setDeltaAspiration(int deltaAspiration) {
        this.deltaAspiration = deltaAspiration;
    }

    @Override
    public String toString() {
        return "ListeTabou{" + "listeOperateur=" + listeOperateurs + '}';
    }
}
