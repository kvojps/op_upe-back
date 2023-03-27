package com.upe.observatorio.projeto.dominio.envelopes;

import java.util.HashMap;

import lombok.Data;

@Data
public class DashboardVO {
	
	private Integer totalCourses;
	
	private Integer totalCampuses;
	
	private Integer totalProjects;
	
	private Integer totalUsers;
	
	private HashMap<String, Integer> projectsPerCourses;
	
	private HashMap<String, Integer> projectsPerCampuses;
	
	private HashMap<String, Integer> projectsPerModalities;
	
	private HashMap<String, Integer> projectsPerThematicArea;
}
