import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.helper.LogHelper;

import com.users.Manager;

import CorbaApp.Center;
import CorbaApp.CenterHelper;

public class ManagerClient {

	static Center centerImpl;
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
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("MTL");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "MTLServer";
			centerImpl = CenterHelper.narrow(ncRef.resolve_str(name));
			ManagerClient managerClient = new ManagerClient();
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
						Integer status;
						String firstName, lastName, address, phone, spec, loc, id, statusDate, fieldName, temp;
						String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
						String[] courses;
						String[] newValue = new String[5];
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
								// connect_teacher(managerID, firstName,
								// lastName, address, phone, spec, loc);
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
							temp = s.nextLine();
							courses = temp.split(",");
							System.out.println("Status : (1 for active & 0 for deactive)");
							String status1 = s.nextLine();
							// s.nextLine();
							System.out.println("Status Date : (DD/MM/YYYY)");
							statusDate = s.nextLine();
							pattern = Pattern.compile(DATE_PATTERN);
							matcher = pattern.matcher(statusDate);
							if (!firstName.equals("") && !lastName.equals("") && courses.length != 0
									&& !status1.equals("") && !statusDate.equals("")) {
								if (status1.equals("0") || status1.equals("1")) {
									status = Integer.parseInt(status1);
									if (matcher.matches() && (status.equals(0) || status.equals(1))) {
										// connect_student(managerID, firstName,
										// lastName, courses, status,
										// statusDate);
									} else
										System.out.println("check if you have entered correct status or date or ID");
								} else {
									System.out.println("Invalid status!");
								}
							} else
								System.out.println("Field can not be blank");
							break;
						case "3":
							// connect_record_count(managerID);
							break;
						case "4":
							System.out.println("Enter information to edit : ");
							System.out.println("ID : (e.g. MTR00001/MSR10001)");
							id = s.nextLine();
							System.out.println("Field Name : ");
							fieldName = s.nextLine();
							System.out.println("New Value : ");
							temp = s.nextLine();
							if (!temp.equals("")) {
								if (temp.contains(",")) {
									newValue = temp.split(",");
								} else
									newValue[0] = temp;
								// if (validate_edit(id, fieldName, newValue)) {
								// connect_edit(managerID, fieldName, newValue,
								// id);
								// }
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

			centerImpl.shutdown();

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

}