package com.grepiu.www.process.grepiu.entity.sub;

import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.assertj.core.util.Lists;

@Data
public class RealtimeVoteItem {

  @ApiModelProperty("항목")
  private String item;

  @ApiModelProperty(hidden = true, value = "투표")
  private List<String> voteIp = Lists.newArrayList();

  @ApiModelProperty(hidden = true, value = "투표 카운트")
  private long vote = 0;
}
