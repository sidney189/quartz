package com.example.task.service.impl;

import com.example.task.dao.TaskDao;
import com.example.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Override
    public Integer getCount() {
        return taskDao.getCount();
    }
}
