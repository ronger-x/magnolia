package com.rymcu.magnolia;

import com.alibaba.fastjson.JSONObject;
import com.rymcu.magnolia.constant.RabbitQueueConstant;
import com.rymcu.magnolia.entity.PatFeeItem;
import com.rymcu.magnolia.entity.PatInfo;
import com.rymcu.magnolia.sender.CommonSender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MagnoliaApplicationTests {

    @Resource
    private CommonSender commonSender;

    @Test
    void contextLoads() {
        PatInfo patInfo = new PatInfo();
        patInfo.setPatientCode("02852365");
        patInfo.setPatientName("李云");
        List<PatFeeItem> patFeeItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PatFeeItem patFeeItem = new PatFeeItem();
            patFeeItem.setFeeItemName("收费项目" + i);
            patFeeItem.setIdPatFeeItem(i);
            patFeeItems.add(patFeeItem);
        }
        patInfo.setPatFeeItems(patFeeItems);
        commonSender.send(RabbitQueueConstant.WEIXIN_QUEUE, JSONObject.toJSONString(patInfo));
    }

}
