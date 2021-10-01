import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;


public class Main {
	public static void readdata (Connection con) throws Exception{
		System.out.println("\n*** ������ ��ȸ ***\n");
		Statement stat = con.createStatement();
		String sql = "select * from g_artists";
		ResultSet rs = stat.executeQuery(sql);
		while(rs.next()) {
			String id = rs.getString("id");
			String a_type = rs.getString("a_type");
			String a_year = rs.getString("a_year");
			String name = rs.getString("name");
			String debut = rs.getString("debut");
			String regdate = rs.getString("regdate");
			
			System.out.println("["+id+"] "+name+" - "+a_type+" - "+a_year+" - "+ debut+ " - " + regdate);
		}
		stat.close();
	}
	
	public static void adddata(Connection con, String name, String a_type, String a_year, String debut)  throws Exception {
		System.out.println("\n*** �� ������ �߰� ***");
		PreparedStatement pstmt = con.prepareStatement("insert into g_artists (name,a_type,a_year,debut,regdate) values (?,?,?,?,datetime('now','localtime'))");
		pstmt.setString(1,name);
		pstmt.setString(2,a_type);
		pstmt.setString(3, a_year);
		pstmt.setString(4,debut);
		int cnt = pstmt.executeUpdate();
		if(cnt>0)
			System.out.println(name+" �����Ͱ� �߰��Ǿ����ϴ�!");
		else
			System.out.println("[Error] ������ �߰� ���� �߻�");
		pstmt.close();
	}
	public static void updateayear(Connection con, String data, int id)  throws Exception {
		System.out.println("\n*** ������ ���� ***");
		PreparedStatement pstmt = con.prepareStatement("update g_artists set a_year =  ? where id=?");
		pstmt.setString(1,data);
		pstmt.setInt(2,id);
		int cnt = pstmt.executeUpdate();
		if(cnt>0)
			System.out.println("�����Ͱ� �����Ǿ����ϴ�!");
		else
			System.out.println("[Error] ������ ���� ���� �߻�");
		pstmt.close();
	}
	public static void deletedata(Connection con, int id)  throws Exception {
		System.out.println("\n*** ������ ���� ***");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select name,a_type,a_year,debut,regdate from g_artists where id='" +  id + "'");
		while(rs.next()) {
			String a_type = rs.getString("a_type");
			String a_year = rs.getString("a_year");
			String name = rs.getString("name");
			String debut = rs.getString("debut");
			String regdate = rs.getString("regdate");
			System.out.println("["+id+"] "+name+" - "+a_type+" - "+a_year+" - "+ debut+ " - " + regdate);
		}
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) : ");
		Scanner keyboard = new Scanner(System.in);
		String sentinel = keyboard.next().trim();
		stmt.close();
		keyboard.close();
		if(sentinel.equals("y")){
			Statement stat = con.createStatement(); 
			int cnt = stat.executeUpdate("delete from g_artists where id='" +  id + "'");
			
			if(cnt>0)
				System.out.println("�����Ͱ� �����Ǿ����ϴ�!");
			else
				System.out.println("[Error] ������ ���� ���� �߻�");
			stat.close();
		}
		
	}

	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			
			String dbFile = "myfirst.db";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			readdata(con);
			//adddata(con,"��ź�ҳ��","�����׷�","2010���, 2020���", "2013�� / No More Dream");
			//adddata(con,"Ariana Grande","����","2010���, 2020���", "2010�� / Put Your Hearts Up");
			//updateayear(con, "2000���, 2010���, 2020���",1);
			//updateayear(con, "3000���, 3010���, 3020���",9);
			//deletedata(con,9);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(con!=null)
				try {
					con.close();
				} catch(Exception e) {}
		}

	}
}
