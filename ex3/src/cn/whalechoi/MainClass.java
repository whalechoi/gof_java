package cn.whalechoi;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import cn.whalechoi.tools.MyInput;

public class MainClass {
    private static String sourceName = "cn.whalechoi.sources.";
    private static String transformerName = "cn.whalechoi.transformers.";

    public static void main(String[] args) throws Exception {
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Welcome to GoF(JAVA version) ex3!");
        if (!loadConfig()) {
            System.exit(-1);
        }

        // TextSource TestSource = new TestSource();
        // testSource.setSource();
        // testSource.openSource();
        //
        // XLSTransformer testTransformer = new XLSTransformer();
        // testTransformer.setTarget();
        // testTransformer.createTarget();
        //
        // testTransformer.writeData(testSource.getData());
        //
        // testSource.closeSource();
        // testTransformer.closeTarget();

        Class source = Class.forName(sourceName);
        Constructor sourceConstructor = source.getConstructor();
        Method sourceMethod = source.getMethod("setSource");
        Object sourceObject = sourceConstructor.newInstance();
        sourceMethod.invoke(sourceObject);
        sourceMethod = source.getMethod("openSource");
        sourceMethod.invoke(sourceObject);

        Class target = Class.forName(transformerName);
        Constructor targetConstructor = target.getConstructor();
        Method targetMethod = target.getMethod("setTarget");
        Object targetObject = targetConstructor.newInstance();
        targetMethod.invoke(targetObject);
        targetMethod = target.getMethod("createTarget");
        targetMethod.invoke(targetObject);

        sourceMethod = source.getMethod("getData");
        targetMethod = target.getMethod("writeData", ArrayList.class);
        targetMethod.invoke(targetObject, sourceMethod.invoke(sourceObject));

        sourceMethod = source.getMethod("closeSource");
        sourceMethod.invoke(sourceObject);
        targetMethod = target.getMethod("closeTarget");
        targetMethod.invoke(targetObject);

        MyInput.scanner.close();
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Bye!");
        return;
    }

    private static boolean loadConfig() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File("gof_java/ex3/resource/config.xml");
            Document document = db.parse(file);
            sourceName += document.getElementsByTagName("sourceName").item(0).getFirstChild().getNodeValue();
            transformerName += document.getElementsByTagName("transformerName").item(0).getFirstChild().getNodeValue();
            System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Load config success!");
            return true;
        } catch (Exception e) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Load config failed!");
            e.printStackTrace();
        }
        return false;
    }
}
