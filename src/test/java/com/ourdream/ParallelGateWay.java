package com.ourdream;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 并行网关
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParallelGateWay {


}
