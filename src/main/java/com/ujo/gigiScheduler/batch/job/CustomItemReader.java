package com.ujo.gigiScheduler.batch.job;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;


public class CustomItemReader<T> implements ItemReader<T>{

    private List<T> items;

    /**
     * 리스트 초기화
     * */
    public CustomItemReader(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    /**
     * read 할 때마다 리스트에서 하나씩 제거하며 반환 없으면 null(종료)
     * */
    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!items.isEmpty()){
            return items.remove(0);
        }

        return null;
    }
}
