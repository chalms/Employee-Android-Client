package models.nodes;

import java.util.ArrayList;
import java.util.Stack;

import org.json.JSONException;
import org.json.JSONObject;

import views.ListItemContent;
import web.Router;
//import java.util.Map;
import web.WebClient;

public class FireNode{

	protected String name; //the name of the element, eg. 'North Bay Inc'
	protected String id; //created ID to define a unique node in the app
	protected String nodeID; //the nodes product ID defined by Fire Alert
	protected String tag; //The type of element, eg. 'Client'
	public boolean completed; //set to true if all children nodes are completed
	protected ArrayList <FireNode> childList;  //an array list of the elements children nodes 
	public int checked = 0; 
	public boolean changed; 
	protected Router router; 

	public FireNode () { 
		this.name = null;
		this.id = null;
		this.nodeID = null;
		this.tag = null;
		this.childList = null;
		this.completed = false; 
		router = WebClient.getMain().getRouter();
	}

	protected FireNode(String name, String id, String nodeID, String tag) {
		this.name = name;
		this.id = id;
		this.nodeID = nodeID;
		this.tag = tag;
		this.childList = null;
		this.completed = false; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return this.id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getNodeID() {
		return this.nodeID;
	}

	public void setNodeID(String id) {
		this.nodeID = id;
	}

	public String getTag() {
		return tag;
	}

	public void setChecked(int check) {
		this.checked = check;
	}
	
	public void setChanged() {
		this.changed = true; 
	}
	
	public boolean getChanged() {
		return this.changed; 
	}

	public int getChecked() {
		return this.checked;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getDisplay(){
		return name;
	}

	public ArrayList <FireNode> childList() {
		return childList;
	}

	public void add(FireNode newNode) {
		childList.add(newNode);
	}

	//RECURSIVE ALGORITHMS -----> 

	public FireNode getChildByID (String id) {
		//Algorithm that finds a child node from the elements childList, by matching the 'id'attribute
		//If it cannot find a matching node it returns the first child in the list 
		
		for (int i=0;i < childList.size(); i++) {
			if (id.equals(childList.get(i).getID())) {
				return childList.get(i);
			}
		}
		return null;
	}

	public ListItemContent createRowContent () {
		//passes attribute information into 'Row Content Item', a class that holds the necessary data to display 
		//each element in a 'Row' the in the list. The 'Row' class attaches this information to the lists TextViews
		
		ListItemContent item = new ListItemContent (this);
		return item;
	}

	public boolean checkCompleted(boolean t) {
		//Checks to see if this nodes children are completed. If all its children are completed the node sets its
		//completed attribute to 'true'
		
		int size = this.childList().size();
		this.completed = true; 
		for (int i=0; i < size; i++) {
			if (!this.childList().get(i).checkCompleted(t)) this.completed = false; 
		}
		return this.completed;
	}

	public Stack <FireNode> checkForId(String id) {
		//Algorithm that checks if this elements nodeID attribute is equal to the String 'id' variable
		//If it is equal, this element creates a stack and pushes itself into the stack
		//If it is not equal, it recursively calls this function on each of its children 
		//If any of its children return a non-empty stack, this function pushes itself to the top of the stack and 
		//returns the stack. If all of its children return an empty stack, the element returns an empty stack 
		//If this element is a leaf node, EG. 'testResult' it returns an empty stack. 
		//The result will be a stack of all childNodes from this node to the node that has the matching product ID

		if(this.getNodeID().equals(id)){
			Stack <FireNode> nodeList = new Stack <FireNode> ();
			nodeList.push(this);
			return nodeList;
		} 
		if (!this.getTag().equals("Leaf")) {
			int size = this.childList().size();
			for (int i=0; i < size; i++) {
				Stack <FireNode> theReturnStack = this.childList().get(i).checkForId(id);
				if (!theReturnStack.empty()) {
					theReturnStack.push(this);
					return theReturnStack;
				}
			}
		} 
		return (new Stack <FireNode> ());
	}

	public void clear(){
		if(this.childList().isEmpty()){
			this.checked = 0; 
			this.completed = false; 
			return ; 
		} else {
			for(int i = 0; i < this.childList().size(); i++) {
				this.childList().get(i).clear();
			}
			return; 
		}
	}

	public JSONObject upload() throws JSONException {
		return null; }
}