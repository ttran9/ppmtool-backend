package tran.example.ppmtool.services.projects;

import tran.example.ppmtool.domain.project.Project;

import java.security.Principal;

public interface ProjectService {

    /**
     * saves or updates a project.
     * @param project The project to be saved or updated.
     * @return Returns the saved or updated object.
     */
    Project saveOrUpdateProject(Project project);

    /**
     * retrieves the project by the projectIdentifier
     * @param projectId The project's projectIdentifier field.
     * @return Returns the project with the specified projectIdentifier and if the username matches the projectLeader name.
     */
    Project findProjectByIdentifier(String projectId);

    /**
     * A method to retrieve all the objects in our database.
     * @return Returns all the projects.
     */
    Iterable<Project> findAllProjects();

    /**
     * deletes a project with the specified identifier
     * @param projectId The project's identifier.
     */
    void deleteProjectByIdentifier(String projectId);
}
