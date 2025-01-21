package com.hopoong.dspmigration.app.legacy.api.adgroup;


import com.hopoong.dspmigration.app.legacy.api.adgroup.model.*;
import com.hopoong.dspmigration.app.legacy.api.campaign.LegacyCampaignService;
import com.hopoong.dspmigration.app.legacy.api.campaign.model.LegacyCampaignResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adgroup/v1")
public class LegacyAdGroupController {

    private final LegacyAdGroupService service;
    private final LegacyCampaignService campaignService;

    @PostMapping("")
    public LegacyAdGroupResp create(@RequestBody LegacyAdGroupCreateRequest req) {
        LegacyCampaignResult campaign = campaignService.find(req.campaignId());
        return LegacyAdGroupResp.from(service.create(
                new LegacyAdGroupCreateCommand(req.name(), campaign.id(), campaign.userId(),
                        req.linkUrl())));
    }

    @GetMapping("/{id}")
    public LegacyAdGroupResp find(@PathVariable("id") Long id) {
        return LegacyAdGroupResp.from(service.find(id));
    }

    @PatchMapping("/name")
    public LegacyAdGroupResp updateName(@RequestBody LegacyAdGroupUpdateNameRequest req) {
        return LegacyAdGroupResp.from(service.updateName(req.id(), req.name()));
    }

    @PatchMapping("/linkUrl")
    public LegacyAdGroupResp updateLinkUrl(@RequestBody LegacyAdGroupUpdateLinkUrlRequest req) {
        return LegacyAdGroupResp.from(service.updateLinkUrl(req.id(), req.linkUrl()));
    }

    @DeleteMapping("/{id}")
    public LegacyAdGroupResp delete(@PathVariable("id") Long id) {
        return LegacyAdGroupResp.from(service.delete(id));
    }

}
