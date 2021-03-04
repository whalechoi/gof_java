import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class UserDAO {
    private DBUtil db=new DBUtil();
    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                                + "(\\b(select|update|union|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    private boolean isValid(String str)
    {
        if (sqlPattern.matcher(str).find())
        {
            System.out.println("err：str=" + str);
            return false;
        }
        return true;
    }

    public boolean findUser(String userName, String userPassword) throws Exception {
        if(!isValid(userName)){
            return false;
        }
        if(!isValid(userPassword)){
            return false;
        }
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        try {
            conn = db.getConnection();
            String sql = "select * from tb_reader where rdID = '" + userName + "' and rdPwd = '"+userPassword+"'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            rs.close();
            st.close();
            conn.close();
        }
        return false;
    }
}
