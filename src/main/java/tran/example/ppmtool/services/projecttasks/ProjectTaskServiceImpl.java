package tran.example.ppmtool.services.projecttasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tran.example.ppmtool.constants.projecttask.ProjectTaskPriority;
import tran.example.ppmtool.constants.projecttask.ProjectTaskStatus;
import tran.example.ppmtool.domain.project.Backlog;
import tran.example.ppmtool.domain.project.ProjectTask;
import tran.example.ppmtool.exceptions.projects.ProjectNotFoundException;
import tran.example.ppmtool.repositories.project.ProjectTaskRepository;
import tran.example.ppmtool.services.projects.ProjectService;

import java.security.Principal;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;

    private ProjectService projectService;

    @Autowired
    public ProjectTaskServiceImpl(ProjectTaskRepository projectTaskRepository, ProjectService projectService) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectService = projectService;
    }

    /**
     * Add a project task to an existing Project and Backlog.
     * @param projectIdentifier The projectIdentifier to grab the associated Backlog.
     * @param projectTask The projectTask to be added to a backlog identified by the projectIdentifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Return a new Project Task.
     */
    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, Principal principal) {

        // Project Tasks to be added to a specific project, project != null, and backlog must exist.
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, principal).getBacklog();

        // set the backLog to projectTask.
        projectTask.setBacklog(backlog);

        // we want our project sequence to be like this: IDP1, IDP2, ..., IDP100.
        Integer backlogSequence = backlog.getPtSequence();
        // update the backlog sequence (before we set the projectTask project sequence b/c we start at 0 in the Backlog object).
        backlogSequence++;

        backlog.setPtSequence(backlogSequence);

        // Add Sequence to Project Task.
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // INITIAL priority when priority null
        if(projectTask.getPriority() == null || projectTask.getPriority() == 0 ) {
            projectTask.setPriority(ProjectTaskPriority.LOW.getValue());
        }

        // INITIAL status when status is null
        if(projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
            projectTask.setStatus(ProjectTaskStatus.TO_DO.getStatus());
        }
        return projectTaskRepository.save(projectTask);


    }

    /**
     * Gets a list of Project Tasks associated with a specific Backlog.
     * @param backlogId The backlog's projectIdentifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Gets a list of Project Tasks ordered by priority.
     */
    @Override
    public Iterable<ProjectTask> findBacklogById(String backlogId, Principal principal) {

        projectService.findProjectByIdentifier(backlogId, principal);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }

    /**
     * Gets the project task with the specified project sequence and is a part of the proper backlog.
     * @param backlogId The backlog identifier that the project task is a part of.
     * @param projectSequence The project sequence identifying a project task.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns a project task.
     */
    @Override
    public ProjectTask findProjectTaskByBackLogIdAndProjectSequence(String backlogId, String projectSequence,
                                                                    Principal principal) {

        // make sure we are searching on the right backlog.
        projectService.findProjectByIdentifier(backlogId, principal);

        // make sure that our project task exists.
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + projectSequence + "' not found");
        }

        // make sure that the backlog/project id in the path corresponds to the right project.
        if(!projectTask.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project Task '" + projectSequence + "' does not exist in project: '" + backlogId);
        }

        return projectTask;
    }

    /**
     * Updates the project task with the specified project sequence and backlogId.
     * @param updatedProjectTask The project task with the newly updated content(s).
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be updated.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns the updated project task.
     */
    @Override
    public ProjectTask updateProjectTaskByProjectSequenceAndBacklogId(ProjectTask updatedProjectTask, String backlogId,
                                                                      String projectSequence, Principal principal) {

        // find existing project task
        findProjectTaskByBackLogIdAndProjectSequence(backlogId, projectSequence, principal);

        // save update.
        return projectTaskRepository.save(updatedProjectTask);
    }

    /**
     * Deletes a project task with the specified project sequence and backlogId.
     * @param backlogId The backlog Id that this project task is a part of.
     * @param projectSequence The project sequence of the project task to be removed.
     * @param principal The object expected to hold the logged in user's information (such as username).
     */
    @Override
    public void deleteProjectTaskByProjectSequenceAndBacklogId(String backlogId, String projectSequence,
                                                               Principal principal) {
        // find existing project task
        ProjectTask projectTask = findProjectTaskByBackLogIdAndProjectSequence(backlogId, projectSequence, principal);

        projectTaskRepository.delete(projectTask);
    }
}
