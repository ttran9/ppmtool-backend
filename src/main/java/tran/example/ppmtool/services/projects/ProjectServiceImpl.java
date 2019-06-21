package tran.example.ppmtool.services.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.project.Backlog;
import tran.example.ppmtool.domain.project.Project;
import tran.example.ppmtool.exceptions.projects.ProjectIdException;
import tran.example.ppmtool.exceptions.projects.ProjectNotFoundException;
import tran.example.ppmtool.repositories.applicationusers.ApplicationUserRepository;
import tran.example.ppmtool.repositories.project.BacklogRepository;
import tran.example.ppmtool.repositories.project.ProjectRepository;

import java.security.Principal;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository,
                              ApplicationUserRepository applicationUserRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    /**
     * saves or updates a project.
     * @param project The project to be saved or updated.
     * @param principal The object expected to hold the logged in user's information (such as username).
     * @return Returns the saved or updated object.
     */
    @Override
    public Project saveOrUpdateProject(Project project, Principal principal) {

        /*
         * check for the case where we are trying to update a project not in our account.
         * check for the case where we are trying to pass in an invalid id but we still create a project (we don't want this case!)
         */
        if(project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if(existingProject != null && (!existingProject.getProjectLeader().equals(principal.getName()))) {
                throw new ProjectNotFoundException("Project not found in your account");
            }
            else if(existingProject == null) {
                // passing in the invalid database id
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it doesn't exist.");
            }
        }

        try {
            ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            String projectIdentifierUpperCase = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectIdentifierUpperCase);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifierUpperCase);
            }

            if(project.getId() != null) {
                // since we are only updating the relevant project information info just grab our existing back log.
                Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifierUpperCase);
                project.setBacklog(backlog);
            }

            return projectRepository.save(project);
        } catch(Exception ex) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    /**
     * retrieves the project by the projectIdentifier
     * @param projectId The project's projectIdentifier field.
     * @param principal The logged in user, may not be the owner of this project.
     * @return Returns the project with the specified projectIdentifier and if the username matches the projectLeader name.
     */
    @Override
    public Project findProjectByIdentifier(String projectId, Principal principal) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' doesn't exist");
        }

        if (!project.getProjectLeader().equals(principal.getName())) {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    /**
     * A method to retrieve all the objects in our database.
     * @param principal The logged in user to retrieve all the projects for.
     * @return Returns all the projects.
     */
    @Override
    public Iterable<Project> findAllProjects(Principal principal) {
        String username = principal.getName();
        return projectRepository.findAllByProjectLeader(username);
    }

    /**
     * deletes a project with the specified identifier
     * @param projectId The project's identifier.
     * @param principal The object expected to hold the logged in user's information (such as username).
     */
    @Override
    public void deleteProjectByIdentifier(String projectId, Principal principal) {
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//
//        if(project == null) {
//            throw new ProjectIdException("Cannot Delete Project with ID '" + projectId + "'. This project doesn't exist");
//        }

        projectRepository.delete(findProjectByIdentifier(projectId, principal));
    }
}
