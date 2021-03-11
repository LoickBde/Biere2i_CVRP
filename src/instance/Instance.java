package instance;

import instance.reseau.Client;
import instance.reseau.Depot;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class Instance {
    private String nom;
    private int capacite;
    private Depot depot;
    private Map<Integer, Client> clients;

    public Instance(String nom, int capacite, Depot depot) {
        this.nom = nom;
        this.capacite = capacite;
        this.depot = depot;
        this.clients = new LinkedHashMap<>();
    }

    public boolean ajouterClient(Client clientToAdd) {
        if(clientToAdd == null) return false;

        this.clients.put(clientToAdd.getId(), clientToAdd);
        this.depot.ajouterRoute(clientToAdd);

        if(!clientToAdd.ajouterRoute(this.depot)) return false;

        for(Client client: this.clients.values()) {
            if(!clientToAdd.ajouterRoute(client)) return false;
            client.ajouterRoute(clientToAdd);
        }
        return true;
    }

    public int getNbClients() {
        return this.clients.size();
    }

    public Client getClientById(int id) {
        return this.clients.get(id);
    }

    public LinkedList<Client> getClients() {
        return new LinkedList<>(this.clients.values());
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public Depot getDepot() {
        return depot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return capacite == instance.capacite && Objects.equals(nom, instance.nom) && Objects.equals(depot, instance.depot) && Objects.equals(clients, instance.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, capacite, depot, clients);
    }

    @Override
    public String toString() {
        return "Instance { " +
                "nom= '" + nom + '\'' +
                ", capacite= " + capacite +
                ", depot= " + depot +
                ", clients= " + clients +
                " }";
    }
}