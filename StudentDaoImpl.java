package example;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class StudentDaoImpl implements StudentDao {
	//list is working as a database
	//   List<Student> students;
	private String url ="jdbc:mariadb://localhost/student?user=root";
	private Connection conn;

	
	public StudentDaoImpl(){
	     try {
	    	 conn= DriverManager.getConnection(url);
	      	 String sql ="INSERT INTO studenti(nome,matricola) VALUES (?,?)";
	    	 PreparedStatement insertState = conn.prepareStatement(sql);
	    	 insertState.setString(1, "Robert");
	    	 insertState.setInt(2, 0);
	    	 int rowInsert = insertState.executeUpdate();
	    	 System.out.println("row affected"+ rowInsert);
	    	 
	    	 insertState.setString(1, "john");
	    	 insertState.setInt(2, 1);
	    	 rowInsert = insertState.executeUpdate();
	    	 System.out.println("row affected"+ rowInsert);
	    	 
	    	 insertState.close();
		     
	     }catch(SQLException ex) {
	    	System.out.println("SqlException "+ ex.getMessage()); 
	    	System.out.println("Sql State "+ ex.getSQLState()); 
	    	System.out.println("VendorError"+ ex.getErrorCode()); 
	    	
	     }	
	   }
	
	
	   @Override
	   public void deleteStudent(Student student) {
		 try {
			 String sql = "DELETE FROM studenti where studenti.matricola = ?";
			 PreparedStatement del = conn.prepareStatement(sql);
			 del.setInt(1, student.getRollNo());
			 int rows = del.executeUpdate();
		     System.out.println("Deleted rows: " + rows);
			 
			 
		 }catch(SQLException ex) {
		    System.out.println("SqlException "+ ex.getMessage()); 
		    System.out.println("Sql State "+ ex.getSQLState()); 
		    System.out.println("VendorError"+ ex.getErrorCode()); 
		   } 
	   }

	   //retrive list of students from the databas
	   @Override
	   public List<Student> getAllStudents() {
		   try {
			   List <Student> list = new ArrayList<>();
			   String sql = "SELECT * FROM studenti";
			   Statement st = conn.createStatement();
			   ResultSet rs = st.executeQuery(sql);
			   while(rs.next()) {
				   Student s = new Student(rs.getString("nome"), rs.getInt("matricola"));
				   list.add(s);			   
			   }
			   return list;
			      
		   }catch(SQLException ex) {
			    System.out.println("SqlException "+ ex.getMessage()); 
			    System.out.println("Sql State "+ ex.getSQLState()); 
			    System.out.println("VendorError"+ ex.getErrorCode()); 
			   } 
		   return null;	   
	   }

	   @Override
	   public Student getStudent(int rollNo) {
		   try {
			   String sql = "SELECT * FROM studenti where matricola=?";
			   PreparedStatement st= conn.prepareStatement(sql);
			   st.setInt(1, rollNo);
			   ResultSet rs = st.executeQuery();
			   if(rs.next()) {
				   Student stud = new Student(rs.getString("nome"), rs.getInt("matricola"));
				   return stud;
			   }
		   }catch(SQLException ex) {
			    System.out.println("SqlException "+ ex.getMessage()); 
			    System.out.println("Sql State "+ ex.getSQLState()); 
			    System.out.println("VendorError"+ ex.getErrorCode()); 
			} 
		   return null;
	   }

	   @Override
	   public void updateStudent(Student student) {
		   try {
			   String sql= "UPDATE studenti Set nome=? where matricola = ?";
			   PreparedStatement st= conn.prepareStatement(sql);
			   st.setInt(2,student.getRollNo());
			   st.setString(1, student.getName());
			   st.execute();
			   
			   
		   }catch(SQLException ex) {
			    System.out.println("SqlException "+ ex.getMessage()); 
			    System.out.println("Sql State "+ ex.getSQLState()); 
			    System.out.println("VendorError"+ ex.getErrorCode()); 
			} 
	      System.out.println("Student: Roll No " + student.getRollNo() + ", updated in the database");
	   }
	}