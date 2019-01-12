package com.ck.customcrud.service.impl;

import com.ck.customcrud.entity.TestUsers;
import com.ck.customcrud.mapper.TestMapper;
import com.ck.customcrud.service.TestService;
import com.ck.enhance.Interfaces.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 陈坤
 * @serial 2019/1/12
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestMapper, TestUsers> implements TestService {

}
