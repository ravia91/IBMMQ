package com.example.ibmmq;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RestController
public class QueuePublisher {


  @Autowired
  private JmsTemplate jmsTemplate;


  @PostMapping("send")
  String send(@RequestBody JsonNode request,
              @RequestHeader(value = "requestId", required = false) String requestId){
    try{
      jmsTemplate.send("DEV.QUEUE.1", new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
          Message mapMessage = session.createMapMessage();
          mapMessage.setStringProperty("requestId", requestId);
          mapMessage.setStringProperty("body", request.toString());
          return mapMessage;
        }
      });
      return "OK";
    }catch(JmsException ex){
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
