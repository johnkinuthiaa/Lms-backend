package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.AssignmentDto;
import com.slippery.lmsexample.models.Assignment;
import com.slippery.lmsexample.repository.AssignmentRepository;
import com.slippery.lmsexample.service.AssignmentService;

public class AssignmentServiceImplementation implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImplementation(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public AssignmentDto createNewAssignment(Long userId, Long lessonId, Assignment assignmentDetails) {
        return null;
    }

    @Override
    public AssignmentDto updateAssignment(Long userId, Long lessonId, Long assignmentId, Assignment assignmentDetails) {
        return null;
    }

    @Override
    public AssignmentDto deleteAssignmentById(Long userId, Long lessonId, Long assignmentId) {
        return null;
    }

    @Override
    public AssignmentDto findAssignmentById(Long assignmentId) {
        return null;
    }

    @Override
    public AssignmentDto findAllAssignments(Long assignmentId) {
        return null;
    }
}
