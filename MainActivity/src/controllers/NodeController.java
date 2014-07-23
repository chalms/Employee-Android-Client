package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.json.JSONException;

import main.metrics.MainActivity;
import models.nodes.FireNode;
import models.nodes.ReportTask;
import views.ListItemContent;
import android.content.Context;

public class NodeController {

	public ArrayList <FireNode> childNodes;
	public Stack <FireNode> parentNodes;
	public HashMap <String, HashMap<String, String[]>> myDict;
	FireNode root; 
	public String rootname; 
	boolean set;
	MainActivity context; 
	

	// This class controls the tree of FireNode elements and defines the users current node 
	// in the application. All ancestor nodes from the users current node, are pushed into a Stack, 
	// called 'parentNodes', which displays the headers of parent rows in the list (the rows in Red)
	// the top element of the 'parentNodes' stack is the users current node in the application 
	// The Arraylist, 'childnodes' displays all children of the top parentNode element, and the current clickable 
	// Items in the checklist - items in white
	
	public NodeController(FireNode mikesList, MainActivity c) {
		//Create the Stack and Array list by passing this constructor the root node
		System.out.println("Node Controller set with ---> " + mikesList.getName());
		childNodes = mikesList.childList();
		if (childNodes == null) {
			return; 
		}
		context = c; 
		
		myDict = new HashMap <String, HashMap<String, String[]>>();
		parentNodes = new Stack <FireNode> ();
		this.root = mikesList; 
		
		System.out.println("Pushed Parent Node!");
		parentNodes.push(mikesList);
		
	}

	public void clearModificationDictionary(){
		for (Entry<String, HashMap<String, String[]>> entry : myDict.entrySet()){
			HashMap<String, String[]> elementAndResults = entry.getValue();
			for(Entry<String, String[]> nextEntry : elementAndResults.entrySet()){
				((String[]) nextEntry.getValue())[0] = "";
				((String[]) nextEntry.getValue())[1] = "";
			}
		}
	}

	public void addToDictionary(String id){
		System.out.println("User report ID: " + parentNodes.peek().getNodeID());
		System.out.println(id);
		String userReportID = parentNodes.peek().getNodeID();
		
		
		ReportTask reportTask = getReportTaskById(id);
		System.out.println("report task: " + reportTask.toString());
		
		HashMap<String, String[]> secondaryMap = new HashMap<String, String[]>();
		
		if (myDict != null){
			if (myDict.containsKey(userReportID)){
				System.out.println("myDict already contains the userReportID");
				secondaryMap = myDict.get(userReportID);
			}
		} else {
			System.out.println("need to create new hashmap as myDict == null");
			myDict  = new HashMap <String, HashMap<String, String[]>>();
		}

		if (reportTask != null){		
			String [] ishYaBoy = new String[2];
			System.out.println("Report task test result: " + reportTask.getTestResult());
			System.out.println("Report task test note: " + reportTask.getTestNote());
			ishYaBoy[0] = new String(reportTask.getTestResult());
			ishYaBoy[1] = new String(reportTask.getTestNote());
			System.out.println("Report task test note: " + reportTask.getTestNote());
			String reportTaskID = reportTask.getNodeID();
			System.out.println("Report task id: " + reportTaskID); 
			secondaryMap.put(reportTaskID,  ishYaBoy);
			System.out.println("reportTask and string array placed in secondary map");
			myDict.put(userReportID, secondaryMap);
			System.out.println("user report id and secondary map placed into myDict");
			try {
				context.getRouter().saveDictionary(myDict);
			} catch (JSONException e) {
				System.out.println("Updates could not be saved...");
				e.printStackTrace();
			}

		}
		
	}

	public void saveReportTaskById(Context context, String id){
		updateAndSave(context, getReportTaskById(id)); 
	}

	public ReportTask getReportTaskById(String id){
		FireNode element = getChildByID(id);
		boolean tag = element.getTag().equals("Leaf");
		if (tag) {
			return (ReportTask) element;
		} else {
			System.out.println("Repor task could not be found!");
		}
		return null;
	}

	public void updateAndSave(Context context, ReportTask inspectionTest){
		if (inspectionTest != null) {			

		} else { 
			System.out.println(parentNodes.peek().getTag());
		}
	}

