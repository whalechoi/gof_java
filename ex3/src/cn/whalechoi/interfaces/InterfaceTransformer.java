package cn.whalechoi.interfaces;

import java.util.ArrayList;
import cn.whalechoi.bases.Student;

public interface InterfaceTransformer {
    void setTarget();
    boolean createTarget();
    boolean writeData(ArrayList<Student> students);
    boolean closeTarget();
}
