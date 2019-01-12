package com.ck.customcrud.controller;

import com.ck.customcrud.entity.TestUsers;
import com.ck.customcrud.service.TestService;
import com.ck.enhance.toolkit.TableInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈坤
 * @serial 2019/1/12
 */
@RestController
@RequestMapping("/testUser")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/mc")
    public Object getMapCache() {
        return TableInfoHelper.getTableInfoCache();
    }

    @GetMapping("/{id}")
    public TestUsers getById(@PathVariable Long id) {
        return this.testService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Long id) {
        return this.testService.removeById(id);
    }

    @PostMapping("/")
    public boolean save(TestUsers testUsers) {
        return this.testService.save(testUsers);
    }

    @PostMapping("/s2")
    public boolean save2(TestUsers testUsers) {
        return this.testService.saveIdWorkerKey(testUsers);
    }

    @PutMapping("/")
    public boolean updateById(TestUsers testUsers) {
        return this.testService.updateById(testUsers);
    }
}
