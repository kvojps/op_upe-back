package com.upe.observatorio.projeto.dominio.envelopes;

import lombok.Data;

import java.util.HashMap;

@Data
public class DashboardVO {
	
	private Long totalCourses;
	
	private Long totalCampuses;
	
	private Long totalProjects;
	
	private Long totalUsers;
	
	private HashMap<String, Integer> projectsPerCourses;
	
	private HashMap<String, Integer> projectsPerCampuses;
	
	private HashMap<String, Long> projectsPerModalities;
	
	private HashMap<String, Long> projectsPerThematicArea;
}
