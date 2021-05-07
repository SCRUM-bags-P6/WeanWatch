package WeanWatch.model;

import java.util.ArrayList;

public class CaseHandler {
	private static CaseHandler instance = null;

	private ArrayList<Case> cases = new ArrayList<Case>();

	private CaseHandler(){
	}

	public CaseHandler getInstance(){
		if (CaseHandler.instance == null) {
            // Create an instance and store it
            CaseHandler.instance = new CaseHandler();
        }
        // Return the stored instance
        return CaseHandler.instance;
	}

	public void addCase(String name, String description, Indicator[] characteristics){
		Case patientCase = new Case(name, description, characteristics);
		this.cases.add(patientCase);
	}

	public void removeCase(String caseName) {
		this.cases.removeIf(x -> x.getName().equals(caseName));
	}

	public Case getCase(String caseName){
		for(Case tempCase : this.cases){
			if(tempCase.getName().equals(caseName)){
				return tempCase;
			}
		}
		return null;
	}

	public ArrayList<Case> getCases() {
		return this.cases;
	}




	
}
