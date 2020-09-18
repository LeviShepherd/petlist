import java.util.List;
import java.util.Scanner;

import controller.PetHelper;
import model.Pet;

public class StartProgram {

	static Scanner in = new Scanner(System.in);
	static PetHelper ph = new PetHelper();
	
	private static void addPet() {
		System.out.print("Enter the name of a pet: ");
		String name = in.nextLine();
		System.out.print("Enter the type of a pet: ");
		String type = in.nextLine();
		Pet toAdd = new Pet(name, type);
		ph.insertPet(toAdd);
	}
	
	private static void deletePet() {
		System.out.print("Enter the name of the pet to delete: ");
		String name = in.nextLine();
		System.out.print("Enter the type of pet to delete: ");
		String type = in.nextLine();
		Pet toDelete = new Pet(name, type);
		ph.deletePet(toDelete);
	}
	
	private static void editPet() {
		
		System.out.println("How would you like to search? ");
		System.out.println("1 : Search by Name");
		System.out.println("2 : Search by Type");
		int searchBy = in.nextInt();
		in.nextLine();
		List<Pet> foundPets;
		if (searchBy == 1) {
			System.out.print("Enter the name of the pet: ");
			String petName = in.nextLine();
			foundPets = ph.searchForPetByName(petName);
		} else {
			System.out.print("Enter the type of the pet: ");
			String petType = in.nextLine();
			foundPets = ph.searchForPetByType(petType);

		}

		if (!foundPets.isEmpty()) {
			System.out.println("Found Results.");
			for (Pet p : foundPets) {
				System.out.println(p.getId() + " : " + p.toString());
			}
			System.out.print("Which ID to edit: ");
			int idToEdit = in.nextInt();

			Pet toEdit = ph.searchForPetById(idToEdit);
			System.out.println("Retrieved " + toEdit.getName() + " from " + toEdit.getType());
			System.out.println("1 : Update Name");
			System.out.println("2 : Update Type");
			int update = in.nextInt();
			in.nextLine();

			if (update == 1) {
				System.out.print("New Name: ");
				String newName = in.nextLine();
				toEdit.setName(newName);
			} else if (update == 2) {
				System.out.print("New Type: ");
				String newType = in.nextLine();
				toEdit.setType(newType);
			}

			ph.updatePet(toEdit);

		} else {
			System.out.println("---- No results found");
		}
	}
	
	private static void runMenu() {
		boolean goAgain = true;
		System.out.println("--- Welcome to the Pet Emporium! ---");
		while (goAgain) {
			System.out.println("*  Select an item:");
			System.out.println("*  1 -- Add a pet");
			System.out.println("*  2 -- Edit a pet");
			System.out.println("*  3 -- Delete a pet");
			System.out.println("*  4 -- View the list of pets");
			System.out.println("*  5 -- Exit");
			System.out.println("------------------------------------");
			System.out.print("*  Your selection: ");
			int selection = in.nextInt();
			in.nextLine();

			if (selection == 1) {
				addPet();
			} else if (selection == 2) {
				editPet();
			} else if (selection == 3) {
				deletePet();
			} else if (selection == 4) {
				viewPets();
			} else {
				ph.cleanUp();
				System.out.println("   Goodbye!   ");
				goAgain = false;
			}

		}
	}
	
	private static void viewPets() {
		List<Pet> allPets = ph.showAllPets();
		for(Pet singlePet : allPets) {
			System.out.println(singlePet.returnPetDetails());
		}
	}
	
	public static void main(String[] args) {
		runMenu();
	}
	
	
}
