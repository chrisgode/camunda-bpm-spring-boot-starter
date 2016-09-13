package org.camunda.bpm.spring.boot.starter;

import static org.junit.Assert.assertTrue;

import org.camunda.bpm.spring.boot.starter.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CamundaBpmActuatorConfigurationIT {

  @Value("${local.server.port}")
  private int port;

  @Test
  public void jobExecutorHealthIndicatorTest() {
    final String body = getHealthBody();
    assertTrue("wrong body " + body, body.contains("jobExecutor\":{\"status\":\"UP\""));
  }

  @Test
  public void processEngineHealthIndicatorTest() {
    final String body = getHealthBody();
    assertTrue("wrong body " + body, body.contains("\"processEngine\":{\"status\":\"UP\",\"name\":\"testEngine\"}"));
  }

  private String getHealthBody() {
    ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:{port}/health", String.class, port);
    final String body = entity.getBody();
    return body;
  }
}
