package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.Database;
import model.Student;

@WebServlet("/student")
public class StudentServlet extends HttpServlet{
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Recuperar todos os estudantes cadastrados no BD
		List<Student> students = Database.getStudents();
		
		//Instanciar um objeto para serializar e desserializar os objetos
		Gson gson = new Gson(); 
		
		//Processo de Serialização, convertendo um objeto Java em Json
		String studentAsJson = gson.toJson(students);
		
		response.setContentType("application/json");
		
		//Recuperar o objeto de escrita dentro do response
		PrintWriter writer = response.getWriter();
		
		//Checar a listagem e caso esteja vazia, retornaremos o status 204.
		if(students.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			writer.print(studentAsJson);
			return;
		}
		
		//Escrever na resposta o meu conteúdo JSON
		writer.print(studentAsJson);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Transformado o objeto Json em um objeto Java
		Student student = this.convertJsonToClass(request);
		
		//Adiconar o student no BD e retornar o status 201
		Database.add(student);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// http://localhost:8080/crud-servlets-api/student?registration=201
		//Capturar o registration passado por parâmetro da URL
		Integer registration = Integer.valueOf(request.getParameter("registration"));
		Student studentUpdated = this.convertJsonToClass(request);
		
		//Recuperar o objeto para atualizá-lo
		Student currentStudent = Database.getStudent(registration);
		currentStudent.setName(studentUpdated.getName());
		currentStudent.setEmail(studentUpdated.getEmail());
		
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Capturar o registration passado por parâmetro da URL
		Integer registration = Integer.valueOf(request.getParameter("registration"));
		Student student = Database.getStudent(registration);
		
		//Verificar se o registration corresponde a um estudante
		if(student == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write(String.format("O registro [%s] não foi encontrado!", registration));
			return;
		}
		
			Database.remove(registration);
			response.setStatus(HttpServletResponse.SC_OK);
	}
	
	//Capturar o estudante enviado na requisição e convertê-lo em um objeto Java.
	private Student convertJsonToClass(HttpServletRequest request) throws IOException {
		Gson gson = new Gson();
		BufferedReader reader = request.getReader();
		String studentAsJson = reader.lines().collect(Collectors.joining());
		return gson.fromJson(studentAsJson, Student.class);
	}

}
