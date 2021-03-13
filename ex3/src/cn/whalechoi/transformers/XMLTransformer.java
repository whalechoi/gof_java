package cn.whalechoi.transformers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.whalechoi.bases.Student;
import cn.whalechoi.interfaces.InterfaceTransformer;
import cn.whalechoi.tools.MyInput;

public class XMLTransformer implements InterfaceTransformer {
    private String targetPath = "";
    private File targetFile = null;

    @Override
    public void setTarget() {
        System.out.print("\033[32;1m" + "[Interaction]" + "\033[0m" + " Please input the output file(.xml) path:");
        if (MyInput.scanner.hasNextLine()) {
            targetPath=MyInput.scanner.nextLine();
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Set xml output file path success!");
    }

    @Override
    public boolean createTarget() {
        targetFile = new File(targetPath);
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Create xml output file failed!");
            return false;
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Create xml output file success!");
        return true;
    }

    @Override
    public boolean writeData(ArrayList<Student> students) {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("StudentsInfo");
            doc.appendChild(root);
            for (Student stu : students) {
                Element Student = doc.createElement("Student");
                
                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(stu.getId())));
                Student.appendChild(id);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(String.valueOf(stu.getName())));
                Student.appendChild(name);

                Element sex = doc.createElement("sex");
                sex.appendChild(doc.createTextNode(String.valueOf(stu.getSex())));
                Student.appendChild(sex);

                Element age = doc.createElement("age");
                age.appendChild(doc.createTextNode(String.valueOf(stu.getAge())));
                Student.appendChild(age);
                
                root.appendChild(Student);
            }

            // Save the document to the disk file
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tFactory.newTransformer();

            // Format the XML nicely
            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            try {
                FileWriter fos = new FileWriter(targetFile);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);
                System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Write output xml file success!");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (TransformerException e) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Output xml file failed!");
        } catch (ParserConfigurationException e) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Build xml file failed!");
        }
        return false;
    }

    @Override
    public boolean closeTarget() {
        targetPath = "";
        targetFile = null;
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Close output xml file success!");
        return true;
    }
}
