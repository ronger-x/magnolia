package com.rymcu.magnolia.web;

import com.rymcu.magnolia.constant.RabbitQueueConstant;
import com.rymcu.magnolia.sender.CommonSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ronger
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {

    @Resource
    CommonSender sender;

    @PostMapping("/send-message")
    public String sendMessage(String message) {
        sender.send(RabbitQueueConstant.COMMON_QUEUE, message);
        return "success";
    }

}
