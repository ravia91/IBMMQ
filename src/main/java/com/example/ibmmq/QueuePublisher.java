package com.example.ibmmq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueuePublisher {


  @Autowired
  private JmsTemplate jmsTemplate;

  @Value("${ibm.mq.requestQueueName}")
  private String destinationName;


  @PostMapping("send")
  public String send(@RequestBody String request,
      @RequestHeader(value = "requestId", required = false) String requestId) {
    try {
      jmsTemplate.send(destinationName, new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
          TextMessage textMessage = session.createTextMessage();
          textMessage.setStringProperty("requestId", requestId);
          textMessage.setText(request);
          return textMessage;
        }
      });
      return "OK";
    } catch (JmsException ex) {
      ex.printStackTrace();
      return "FAIL";
    }
  }

//  @GetMapping("recv")
//  String recv(){
//    try{
//      return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
//    }catch(JmsException ex){
//      ex.printStackTrace();
//      return "FAIL";
//    }
//  }

}
