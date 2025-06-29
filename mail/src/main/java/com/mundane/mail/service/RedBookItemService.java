package com.mundane.mail.service;

import com.mundane.mail.entity.RedBookItemEntity;
import com.mundane.mail.mapper.RedBookItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RedBookItemService {

    @Autowired
    private RedBookItemMapper itemMapper;
    public List<RedBookItemEntity> queryList() {
        RedBookItemEntity record = new RedBookItemEntity();
        record.setStatus(1);
        return itemMapper.select(record);
    }
}
