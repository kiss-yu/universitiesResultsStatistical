package com.vote.api.service.impl;

import com.vote.api.service.ApiService;
import com.vote.common.Const;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AliDatavApiServiceImpl implements ApiService{

    @Override
    public String getTitle() {
        return "[{\"title\":\"" + Const.getTitle() + "\"}]";
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getVoteKeyAndValue() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (Map.Entry<String,Integer> entry:Const.getVoteMsg().entrySet()){
            stringBuffer.append("{\"key\":\"" + entry.getKey() + "\",")
                        .append("\"value\":" + entry.getValue() + "},");
        }
        stringBuffer.setCharAt(stringBuffer.length() - 1,']');
        return stringBuffer.toString();
    }

    @Override
    public String getSumVote() {
        return "[{\"name\":\"有效票数\",\"sum_vote\":" + Const.getSumVote() + "}]";
    }

    public String getTable(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[{\"type\":\"" + Const.getType() + "\",")
                    .append("\"value\":\"票数\"},")
                    .append("{");
        for (Map.Entry<String,Integer> entry:Const.getVoteMsg().entrySet()){
            stringBuffer.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
        }
        stringBuffer.setCharAt(stringBuffer.length() - 1,'}');
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
