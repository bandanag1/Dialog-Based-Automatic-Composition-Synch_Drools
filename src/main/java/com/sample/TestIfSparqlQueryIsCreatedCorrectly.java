package com.sample;

import java.io.StringReader;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TestIfSparqlQueryIsCreatedCorrectly {

	private Document testGoalModel;
	
	@Before
	public void setUp() throws Exception {
		String xml = "<goal>" + 
				"    <Tourist/>" + 
				"    <LanguageAssistance>" + 
				"    <keywords>translation,languages</keywords>" + 
				"</LanguageAssistance>" + 
				"    <InterestHistoricalPlaces>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">asd	qw	</content>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">asdcxy</content>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">cxv</content>" + 
				"    <keywords>sightseeing,travel,history</keywords>" + 
				"</InterestHistoricalPlaces>" + 
				"    <Location>" + 
				"    <city>Berlin</city>" + 
				"    <land>Germany</land>" + 
				"</Location>" + 
				"    <InterestFoodAndRestaurants>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">asdqwe</content>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">yxcxycsad</content>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">wqewqe</content>" + 
				"    <content xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">asd</content>" + 
				"    <keywords>food,restaurants</keywords>" + 
				"</InterestFoodAndRestaurants>" + 
				"    <HotelAssistance>" + 
				"    <keywords>hotel search,hotel booking</keywords>" + 
				"</HotelAssistance>" + 
				"</goal>";
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
		testGoalModel = db.parse(is);
	}

	@Test
	public void test() {
		Set<Widget> widgets = new CompositionEngine().createWorkspace(testGoalModel);
	}

}
