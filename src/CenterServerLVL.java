
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CorbaApp.Center;
import CorbaApp.CenterHelper;
import CorbaApp.CenterPOA;

class CenterServerLVLImplementation extends CenterPOA {
	private ORB orb;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	/*
	 * // implement sayHello() method public Boolean createTRecord(){
	 * System.out.println("hii teacher"); return null; }
	 * 
	 * public Boolean createSRecord() { System.out.println("hii studnet");
	 * return null; }
	 */

	public String sayHello() {
		return "\nHello world !!\n";
	}

	// implement shutdown() method
	public void shutdown() {
		orb.shutdown(false);
	}

	@Override
	public String createTRecord(String managerID, String fname, String lastName, String address, String phone,
			String specialization, String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSRecord(String managerID, String fname, String lastName, String courseRegistered, String status,
			String statusDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRecordCounts(String managerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editRecord(String managerID, String recordID, String fieldName, String newValue) {
		// TODO Auto-generated method stub
		return null;
	}

}

public class CenterServerLVL {

	public static void main(String args[]) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			CenterServerLVLImplementation centerServerLVLImplementation = new CenterServerLVLImplementation();
			centerServerLVLImplementation.setORB(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(centerServerLVLImplementation);
			Center href = CenterHelper.narrow(ref);

			// get the root naming context
			// NameService invokes the name service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			String name = "LVLServer";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("HelloServer ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("HelloServer Exiting ...");

	}
}