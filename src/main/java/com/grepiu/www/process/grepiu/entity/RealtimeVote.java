package com.grepiu.www.process.grepiu.entity;

import com.grepiu.www.process.grepiu.entity.sub.RealtimeVoteItem;
import io.swagger.v3.oas.annotations.Parameter;
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

  @Parameter(hidden = true, description = "일련번호")
  @Id
  private String id;

  @Parameter(description = "투표제목")
  private String subject;

  @Parameter(description = "투표내용")
  private String contents;

  @Parameter(description = "투표항목")
  private List<RealtimeVoteItem> items;
}
