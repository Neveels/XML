import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SAXParser {
    public static void main(String[] args) throws XMLStreamException {
        List<Student> stList = null;
        Student currSt = null;
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader =
                factory.createXMLStreamReader(
                        ClassLoader.getSystemResourceAsStream("Student.xml"));

        while(reader.hasNext()){
            int event = reader.next();

            switch(event){
                case XMLStreamConstants.START_ELEMENT:
                    if ("student".equals(reader.getLocalName())){
                        currSt = new Student();
                        currSt.setId(reader.getAttributeValue(0));
                    }
                    if("students".equals(reader.getLocalName())){
                        stList = new ArrayList<>();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch(reader.getLocalName()){
                        case "student" -> stList.add(currSt);
                        case "firstName" -> currSt.setFirstName(tagContent);
                        case "secondName"-> currSt.setSecondName(tagContent);
                        case "country" -> currSt.setCountry(tagContent);
                        case "age" -> currSt.setAge(Integer.valueOf(tagContent));
                    }
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    stList = new ArrayList<>();
                    break;
            }

        }
        stList.forEach(System.out::println);
    }
}
