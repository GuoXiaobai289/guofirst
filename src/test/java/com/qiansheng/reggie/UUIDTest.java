package com.qiansheng.reggie;

import com.qiansheng.reggie.util.SMSUtil;
import com.qiansheng.reggie.util.SnowFlakeUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    public void tt() throws Exception {

        for(int i=1;i<=10;i++){
            System.out.println(SnowFlakeUtil.getId());
        }

    }
}
