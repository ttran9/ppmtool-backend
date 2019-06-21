package tran.example.ppmtool.services.projects;

import tran.example.ppmtool.domain.project.Project;

import java.security.Principal;

public interface ProjectService {

    /**
     * saves or updates a project.
     * @param project The project to be saved or updated.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns the saved or updated object.
     */
    Project saveOrUpdateProject(Project project, Principal principal);

    /**
     * retrieves the project by the projectIdentifier
     * @param projectId The project's projectIdentifier field.
     * @param principal The logged in user, may not be the owner of this project.
     * @return Returns the project with the specified projectIdentifier and if the username matches the projectLeader name.
     */
    Project findProjectByIdentifier(String projectId, Principal principal);

    /**
     * A method to retrieve all the objects in our database.
     * @param principal The logged in user to retrieve all the projects for.
     * @return Returns all the projects.
     */
    Iterable<Project> findAllProjects(Principal principal);

    /**
     * deletes a project with the specified identifier
     * @param projectId The project's identifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     */
    void deleteProjectByIdentifier(String projectId, Principal principal);
}
