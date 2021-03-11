package operateur;

import instance.reseau.Client;
import solution.Tournee;

public class InsertionClient extends Operateur {

    private Client client;
    private int position;

    public InsertionClient() {
        super();
        this.client = null;
        this.position = 0;
    }

    public InsertionClient(Tournee tournee) {
        super(tournee);
    }

    public InsertionClient(Tournee tournee, Client client, int position) {
        super(tournee);
        if(client != null) this.client = client;
        this.position = position;
        this.cout = this.evalDeltaCout();
    }

    @Override
    protected int evalDeltaCout() {
        return this.tournee.deltaCoutInsertion(this.position, client);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doInsertion(this);
    }

    public int getPosition() {
        return position;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "InsertionClient { " +
                "client= " + client +
                ", position= " + position +
                ", cout= " + this.cout +
                " }";
    }
}
