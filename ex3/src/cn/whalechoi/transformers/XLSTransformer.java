package cn.whalechoi.transformers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;

import cn.whalechoi.bases.Student;
import cn.whalechoi.interfaces.InterfaceTransformer;
import cn.whalechoi.tools.MyInput;

public class XLSTransformer implements InterfaceTransformer {
    private String targetPath = "";
    private File targetFile = null;

    @Override
    public void setTarget() {
        System.out.print("\033[32;1m" + "[Interaction]" + "\033[0m" + " Please input the output file(.xlsx) path:");
        if (MyInput.scanner.hasNextLine()) {
            targetPath = MyInput.scanner.nextLine();
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Set xlsx output file path success!");
    }

    @Override
    public boolean createTarget() {
        targetFile = new File(targetPath);
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Create xlsx output file failed!");
            return false;
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Create xlsx output file success!");
        return true;
    }

    @Override
    public boolean writeData(ArrayList<Student> students) {
        EasyExcel.write(targetFile).head(generateHead()).sheet("StudentInfo").doWrite(generateData(students));
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Write output xlsx file success!");
        return true;
    }

    @Override
    public boolean closeTarget() {
        targetPath = "";
        targetFile = null;
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Close output xlsx file success!");
        return true;
    }

    private List<List<String>> generateHead() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("id");
        List<String> head1 = new ArrayList<String>();
        head1.add("name");
        List<String> head2 = new ArrayList<String>();
        head2.add("sex");
        List<String> head3 = new ArrayList<String>();
        head3.add("age");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        return list;
    }

    private List<List<Object>> generateData(ArrayList<Student> students) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (Student x:students) {
            List<Object> data = new ArrayList<Object>();
            data.add(x.getId());
            data.add(x.getName());
            data.add(x.getSex());
            data.add(x.getAge());
            list.add(data);
        }
        return list;
    }
}
