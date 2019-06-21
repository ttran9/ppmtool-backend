package tran.example.ppmtool.services.projecttasks;

import tran.example.ppmtool.domain.project.ProjectTask;

import java.security.Principal;

public interface ProjectTaskService {

    /**
     * Add a project task to an existing Project and Backlog.
     * @param projectIdentifier The projectIdentifier to grab the associated Backlog.
     * @param projectTask The projectTask to be added to a backlog identified by the projectIdentifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Return a new Project Task.
     */
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, Principal principal);

    /**
     * Gets a list of Project Tasks associated with a specific Backlog.
     * @param backlogId The backlog's projectIdentifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Gets a list of Project Tasks ordered by priority.
     */
    Iterable<ProjectTask> findBacklogById(String backlogId, Principal principal);

    /**
     * Gets the project task with the specified project sequence and is a part of the proper backlog.
     * @param backlogId The backlog identifier that the project task is a part of.
     * @param projectSequence The project sequence identifying a project task.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns a project task.
     */
    ProjectTask findProjectTaskByBackLogIdAndProjectSequence(String backlogId, String projectSequence, Principal principal);

    /**
     * Updates the project task with the specified project sequence and backlogId.
     * @param updatedProjectTask The project task with the newly updated content(s).
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be updated.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns the updated project task.
     */
    ProjectTask updateProjectTaskByProjectSequenceAndBacklogId(ProjectTask updatedProjectTask, String backlogId,
                                                               String projectSequence, Principal principal);

    /**
     * Deletes a project task with the specified project sequence and backlogId.
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be removed.
     * @param principal The object expected to hold the logged in user's information (such as username).
     */
    void deleteProjectTaskByProjectSequenceAndBacklogId(String backlogId, String projectSequence, Principal principal);
}
