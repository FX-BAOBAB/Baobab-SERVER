package store.domain.loading.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.domain.loading.business.LoadingBusiness;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/loading")
public class LoadingController {

    private final LoadingBusiness loadingBusiness;

    @PostMapping("/{receivingId}")
    public String loadingReceiving(@PathVariable Long receivingId, RedirectAttributes redirectAttributes){
        loadingBusiness.setLoading(receivingId);
        redirectAttributes.addAttribute("receivingId",receivingId);
        return "redirect:/api/receiving/{receivingId}";
    }

}
