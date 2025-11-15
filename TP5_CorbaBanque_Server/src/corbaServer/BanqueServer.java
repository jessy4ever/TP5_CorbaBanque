package corbaServer;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import service.BanqueImpl;

public class BanqueServer {
    public static void main(String[] args) {
        try {
        
            ORB orb = ORB.init(args, null);
            System.out.println("ORB initialisé.");

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            System.out.println("POA activé.");

            BanqueImpl banqueServant = new BanqueImpl();
            System.out.println("Servant BanqueImpl créé.");

            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(banqueServant);
            System.out.println("Référence CORBA du servant obtenue.");

            NamingContextExt namingContext = NamingContextExtHelper.narrow(
                orb.resolve_initial_references("NameService")
            );
            
            String name = "BanqueService";
            NameComponent[] path = namingContext.to_name(name);
            namingContext.rebind(path, ref);
            System.out.println("Service 'BanqueService' enregistré dans le Naming Service.");

            System.out.println("Serveur en attente de requêtes...");
            orb.run(); 

        } catch (Exception e) {
            System.err.println("Erreur serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
