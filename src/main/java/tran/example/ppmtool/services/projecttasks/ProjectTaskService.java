package tran.example.ppmtool.services.projecttasks;

import tran.example.ppmtool.domain.project.ProjectTask;

import java.security.Principal;

public interface ProjectTaskService {

    /**
     * Add a project task to an existing Project and Backlog.
     * @param projectIdentifier The projectIdentifier to grab the associated Backlog.
     * @param projectTask The projectTask to be added to a backlog identified by the projectIdentifier.
     * @return Return a new Project Task.
     */
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);

    /**
     * Gets a list of Project Tasks associated with a specific Backlog.
     * @param backlogId The backlog's projectIdentifier.
     * @return Gets a list of Project Tasks ordered by priority.
     */
    Iterable<ProjectTask> findBacklogById(String backlogId);

    /**
     * Gets the project task with the specified project sequence and is a part of the proper backlog.
     * @param backlogId The backlog identifier that the project task is a part of.
     * @param projectSequence The project sequence identifying a project task.
     * @return Returns a project task.
     */
    ProjectTask findProjectTaskByBackLogIdAndProjectSequence(String backlogId, String projectSequence);

    /**
     * Updates the project task with the specified project sequence and backlogId.
     * @param updatedProjectTask The project task with the newly updated content(s).
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be updated.
     * @return Returns the updated project task.
     */
    ProjectTask updateProjectTaskByProjectSequenceAndBacklogId(ProjectTask updatedProjectTask, String backlogId,
                                                               String projectSequence);

    /**
     * Deletes a project task with the specified project sequence and backlogId.
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be removed.
     */
    void deleteProjectTaskByProjectSequenceAndBacklogId(String backlogId, String projectSequence);
}
