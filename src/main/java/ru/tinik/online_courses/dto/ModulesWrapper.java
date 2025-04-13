package ru.tinik.online_courses.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.tinik.online_courses.models.CourseModule;

@Getter
@Setter
public class ModulesWrapper {

	private List<CourseModule> modules;
	
	public void addModule(CourseModule module) {
        this.modules.add(module);
    }
}
