package com.anthive.article.adapter;

import com.anthive.article.application.article.required.IdGenerator;
import kuke.board.common.snowflake.Snowflake;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator implements IdGenerator {
    private final Snowflake snowflake = new Snowflake();

    @Override
    public Long nextId() {
        return snowflake.nextId();
    }
}
