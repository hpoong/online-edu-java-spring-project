package com.hopoong.dspmigration.app.legacy.api.keyword;

import com.hopoong.dspmigration.app.legacy.api.adgroup.LegacyAdGroupService;
import com.hopoong.dspmigration.app.legacy.api.adgroup.model.LegacyAdGroupResult;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordCreateRequest;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword/v1")
public class LegacyKeywordController {

  private final LegacyKeywordService service;
  private final LegacyAdGroupService adGroupService;

  @PostMapping("")
  public LegacyKeywordResp create(@RequestBody LegacyKeywordCreateRequest req) {
    LegacyAdGroupResult adGroup = adGroupService.find(req.adGroupId());
    return LegacyKeywordResp.from(service.create(
        new LegacyKeywordCreateCommand(req.text(), adGroup.id(), adGroup.userId())));
  }

  @GetMapping("/{id}")
  public LegacyKeywordResp find(@PathVariable("id") Long id) {
    return LegacyKeywordResp.from(service.find(id));
  }

  @DeleteMapping("/{id}")
  public LegacyKeywordResp delete(@PathVariable("id") Long id) {
    return LegacyKeywordResp.from(service.delete(id));
  }
}
