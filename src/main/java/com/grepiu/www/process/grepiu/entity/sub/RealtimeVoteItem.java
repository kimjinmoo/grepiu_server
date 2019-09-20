package com.grepiu.www.process.grepiu.entity.sub;

import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class RealtimeVoteItem {

  @ApiModelProperty("항목")
  private String item;

  @ApiModelProperty(hidden = true, value = "투표")
  private Set<String> voteIp = new HashSet<>();

  @ApiModelProperty(hidden = true, value = "투표 카운트")
  private long vote = 0;
}
