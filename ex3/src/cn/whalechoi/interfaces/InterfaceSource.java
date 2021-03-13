package cn.whalechoi.interfaces;

import java.util.ArrayList;
import cn.whalechoi.bases.Student;

public interface InterfaceSource {
    void setSource();
    boolean openSource();
    ArrayList<Student> getData();
    boolean closeSource();
}
