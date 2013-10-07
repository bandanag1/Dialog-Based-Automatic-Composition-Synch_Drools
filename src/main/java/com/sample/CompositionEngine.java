package com.sample;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CompositionEngine {
	
	private WidgetRegistry widgetRegistry = new WidgetRegistry();
	
	protected WidgetRegistry getWidgetRegistry() {
		return widgetRegistry;
	}
	
	public Set<Widget> createWorkspace(Document goalModel) {
		HashSet<Widget> allWidgets = new HashSet<Widget>();
		
		NodeList facts = goalModel.getFirstChild().getChildNodes();		
		Node locationFact = findLocationInNodes(facts);
		
    	for(int i=0; i < facts.getLength(); i++) {
    		Node fact = facts.item(i);
    		if(!(fact instanceof Element))
    			continue;
    		
    		Set<Widget> widgetsForFact = findWidgetsForFact(fact, locationFact);
    		allWidgets.addAll(widgetsForFact);
    	}
    	
        return allWidgets;
	}
	
	
	

    private Node findLocationInNodes(NodeList facts) {
    	for(int i=0; i < facts.getLength(); i++) {
    		Node fact = facts.item(i);
    		if(fact.getNodeName().equals("Location"))
    			return fact;
    	}
    	return null;
	}

	private Set<Widget> findWidgetsForFact(Node fact, Node location) {
        String sparql = "PREFIX om: <http://www.ict-omelette.eu/schema.rdf#> " +
        		"PREFIX ctag: <http://commontag.org/ns#> " +
        		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
        		"PREFIX dc: <http://purl.org/dc/elements/1.1/> " + 
        		"SELECT ?source ?title ?description WHERE {" +
    			"?widget rdf:type om:Widget; " +
    			" 	     dc:source ?source; " + 
    			" 	     dc:description ?description; " + 
    			" 	     rdfs:label ?title; " +
    			" 		 ctag:tagged ?tagNode; ";
        
        if(location != null)
        	sparql += " rdf:lang \"" + getLanguageCode(location) + "\"; ";
        
    	sparql += "?tagNode rdfs:label ?tag; }";        
        
        NodeList keywordNodes = ((Element)fact).getElementsByTagName("keywords");
        
		if(keywordNodes.getLength()>0) {
			sparql += " FILTER { ";
			Node keyword = keywordNodes.item(0);
			
			String freetext = "";
			String[] keywords = keyword.getTextContent().split(",");
			for(String k: keywords) {
				freetext +=  "|| regex(?description, \"" + k + "\") ";
			}			
			sparql += freetext.substring(3) + "}";
		}	
		else {
			System.out.println("- Skipping " + fact.getNodeName() +  " - no keywords");
		}
        System.out.println(sparql); 
        
		return getWidgetRegistry().query(sparql);
	}

	private String getLanguageCode(Node location) {
		Element l = (Element)location;
		String land = l.getElementsByTagName("land").item(0).getTextContent();
		return land;
	}
}
