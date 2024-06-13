package com.enerkom.karyawan.repository;

import com.enerkom.karyawan.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAllByOrderByJobName(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * from job")
    List<Job> getAllJobs();

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE job SET employee_id = :employee_id where id = :id")
    int updateJob(@Param("employee_id") Long employee_id, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE job SET is_finished = true WHERE id = :id")
    void updateJobToBeFinished(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM job where job_name = :job_name ")
    Job getOneJob(@Param("job_name") String job_name);

}
