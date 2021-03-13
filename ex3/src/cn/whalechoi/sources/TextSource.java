package cn.whalechoi.sources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import cn.whalechoi.bases.Student;
import cn.whalechoi.interfaces.InterfaceSource;
import cn.whalechoi.tools.MyInput;

public class TextSource implements InterfaceSource {
    private String txtPath = "";
    private File txtFile = null;

    @Override
    public void setSource() {
        System.out.print("\033[32;1m" + "[Interaction]" + "\033[0m" + " Please input the file(.txt) path:");
        if (MyInput.scanner.hasNextLine()) {
            txtPath = MyInput.scanner.nextLine();
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Set txt source file path success!");
    }

    @Override
    public boolean openSource() {
        txtFile = new File(txtPath);
        if (txtFile.exists() && txtFile.canRead()) {
            System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Open txt source file success!");
            return true;
        }
        System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Open txt source file failed!");
        return false;
    }

    @Override
    public ArrayList<Student> getData() {
        if (txtPath == "") {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Please set file path first!");
            return null;
        }
        if (txtFile == null) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Please make sure open source!");
            return null;
        }
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            FileReader fileReader = new FileReader(txtFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineContent = null;
            while ((lineContent = bufferedReader.readLine()) != null) {
                if (lineContent.length() == 0) {
                    continue;
                }
                String[] temp = lineContent.split("\\s+");
                if (temp.length < 4) {
                    bufferedReader.close();
                    fileReader.close();
                    System.out.println(
                            "\033[31;1m" + "[Error]" + "\033[0m" + " Please make sure the source format is correct!");
                    return null;
                }
                Student student = new Student();
                student.setId(Integer.parseInt(temp[0]));
                student.setName(temp[1]);
                student.setSex(temp[2]);
                student.setAge(Integer.parseInt(temp[3]));
                students.add(student);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Get data from txt source file success!");
        return students;
    }

    @Override
    public boolean closeSource() {
        txtPath = "";
        txtFile = null;
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Close txt source file success!");
        return true;
    }
}
