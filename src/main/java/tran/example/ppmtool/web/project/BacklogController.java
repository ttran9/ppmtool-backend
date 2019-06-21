package tran.example.ppmtool.web.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tran.example.ppmtool.domain.project.ProjectTask;
import tran.example.ppmtool.services.projecttasks.ProjectTaskService;
import tran.example.ppmtool.services.validations.MapValidationErrorService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private ProjectTaskService projectTaskService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult bindingResult, @PathVariable String backlogId,
                                                     Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);

        if(errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlogId, projectTask, principal);

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getProjectBackLog(@PathVariable String backlogId, Principal principal) {
        return projectTaskService.findBacklogById(backlogId, principal);
    }

    @GetMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId, Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByBackLogIdAndProjectSequence(backlogId, projectTaskId, principal);

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask, BindingResult bindingResult,
                                               @PathVariable String backlogId, @PathVariable String projectTaskId,
                                               Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);

        if(errorMap != null) return errorMap;

        ProjectTask updatedProjectTaskTwo = projectTaskService.updateProjectTaskByProjectSequenceAndBacklogId(
                updatedProjectTask, backlogId, projectTaskId, principal);

        return new ResponseEntity<>(updatedProjectTaskTwo, HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{projectTaskId}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId,
                                               Principal principal) {
        projectTaskService.deleteProjectTaskByProjectSequenceAndBacklogId(backlogId, projectTaskId, principal);

        return new ResponseEntity<>("Project Task " + projectTaskId + " was deleted successfully", HttpStatus.OK);
    }
}
