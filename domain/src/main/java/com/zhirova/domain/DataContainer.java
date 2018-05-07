package com.zhirova.domain;


public class DataContainer<R> {

    private final R data;
    private final Source source;


    public enum Source{
        LOCAL, REMOTE
    }


    public DataContainer(R data, Source source){
        this.data = data;
        this.source = source;
    }


    public R getData(){
        return data;
    }


    public boolean isLocal(){
        return source == Source.LOCAL;
    }


}
