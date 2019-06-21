package tran.example.ppmtool.web.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tran.example.ppmtool.domain.project.Project;
import tran.example.ppmtool.services.projects.ProjectService;
import tran.example.ppmtool.services.validations.MapValidationErrorService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    private ProjectService projectService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult,
                                              Principal principal) {
        // we should return a generic type so we can return a ResponseEntity with more than just Project.

        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if (errorMap != null) return errorMap;

        Project project1 = this.projectService.saveOrUpdateProject(project, principal);
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectId, Principal principal) {

        Project project = this.projectService.findProjectByIdentifier(projectId, principal);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return this.projectService.findAllProjects(principal);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {
        this.projectService.deleteProjectByIdentifier(projectId, principal);

        return new ResponseEntity<>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
    }
}
