package ru.tinik.online_courses.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.models.CourseModule;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final CourseModuleService courseModuleService;

    public boolean isModuleOwner(Long moduleId, String username) {
        CourseModule module = courseModuleService.findById(moduleId);
        return module.getCreatedBy().getUsername().equals(username);
    }
}
