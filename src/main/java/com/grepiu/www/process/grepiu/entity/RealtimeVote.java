package com.grepiu.www.process.grepiu.entity;

import com.grepiu.www.process.grepiu.entity.sub.RealtimeVoteItem;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "realtimeVote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeVote {

  @ApiModelProperty(hidden = true, value = "일련번호")
  @Id
  private String id;

  @ApiModelProperty(value = "투표제목")
  private String subject;

  @ApiModelProperty(value = "투표내용")
  private String contents;

  @ApiModelProperty(value = "투표항목")
  private List<RealtimeVoteItem> items;
}