	public int goToParentNode(String id, Context context) {
		// Attempts to traverse to the Parent Node to the current 'Context Node', 
		// by popping elements in the current parentNode stack, until a parent has an ID
		// matching the String 'id'. If no parent can be found the algorithm will traverse back to the 
		// root and return zero 
		
		System.out.println("Testing for a match with: " + id);
		int amount = 0;

		if (id.equals(parentNodes.peek().getID())) {
			System.out.println("The ID equals parentNode.peek.getID()");
			return 0;
		}
		
		int size = parentNodes.size(); 
		for(int i=0 ; i < size; i++){
			System.out.println("Parent Nodes, get(i): " + parentNodes.get(i));
			System.out.println("Parent Nodes, get(i), tag: " + parentNodes.get(i).getTag());
			System.out.println("Parent Nodes, get(i), id: " + parentNodes.get(i).getID());
			if (parentNodes.get(i).getID().equals(id)){	
				System.out.println("Texting for a match with: " + parentNodes.get(i).getID());
				amount = parentNodes.size()-(i+1);
				for (int j=0; j < amount; j++){
					parentNodes.pop(); 
				}
				childNodes = parentNodes.peek().childList();
				return amount;
			}
		}
		return 0;
	}

	public String getParentNodeId(){
		return getContextNode().getID();
	}

	public String getParentNodeDisplay(){
		return parentNodes.peek().getDisplay();
	}

	public void traverseControlToInput(String input) {
		goToNodeItemInList(input);
		for (int i = 0; i < getChildren().size(); i++) {
			if (input.equals(getChildren().get(i).getID())) {
				goToNodeItemInList(getChildren().get(i).getID());
				break;
			}
		}
	}

	public void popToParent() {
		parentNodes.pop();
		childNodes = parentNodes.peek().childList();
	}

	public boolean checkCompleted() {
		set = this.root.checkCompleted(false);
		return set ;
	}

	public void goToNodeItemInList(String id) {
		// Pushes the child with an 'id' matching the String 'id' to the current parent node 
		// (top of the parentNode stack) and sets its childList() to the ArrayList childNodes 
		// If no match can be found the function returns the first child in the list 
		System.out.println("id we are looking for: ->" + id);
		FireNode newParent = getChildByID(new String(id));
		System.out.println("tryed to find new parent");
		if (newParent != null) {

			System.out.println("New parent tag -> " + newParent.getID());
			System.out.println("it does apparently not equal a leaf");
			newParent.setTag(newParent.getID());
			parentNodes.push(newParent);
			childNodes = parentNodes.peek().childList();
			
		}
	}

	public FireNode getChildByID (String id) {
		//If any of the parentNodes children have an 'id' matching String 'id', return them, 
		// if not, return the first item in the child list. 
		FireNode child = null;
		for (int i=0;i < childNodes.size(); i++) {
			if (id.equals(childNodes.get(i).getID().toString())) {
				child = childNodes.get(i);
			}
		}
		if (child != null){
			return child;
		} else {
			return childNodes.get(0);
		}
	}

	public ListItemContent[] getRowContents() {
		// Create an array of 'Row' elements by traversing through the childNodes 
		// and calling the FireNodes 'createRowContent' method. This creates an array of 
		// row content items for each child to be displayed as rows in the ListView. 
		// These row content items define the clickable ('white') elements in the list view
		
		System.out.println("creating list item");
		ListItemContent[] data = new ListItemContent[childNodes.size()];
		System.out.println("list item created");
		for (int i = 0; i < childNodes.size(); i++) {     
			if (childNodes.get(i).completed) {
				System.out.println("1");
				if (childNodes.get(i).getTag().equals("Leaf")){
					System.out.println("2");
					ListItemContent nug = childNodes.get(i).createRowContent();		 
					data[i] = nug; 
				} else {
					System.out.println("3");
					data[i] = childNodes.get(i).createRowContent();
					data[i].setCompleted(true);
				}  	 
			} else {
				System.out.println("4");
				data[i]= childNodes.get(i).createRowContent();
			}
		}
		return data;
	}

	public ArrayList<FireNode> getChildren() {
		// Public getter for the current Context Nodes children
		return childNodes;
	}

	public FireNode getContextNode() {
		// Returns the Node object of the current parent node in the stack. 
		// This defines the current Context node of the applications object traversal 
		return parentNodes.peek();
	}
}