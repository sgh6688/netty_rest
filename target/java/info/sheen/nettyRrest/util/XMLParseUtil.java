package info.sheen.nettyRrest.util;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLParseUtil {
	private static Logger logger = LoggerFactory.getLogger(XMLParseUtil.class);

	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(String xmlString) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new StringReader(xmlString));
			// Document doc = reader.read(new FileReader(new File(
			// "config/test.xml")));
			Element root = doc.getRootElement();
			logger.info("root.getpath:" + root.getPath() + ", attribute count:"
					+ root.attributeCount() + ", root name:" + root.getName()
					+ ", root.value:" + root.getStringValue());
			// // resultMap.put(root.getName(), root.getStringValue());
			// for (Iterator<Element> i = root.elementIterator(); i.hasNext();)
			// {
			// Element element = (Element) i.next();
			// resultMap.put(element.getName(), element.getText());
			// logger.info("elementName:" + element.getName()
			// + ", elementValue:" + element.getText());
			//
			// }

			parseAllElements(resultMap, root);

			logger.info("xml parse result:" + resultMap);
		} catch (DocumentException e) {
			logger.error("Fail to pase xml text to map.", e);
			// } catch (FileNotFoundException e) {
			// logger.error(
			// "Fail to pase xml text to map because of file not found.",
			// e);
		}

		return resultMap;
	}

	@Test
	public void testXmlToMap() {
		String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		StringBuffer sb = new StringBuffer();
		sb.append(XML_HEADER);
		sb.append("<a>");
		sb.append("<b>");
		sb.append("<c>");
		sb.append("DWMC");
		sb.append("</c>");
		sb.append("<d>");
		sb.append("id=10");
		sb.append("</d>");
		sb.append("</b>");
		sb.append("</a>");
		logger.info(sb.toString());
		logger.info(xmlToMap(sb.toString()).toString());
	}

	@SuppressWarnings("all")
	private static void parseAllElements(Map<String, String> resMap,
			Element baseElement) {
		List<Element> elements = baseElement.elements();
		for (Element ele : elements) {
			logger.info("===current base element name:{}, element val:{}",
					ele.getName(), ele.getTextTrim());
			Iterator<Element> iterator = ele.elementIterator();
			if (iterator.hasNext()) {
				logger.info("elemet {} has {} children,continue this method.",
						ele.getName(), ele.elements().size());
				parseAllElements(resMap, ele);
			} else {
				logger.info("element {} has no child, put to map now.",
						ele.getName());
				resMap.put(ele.getName(), ele.getText().trim());
			}
		}

	}
}
