package org.camunda.bpm.spring.boot.starter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.spring.boot.starter.test.TestNoJpaApplication;
import org.camunda.bpm.spring.boot.starter.test.jpa.domain.TestEntity;
import org.camunda.bpm.spring.boot.starter.test.jpa.repository.TestEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestNoJpaApplication.class }, webEnvironment = WebEnvironment.NONE)
@Transactional
public class CamundaNoJpaAutoConfigurationIT extends AbstractCamundaAutoConfigurationIT {

  @Autowired
  private TestEntityRepository testEntityRepository;

  @Test
  public void jpaDisabledTest() {
    TestEntity testEntity = testEntityRepository.save(new TestEntity());
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("test", testEntity);
    try {
      runtimeService.startProcessInstanceByKey("TestProcess", variables);
      fail();
    } catch (ProcessEngineException e) {
      assertNotNull(e);
    }
  }

  @Test
  public void pojoTest() {
    Map<String, Object> variables = new HashMap<String, Object>();
    Pojo pojo = new Pojo();
    variables.put("test", pojo);
    assertNotNull(runtimeService.startProcessInstanceByKey("TestProcess", variables));
  }

  public static class Pojo implements Serializable {

    private static final long serialVersionUID = 1L;

  }
}
