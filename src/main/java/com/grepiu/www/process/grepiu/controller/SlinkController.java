package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.entity.Slink;
import com.grepiu.www.process.grepiu.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Url 줄이기 컨트롤로
 *
 */
@RestController
public class SlinkController {

  @Autowired
  private LabService labService;

  @GetMapping(value = "/s")
  public ResponseEntity<Slink> getLink(@RequestParam String s){
    return ResponseEntity.ok(labService.findSlink(s));
  }

  @PostMapping(value = "/s")
  public ResponseEntity<String> saveLink(@RequestBody String fUrl) {
    return ResponseEntity.ok(labService.saveSlink(fUrl));
  }

  @PutMapping(value="/s/{sUrl}")
  public ResponseEntity<Slink> updateLink(@PathVariable("sUrl") String sUrl, @RequestBody String fUrl) {
    return ResponseEntity.ok(labService.updateSlink(sUrl, fUrl));
  }

  @DeleteMapping(value = "/s/{sUrl}")
  public ResponseEntity<String> deleteLink(@PathVariable("sUrl") String sUrl) {
    labService.deleteSlink(sUrl);
    return ResponseEntity.ok("OK");
  }
}
