package com.enerkom.karyawan.controller;

import com.enerkom.karyawan.entity.Job;
import com.enerkom.karyawan.security.UserDetailsImpl;
import com.enerkom.karyawan.service.EmployeeService;
import com.enerkom.karyawan.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enerkom/job")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private JobService jobService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllJob(){

        List<Job> jobs = this.jobService.getAllJob();
        List<String> jobName = jobs.stream()
                .filter(a -> !a.isFinished())
                .map(Job::getJobName)
                .collect(Collectors.toList());

        return new ResponseEntity<>(jobName, HttpStatusCode.valueOf(200));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getOneJobByName(@RequestParam String jobName){
        var job = this.jobService.getOneJobByName(jobName);
        if(job ==  null){
            return new ResponseEntity<>("Pekerjaan tidak ditemukan", HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(job.getJobName(), HttpStatusCode.valueOf(200));
    }


    @GetMapping("/pageable") //PAGING AND SORTING
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<Job>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "jobName") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Job> jobPage = jobService.getJobsSortedByName(pageable);
        return ResponseEntity.ok(jobPage);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJobRecord(@PathVariable("jobId") Long jobId){
        Long userId= 0L;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
             userId = userDetails.id();
             log.info("data user id: {}", userDetails.id());
             log.info("data user object: {}", userDetails.getUsername());
        }

        var employee = this.employeeService.findEmployeeById(userId);
        if(!Objects.equals(employee.get().getUsers().getId(), userId)){
            return new ResponseEntity<>("Ada kesalahan id", HttpStatusCode.valueOf(400));
        }

        var insert = this.jobService.insertEmployeeToJob(employee.get().getId(), jobId);


        return new ResponseEntity<>("Anda telah menambahkan pekerjaan", HttpStatusCode.valueOf(200));
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/finish/{jobId}")
    public ResponseEntity<?> finishJodb(@PathVariable("jobId") Long jobId){

        Long userId= 0L;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            userId = userDetails.id();
            log.info("data user id: {}", userDetails.id());
            log.info("data user object: {}", userDetails.getUsername());
        }

        Optional<Job> job = this.jobService.getJobById(jobId);
        Job jobs = job.get();

        log.info("data job di finsih Job: {}", jobs.getJobName());

        if(job.get().getEmployee().getUsers().getId() != userId){
            return new ResponseEntity<>("Ini bukan pekerjaan Anda", HttpStatusCode.valueOf(401));
        } else if(job.get().getEmployee().getUsers().getId() == null){
            return new ResponseEntity<>("Ini pun bukan pekerjaan Anda", HttpStatusCode.valueOf(401));
        }

        jobService.updateJobIsFinished(jobId);
        return new ResponseEntity<>("Pekerjaan berhasil diselesaikan", HttpStatusCode.valueOf(200));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<?> deleteFinishedJob(@PathVariable("jobId") Long jobId){

        Long userId= 0L;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            userId = userDetails.id();
            log.info("data user id: {}", userDetails.id());
            log.info("data user object: {}", userDetails.getUsername());
        }

        Optional<Job> job = this.jobService.getJobById(jobId);

        if(job.get().getEmployee().getUsers().getId() != userId){
            return new ResponseEntity<>("Ini bukan pekerjaan Anda", HttpStatusCode.valueOf(401));
        } else if(job.get().getEmployee().getUsers().getId() == null){
            return new ResponseEntity<>("Ini pun bukan pekerjaan Anda", HttpStatusCode.valueOf(401));
        }

        boolean finishedJob = job.get().isFinished();

        if(finishedJob){
            jobService.deleteJob(jobId);
        }else{
            return new ResponseEntity<>("Pekerjaan belum selesai", HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<>("Pekerjaan berhasil dihapus", HttpStatusCode.valueOf(200));
    }


    private Long authenticatedUserId(){
        Long id = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            id = userDetails.id();
            log.info("data user id: {}", userDetails.id());
            log.info("data user object: {}", userDetails.getUsername());
        }
        return id;
    }



}
