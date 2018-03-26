package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
/**
 * Retirado em 21/07/2017 do endere�o https://gist.github.com/alexandreaquiles/d2f3db0441c61778ee81
 */
public class Validador 
{
	public void valida(File xml, File xsd) throws org.xml.sax.SAXException, IOException
	{
		Source schemaFile = new StreamSource(xsd);
		Source xmlFile = new StreamSource(xml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}
	
	public void valida(InputStream xml, File xsd) throws org.xml.sax.SAXException, IOException
	{
		Source schemaFile = new StreamSource(xsd);
		Source xmlFile = new StreamSource(xml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}
}
