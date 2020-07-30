package com.grepiu.www.process.grepiu.entity.sub;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.Data;
import org.assertj.core.util.Lists;

@Data
public class RealtimeVoteItem {

  @Parameter(description = "항목")
  private String item;

  @Parameter(hidden = true, description = "투표")
  private List<String> voteIp = Lists.newArrayList();

  @Parameter(hidden = true, description = "투표 카운트")
  private long vote = 0;
}
