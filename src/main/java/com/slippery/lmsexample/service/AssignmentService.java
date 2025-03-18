package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.AssignmentDto;
import com.slippery.lmsexample.models.Assignment;

public interface AssignmentService {
    AssignmentDto createNewAssignment(Long userId,Long lessonId, Assignment assignmentDetails);
    AssignmentDto updateAssignment(Long userId,Long lessonId,Long assignmentId, Assignment assignmentDetails);
    AssignmentDto deleteAssignmentById(Long userId,Long lessonId,Long assignmentId);
    AssignmentDto findAssignmentById(Long assignmentId);
    AssignmentDto findAllAssignments(Long assignmentId);
}
