package com.tanx.expirit.history;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    int historySeq;
    String email;
    String exDate;
    String exNo;
    String historyVal;
    String day;
    String cntVal;
}
