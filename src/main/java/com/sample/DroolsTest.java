package com.sample;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	
    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            
            // go !                  
            ksession.insert(new StartEvent());
            ksession.setGlobal("userModel", new UserModel());            
            
            ksession.fireAllRules();
            
            Document goalModel = createGoalModel(ksession);
            printGoalModel(goalModel);
            
            Set<Widget> widgets = new CompositionEngine().createWorkspace(goalModel);
            for (Widget widget : widgets) {
				System.out.println(widget.getUri());            	
			}
            
            
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

	private static Document createGoalModel(StatefulKnowledgeSession ksession)
			throws ParserConfigurationException, JAXBException,
			PropertyException, SAXException, IOException,
			TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document xmlDoc = db.newDocument();
		Element root = xmlDoc.createElement("goal");
		xmlDoc.appendChild(root);
		
		InputSource is = new InputSource();
		            
		for(Object obj: ksession.getObjects()) {            	
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    
		    StringWriter writer = new StringWriter(); 
		    m.marshal(new JAXBElement(
		    		  new QName(null,obj.getClass().getSimpleName()), obj.getClass(), obj), writer);
		    
		    is.setCharacterStream(new StringReader(writer.toString()));
		    Document doc = db.parse(is);
		    Node firstDocImportedNode = xmlDoc.importNode(doc.getFirstChild(), true);
		    root.appendChild(firstDocImportedNode );
		}
		
		return xmlDoc;
	}
	
	private static void printGoalModel(Document goalModel) throws Exception {
		DOMSource domSource = new DOMSource(goalModel);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);		
		
		System.out.println(sw.toString());
	}
    

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
        
        //kbuilder.add(ResourceFactory.newClassPathResource("Playlist.dsl"), ResourceType.DSL );
        //kbuilder.add(ResourceFactory.newClassPathResource("Playlist.dslr"), ResourceType.DSLR );
        
        kbuilder.add(ResourceFactory.newClassPathResource("AutomaticComposition.dsl"), ResourceType.DSL );
        //kbuilder.add(ResourceFactory.newClassPathResource("AutomaticComposition.dslr"), ResourceType.DSLR );        
        kbuilder.add(ResourceFactory.newClassPathResource("AutoCompositionEmergencyScenario.dslr"), ResourceType.DSLR );
        
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }
    
    public static class StartEvent {
    	
    }
    
    public static class Communicator {
    	
    	private static Scanner scanIn = new Scanner(System.in);
    	
    	public static Boolean askYesNoQuestion(String message) {
    		System.out.println(message);    		
    		String anwser = scanIn.nextLine();
    		return "y".equals(anwser);
    	}
    	
    	public static String askGeneralQuestion(String message) {
    		System.out.println(message);    		
    		String anwser = scanIn.nextLine();
    		return anwser;
    	}
    	
    	public static List<String> askMultipleChoiceQuestion(String message) {
    		System.out.println(message);    		
    		String anwser = scanIn.nextLine();
    		List<String> list = Arrays.asList(anwser.split(","));
    		return list;
    	}
    }
    
    
}
