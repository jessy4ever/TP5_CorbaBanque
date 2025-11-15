package service;

import corbaBanque.*;
import java.util.ArrayList;
import java.util.List;

public class BanqueImpl extends IBanqueRemotePOA {
    private List<Compte> comptes = new ArrayList<>();

    @Override
    public void creerCompte(Compte cpte) {
        comptes.add(cpte);
        System.out.println("Compte créé: " + cpte.code);
    }

    @Override
    public void verser(float mt, int code) {  
        for (Compte c : comptes) {
            if (c.code == code) {
                c.solde += mt;
                System.out.println("Versement effectué: " + mt + " sur compte " + code);
                return;
            }
        }
        System.out.println("Compte " + code + " introuvable pour versement.");
    }

    @Override
    public void retirer(float mt, int code) {  
        for (Compte c : comptes) {
            if (c.code == code) {
                if (c.solde >= mt) {
                    c.solde -= mt;
                    System.out.println("Retrait effectué: " + mt + " du compte " + code);
                } else {
                    System.out.println("Solde insuffisant pour retrait.");
                }
                return;
            }
        }
        System.out.println("Compte " + code + " introuvable pour retrait.");
    }

    @Override
    public Compte getCompte(int code) { 
        for (Compte c : comptes) {
            if (c.code == code) {
                System.out.println("Compte trouvé: " + c.code + ", solde: " + c.solde);
                return c;
            }
        }
        System.out.println("Compte " + code + " introuvable.");
        Compte vide = new Compte();
        vide.code = -1;
        vide.solde = 0.0f;
        return vide;
    }

    @Override
    public Compte[] getComptes() {
        System.out.println("Liste des comptes demandée (" + comptes.size() + " comptes).");
        Compte[] tableau = new Compte[comptes.size()];
        tableau = comptes.toArray(tableau);
        return tableau;
    }

    @Override
    public double conversion(float mt) {
        double tauxEuroVersDinar = 3.3;
        double resultat = mt * tauxEuroVersDinar;
        System.out.println("Conversion: " + mt + " EUR = " + resultat + " TND");
        return resultat;
    }
}







