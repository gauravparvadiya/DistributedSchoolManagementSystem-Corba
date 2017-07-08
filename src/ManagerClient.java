import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.helper.LogHelper;
import com.users.Manager;

import CorbaApp.Center;
import CorbaApp.CenterHelper;

public class ManagerClient {

	static Center centerImplMTL, centerImplLVL, centerImplDDO;
	public HashMap<String, ArrayList<Manager>> managerHashMap;
	public ArrayList<Manager> mtl, lvl, ddo;
	JsonParser parser;
	static Logger logger = Logger.getLogger(ManagerClient.class);

	/**
	 * Default constructor that reads JSON file and puts data in manager hashmap
	 * 
	 * @throws FileNotFoundException
	 */
	public ManagerClient() throws FileNotFoundException {

		File f = new File("res/manager.json");
		Reader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));

		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(reader).getAsJsonArray();

		mtl = new ArrayList<Manager>();
		lvl = new ArrayList<Manager>();
		ddo = new ArrayList<Manager>();

		managerHashMap = new HashMap<String, ArrayList<Manager>>();

		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JsonObject object = (JsonObject) array.get(i);
				Manager manager = new Manager(object.get("managerID").toString(), object.get("fname").toString(),
						object.get("lname").toString());
				if (object.get("managerID").getAsString().substring(0, 3).equals("MTL")) {
					mtl.add(manager);
				} else if (object.get("managerID").getAsString().substring(0, 3).equals("LVL")) {
					lvl.add(manager);
				} else if (object.get("managerID").getAsString().substring(0, 3).equals("DDO")) {
					ddo.add(manager);
				}
			}
			managerHashMap.put("MTL", mtl);
			managerHashMap.put("LVL", lvl);
			managerHashMap.put("DDO", ddo);
		}
	}

	/**
	 * Method to connect the client to particular server
	 * 
	 * @param managerID
	 * @param fn
	 * @param ln
	 * @param address
	 * @param ph
	 * @param spec
	 * @param loc
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws ServerNotActiveException
	 * @throws InvalidName
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
	 * @throws CannotProceed
	 * @throws NotFound
	 */
	public static void connect_teacher(String managerID, String fn, String ln, String address, String ph, String spec,
			String loc) throws NotBoundException, ServerNotActiveException, InvalidName, NotFound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName {
		logger.info("Using createTRecord method.");
		if (managerID.substring(0, 3).equals("MTL")) {
			if (centerImplMTL.createTRecord(managerID, fn, ln, address, ph, spec, loc).equals("hi")) {
				System.out.println("Record created successfully. ");
				logger.info("Teacher record created successfully.");
			} else {
				System.out.println("Something went wrong!!! ");
				logger.error("Server returns error creating teacher record.");
			}
		} else if (managerID.substring(0, 3).equals("LVL")) {
			// registry = LocateRegistry.getRegistry(1212);
			// Center stub = (Center) registry.lookup("LVLServer");
			/*
			 * if (stub.createTRecord(fn, ln, address, ph, spec, loc,
			 * managerID)) {
			 * System.out.println("Record created successfully. ");
			 * logger.info("Teacher record created successfully."); } else {
			 * System.out.println("Something went wrong!!! ");
			 * logger.error("Server returns error creating teacher record."); }
			 */
		} else {
			// registry = LocateRegistry.getRegistry(1111);
			// Center stub = (Center) registry.lookup("DDOServer");
			/*
			 * if (stub.createTRecord(fn, ln, address, ph, spec, loc,
			 * managerID)) {
			 * System.out.println("Record created successfully. ");
			 * logger.info("Teacher record created successfully."); } else {
			 * System.out.println("Something went wrong!!! ");
			 * logger.error("Server returns error creating teacher record."); }
			 */
		}
	}

	/**
	 * Method to connect client to particular server
	 * 
	 * @param managerID
	 * @param fn
	 * @param ln
	 * @param courses
	 * @param status
	 * @param statusDate
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void connect_student(String managerID, String fn, String ln, String courses, String status,
			String statusDate) throws RemoteException, NotBoundException {
		logger.info("Using createSRecord method.");
		if (managerID.substring(0, 3).equals("MTL")) {
			if (centerImplMTL.createSRecord(managerID, fn, ln, courses, status, statusDate).equals("hi")) {
				System.out.println("Record created successfully.");
				logger.info("Student record created successfully.");
			} else {
				System.out.println("Something went wrong!!! ");
				logger.error("Server returns error creating student record.");
			}
		} else if (managerID.substring(0, 3).equals("LVL")) {
			/*
			 * registry = LocateRegistry.getRegistry(1212); Center stub =
			 * (Center) registry.lookup("LVLServer"); if (stub.createSRecord(fn,
			 * ln, courses, status, statusDate, managerID)) {
			 * System.out.println("Record created successfully.");
			 * logger.info("Student record created successfully."); } else {
			 * System.out.println("Something went wrong!!! ");
			 * logger.error("Server returns error creating student record."); }
			 */
		} else {
			/*
			 * registry = LocateRegistry.getRegistry(1111); Center stub =
			 * (Center) registry.lookup("DDOServer"); if (stub.createSRecord(fn,
			 * ln, courses, status, statusDate, managerID)) {
			 * System.out.println("Record created successfully.");
			 * logger.info("Student record created successfully."); } else {
			 * System.out.println("Something went wrong!!! ");
			 * logger.error("Server returns error creating student record."); }
			 */
		}
	}

	/**
	 * Method to connect client to particular server
	 * 
	 * @param managerID
	 * @param fieldname
	 * @param newvalue
	 * @param id
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void connect_edit(String managerID, String fieldname, String newvalue, String id)
			throws RemoteException, NotBoundException {
		if (managerID.substring(0, 3).equals("MTL")) {
			logger.debug("connected to registry 2964");
			logger.debug("connected to Montreal server");
			System.out.println(centerImplMTL.editRecord(id, fieldname, newvalue, managerID));
			if (centerImplMTL.editRecord(id, fieldname, newvalue, managerID).equals("hi")) {
				System.out.println("Record edited successfully.");
			} else {
				System.out.println("Error.");
			}
		} else if (managerID.substring(0, 3).equals("LVL")) {
			/*
			 * registry = LocateRegistry.getRegistry(1212);
			 * logger.debug("connected to registry 1212"); Center stub =
			 * (Center) registry.lookup("LVLServer");
			 * logger.debug("connected to Laval server"); if
			 * (stub.editRecord(id, fieldname, newvalue, managerID)) {
			 * System.out.println("Record edited successfully."); }
			 */
		} else {
			/*
			 * registry = LocateRegistry.getRegistry(1111);
			 * logger.debug("connected to registry 1111"); Center stub =
			 * (Center) registry.lookup("DDOServer");
			 * logger.debug("connected to Dollard-des-Ormeaux server"); if
			 * (stub.editRecord(id, fieldname, newvalue, managerID)) {
			 * System.out.println("Record edited successfully."); }
			 */
		}
		logger.info("Using editRecord method");
	}

	/**
	 * Method to connect client to particular server
	 * 
	 * @param managerID
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void connect_record_count(String managerID) throws RemoteException, NotBoundException {
		logger.info("Using getRecordCount method.");
		if (managerID.substring(0, 3).equals("MTL")) {
			logger.debug("connected to Montreal server");
			String reply = centerImplMTL.getRecordCounts(managerID);
			System.out.println("Count : \n" + reply);
		} else if (managerID.substring(0, 3).equals("LVL")) {
			/*
			 * registry = LocateRegistry.getRegistry(1212);
			 * logger.debug("connected to registry 1212"); Center stub =
			 * (Center) registry.lookup("LVLServer");
			 * logger.debug("connected to Laval server"); String reply =
			 * stub.getRecordCounts(managerID); System.out.println("Count : \n"
			 * + reply);
			 */ } else {
			/*
			 * registry = LocateRegistry.getRegistry(1111);
			 * logger.debug("connected to registry 1111"); Center stub =
			 * (Center) registry.lookup("DDOServer");
			 * logger.debug("connected to Dollard-des-Ormeaux server"); String
			 * reply = stub.getRecordCounts(managerID);
			 * System.out.println("Count : \n" + reply);
			 */ }
	}

	/**
	 * Method to validate the edit method parameters
	 * 
	 * @param id
	 * @param fieldName
	 * @param newValue
	 * @return
	 */
	public static boolean validate_edit(String id, String fieldName, String newValue) {

		if (id.length() == 8) {
			if (id.substring(0, 3).equals("MTR") || id.substring(0, 3).equals("LTR")
					|| id.substring(0, 3).equals("DTR")) {
				logger.info("Starting to edit teacher.");
				if (fieldName.equals("address") || fieldName.equals("location") || fieldName.equals("phone")) {
					return true;
				} else {
					System.out.println("Invalid Field");
					return false;
				}
			} else if (id.substring(0, 3).equals("MSR") || id.substring(0, 3).equals("LSR")
					|| id.substring(0, 3).equals("DSR")) {
				logger.info("Starting to edit student.");
				if (fieldName.equals("coursesRegistered") || fieldName.equals("status")
						|| fieldName.equals("statusDueDate")) {
					return true;
				} else {
					System.out.println("Invalid Field");
					return false;
				}
			} else {
				return true;
			}
		} else {
			System.out.println("Please enter valid record ID");
			return false;
		}
	}

	/**
	 * Method to identify if the manager is exists or not
	 * 
	 * @param managerClient
	 * @param managerID
	 * @return
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Boolean managerIdentification(ManagerClient managerClient, String managerID) {
		if (managerID.substring(0, 3).equals("MTL")) {
			for (int i = 0; i < managerClient.managerHashMap.get("MTL").size(); i++) {

				Manager manager = managerClient.managerHashMap.get("MTL").get(i);
				if (manager.getManagerID().substring(1, 8).equals(managerID)) {

					String fname = manager.getFname();
					String lname = manager.getLname();
					System.out.println("Welcome , " + fname.substring(1, fname.length() - 1) + " "
							+ lname.substring(1, lname.length() - 1));
					return true;
				}
			}
		} else if (managerID.substring(0, 3).equals("LVL")) {
			for (int i = 0; i < managerClient.managerHashMap.get("LVL").size(); i++) {
				Manager manager = managerClient.managerHashMap.get("LVL").get(i);
				if (manager.getManagerID().substring(1, 8).equals(managerID)) {
					String fname = manager.getFname();
					String lname = manager.getLname();
					System.out.println("Welcome , " + fname.substring(1, fname.length() - 1) + " "
							+ lname.substring(1, lname.length() - 1));
					return true;
				}
			}
		} else if (managerID.substring(0, 3).equals("DDO")) {
			for (int i = 0; i < managerClient.managerHashMap.get("DDO").size(); i++) {
				Manager manager = managerClient.managerHashMap.get("DDO").get(i);
				if (manager.getManagerID().substring(1, 8).equals(managerID)) {

					String fname = manager.getFname();
					String lname = manager.getLname();
					System.out.println("Welcome , " + fname.substring(1, fname.length() - 1) + " "
							+ lname.substring(1, lname.length() - 1));
					return true;
				}
			}
		} else {
			System.out.println("Manager ID should start with MTL/LVL/DDO");
			return false;
		}
		return false;
	}

	public static void main(String args[]) {
		try {
			String args1 = "-ORBInitialPort 1050 -ORBInitialHost localhost";
			String arg[] = args1.split(" ");
			ORB orb = ORB.init(arg, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String mtl = "MTLServer";
			centerImplMTL = CenterHelper.narrow(ncRef.resolve_str(mtl));
			/*
			 * String lvl = "LVLServer"; centerImplLVL =
			 * CenterHelper.narrow(ncRef.resolve_str(lvl)); String ddo =
			 * "DDOServer"; centerImplDDO =
			 * CenterHelper.narrow(ncRef.resolve_str(ddo));
			 */ ManagerClient managerClient = new ManagerClient();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String managerID;
			do {
				System.out.println("Enter the Manager ID : ");
				managerID = reader.readLine();

				if (!managerID.equals("") && managerID.length() == 7
						&& managerClient.managerIdentification(managerClient, managerID)) {
					LogHelper helper = new LogHelper();
					helper.setupLogFile("log/" + managerID + ".log");
					logger.debug("connected to manager client.");
					do {
						System.out.println("\n 1. Create Teacher ");
						System.out.println(" 2. Create Student ");
						System.out.println(" 3. Get Record Counts ");
						System.out.println(" 4. Edit Record ");
						System.out.println(" 5. Exit");

						reader = new BufferedReader(new InputStreamReader(System.in));
						System.out.println("\n Enter your choice : ");

						Scanner s = new Scanner(System.in);
						String status;
						String firstName, lastName, address, phone, spec, loc, id, statusDate, fieldName, temp;
						String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
						String courses;
						String newValue;
						Pattern pattern;
						Matcher matcher;

						switch (reader.readLine()) {
						case "1":
							logger.info("Starting to create teacher.");
							System.out.println("Enter Teacher Information");
							System.out.println("First Name : ");
							firstName = s.nextLine();
							System.out.println("Last Name : ");
							lastName = s.nextLine();
							System.out.println("Address : ");
							address = s.nextLine();
							System.out.println("Phone : ");
							phone = s.nextLine();
							System.out.println("Specialization : ");
							spec = s.nextLine();
							System.out.println("Location : ");
							loc = s.nextLine();
							if (!firstName.equals("") && !lastName.equals("") && !address.equals("")
									&& !phone.equals("") && !spec.equals("") && !loc.equals("")) {
								connect_teacher(managerID, firstName, lastName, address, phone, spec, loc);
							} else
								System.out.println("Please enter all values.");
							break;
						case "2":
							logger.info("Starting to create student.");
							System.out.println("Enter Student Information");
							System.out.println("First Name : ");
							firstName = s.nextLine();
							System.out.println("Last Name : ");
							lastName = s.nextLine();
							System.out.println("Courses registered (separated with comma) : ");
							// temp = s.nextLine();
							// courses = temp.split(",");
							courses = s.nextLine();
							System.out.println("Status : (active & deactive)");
							status = s.nextLine();
							// s.nextLine();
							System.out.println("Status Date : (DD/MM/YYYY)");
							statusDate = s.nextLine();
							pattern = Pattern.compile(DATE_PATTERN);
							matcher = pattern.matcher(statusDate);
							if (!firstName.equals("") && !lastName.equals("") && !status.equals("")
									&& !statusDate.equals("")) {
								if (status.equals("active") || status.equals("deactive")) {
									if (matcher.matches() && (status.equals("active") || status.equals("deactive"))) {
										connect_student(managerID, firstName, lastName, courses, status, statusDate);
									} else
										System.out.println("check if you have entered correct status or date or ID");
								} else {
									System.out.println("Invalid status!");
								}
							} else
								System.out.println("Field can not be blank");
							break;
						case "3":
							connect_record_count(managerID);
							break;
						case "4":
							System.out.println("Enter information to edit : ");
							System.out.println("ID : (e.g. MTR00001/MSR10001)");
							id = s.nextLine();
							System.out.println("Field Name : ");
							fieldName = s.nextLine();
							System.out.println("New Value : ");
							newValue = s.nextLine();
							if (!newValue.equals("")) {
								if (validate_edit(id, fieldName, newValue)) {
									connect_edit(managerID, fieldName, newValue, id);
								}
							} else {
								System.out.println("Please enter all fields.");
							}
							break;
						case "5":
							File file = new File("log/" + managerID + ".log");
							file.delete();
							System.out.println("Bye Bye!!!");
							System.exit(0);
							break;
						default:
							System.out.println("Invalid selection. Please select from given options.");
							break;
						}

					} while (!reader.readLine().equals("5"));
				} else {
					System.out.println("Manager not found.");
				}
			} while (!managerID.equals("exit"));

			centerImplMTL.shutdown();
			centerImplLVL.shutdown();
			centerImplDDO.shutdown();

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

}