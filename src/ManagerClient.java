import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import CorbaApp.Center;
import CorbaApp.CenterHelper;


public class ManagerClient {
	static Center centerImpl;
	
	public static void main(String args[]) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming
			String name = "Hello";
			centerImpl = CenterHelper.narrow(ncRef.resolve_str(name));

			System.out.println("Obtained a handle on server object: " + centerImpl);
			System.out.println();
			System.out.println(centerImpl.sayHello());
			centerImpl.shutdown();

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

}