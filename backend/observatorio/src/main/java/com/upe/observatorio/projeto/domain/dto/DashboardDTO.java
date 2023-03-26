package com.upe.observatorio.projeto.domain.dto;

import java.util.HashMap;

import lombok.Data;

@Data
public class DashboardDTO {
	
	private Integer totalCourses;
	
	private Integer totalCampuses;
	
	private Integer totalProjects;
	
	private Integer totalUsers;
	
	private HashMap<String, Integer> courses;
	
	private HashMap<String, Integer> campuses;
	
	private HashMap<String, Integer> modalities;
	
	private HashMap<String, Integer> thematicArea;
}
