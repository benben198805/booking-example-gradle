package leasing.presentation.controller;

import leasing.application.PromotionProposalApplicationService;
import leasing.domain.model.PromotionProposal;
import leasing.presentation.assembler.ModelToResponseMapper;
import leasing.presentation.dto.response.promotionProposal.PromotionProposalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promotion-proposal")
@Slf4j
@Validated
@AllArgsConstructor
public class PromotionProposalController {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final PromotionProposalApplicationService promotionProposalApplicationService;

    @GetMapping
    public Page<PromotionProposalResponse> queryAllPromotionProposals(
            @PageableDefault(size = DEFAULT_PAGE_SIZE)
            @SortDefault(sort = "createdTime", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return promotionProposalApplicationService.queryAllPromotionProposal(pageable)
                                                  .map(ModelToResponseMapper.INSTANCE::mapToPromotionProposalResponse);
    }

    @GetMapping("/{id}")
    public PromotionProposalResponse queryPromotionProposalById(@PathVariable Long id) {
        PromotionProposal promotionProposal = promotionProposalApplicationService.queryPromotionProposal(id);
        return ModelToResponseMapper.INSTANCE.mapToPromotionProposalResponse(promotionProposal);
    }

}
