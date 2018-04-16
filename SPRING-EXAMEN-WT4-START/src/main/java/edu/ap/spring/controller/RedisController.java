package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;

@Controller
public class RedisController {
	private RedisService service;
	private List<InhaalExamen> studentrequests = new ArrayList<InhaalExamen>();
	private String KEY = "student";
	private int count = 0;
	
	   @Autowired
		public void setRedisService(RedisService service) {
			this.service = service;
	   }
	   
	   @RequestMapping("/list?studentname={studentname}")
	   @ResponseBody
	   public String listStudentrequest(@PathVariable("studentname") String name) {
		   String html = "<HTML>";
		   html += "<BODY><h1> Examens</h1><br/><br/><ul>";
		   Set<String> student = service.keys("student:" + name + ":*");
		   for(String s : student) {
			   // make a key from the byte array
			   String key = new String(s);
			   // get all parts of the key, eg : ["movies", "1998", "The Big Lebowski"]
			   String[] parts = key.split(":");
			   
			   html += "<li>" + parts[3] + "," + parts[2] + "," + parts[1] + "<br/>";
			  
			   
			   html = html.substring(0, html.length() - 2);
			   html += "</BODY></HTML>";
		   		   
		   }
		   listrequests(studentrequests);
		   
		   return html;
	   }
	   
	private void listrequests(List<InhaalExamen> studentrequests2) {
		Collections.sort(studentrequests2, new Comparator<InhaalExamen>(){

			  public int compare(InhaalExamen o1, InhaalExamen o2)
			  {
			     return o1.getReason().compareTo(o2.getReason());
			  }
			});
	}

	@RequestMapping("/new")
	   @ResponseBody
	   public void add(String student, String reason, String examen) {
		   InhaalExamen i = new InhaalExamen(student, examen, reason);
		   byte[] b = i.getStudent().getBytes();
		   String str = i.getReason() + ":" + i.getExam() + ":" + i.getDate();
		   byte[] bo = str.toString().getBytes();
		   Boolean bool = service.setNX(b, bo);
		   service.setBit(student, count, true);  
		   count++;   
	   }
}
