package com.enerkom.karyawan.service;

import com.enerkom.karyawan.entity.Employee;
import com.enerkom.karyawan.entity.Job;
import com.enerkom.karyawan.repository.EmployeeRepository;
import com.enerkom.karyawan.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Job> getJobsSortedByName(Pageable pageable) {
        return jobRepository.findAllByOrderByJobName(pageable);
    }

    public List<Job> getAllJob(){
        return this.jobRepository.getAllJobs();
    }

    public Job getOneJobByName(String job_name){
        return this.jobRepository.getOneJob(job_name);
    }

    @Transactional
    public int insertEmployeeToJob(Long employeeId, Long jobId){
        Optional<Employee> employee = this.employeeRepository.findById(employeeId);
        return this.jobRepository.updateJob(employee.get().getId(), jobId);
    }


    public void updateJobIsFinished(Long jobId){
        this.jobRepository.updateJobToBeFinished(jobId);
    }

    public void deleteJob(Long jobId){
        this.jobRepository.deleteById(jobId);
    }

    @Transactional
    public Optional<Job> getJobById(Long jobId){
        return this.jobRepository.findById(jobId);
    }
}
