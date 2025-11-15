package corbaClient;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            System.out.println("ORB Client initialisé.");

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(objRef);
            System.out.println("Naming Service trouvé.");

            String name = "BanqueService";
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(
                namingContext.resolve_str(name)
            );
            System.out.println("Service 'BanqueService' localisé et narrowé.\n");

            System.out.println("=== Test des opérations CORBA ===\n");

            System.out.println("--- Création de comptes ---");
            Compte c1 = new Compte();
            c1.code = 101;
            c1.solde = 1000.0f;
            banque.creerCompte(c1);
            System.out.println("✓ Compte créé: code=" + c1.code + ", solde=" + c1.solde);

            Compte c2 = new Compte();
            c2.code = 102;
            c2.solde = 2500.0f;
            banque.creerCompte(c2);
            System.out.println("✓ Compte créé: code=" + c2.code + ", solde=" + c2.solde);

            Compte c3 = new Compte();
            c3.code = 103;
            c3.solde = 500.0f;
            banque.creerCompte(c3);
            System.out.println("✓ Compte créé: code=" + c3.code + ", solde=" + c3.solde + "\n");

            System.out.println("--- Versement ---");
            banque.verser(300.0f, 101);
            System.out.println("✓ Versé 300 EUR sur compte 101\n");

            System.out.println("--- Retrait ---");
            banque.retirer(150.0f, 102);
            System.out.println("✓ Retiré 150 EUR du compte 102\n");

            System.out.println("--- Consultation d'un compte ---");
            Compte consulte = banque.getCompte(101);
            System.out.println("✓ Compte " + consulte.code + ": solde = " + consulte.solde + " EUR\n");

            System.out.println("--- Liste de tous les comptes ---");
            Compte[] tousLesComptes = banque.getComptes();
            for (int i = 0; i < tousLesComptes.length; i++) {
                Compte c = tousLesComptes[i];
                System.out.println("  Compte " + c.code + ": " + c.solde + " EUR");
            }
            System.out.println();

            System.out.println("--- Conversion de devises ---");
            float montantEuro = 100.0f;
            double montantDinar = banque.conversion(montantEuro);
            System.out.println("✓ " + montantEuro + " EUR = " + montantDinar + " TND\n");

            System.out.println("=== Tous les tests réussis ! ===");

        } catch (Exception e) {
            System.err.println("Erreur client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

