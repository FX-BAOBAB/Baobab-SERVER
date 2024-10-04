package store.common.exception;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.common.error.GoodsErrorCode;
import store.common.exception.goods.GoodsNotFoundException;
import store.common.exception.goods.InvalidGoodsStatusException;
import store.common.exception.receiving.NotOwnerException;
import store.domain.goods.service.GoodsService;

@Slf4j
@ControllerAdvice
@Order(value = Integer.MIN_VALUE)
@RequiredArgsConstructor
public class GoodsExceptionHandler {

    private final GoodsService goodsService;

    @ExceptionHandler(value = GoodsNotFoundException.class)
    public String imageException(GoodsNotFoundException e, Model model,
        RedirectAttributes redirectAttributes) {
        log.info("", e);
        redirectAttributes.addFlashAttribute("error", GoodsErrorCode.GOODS_NOT_FOUND.getDescription());
        return "redirect:/api/goods";
    }

    @ExceptionHandler(value = InvalidGoodsStatusException.class)
    public ResponseEntity<Api<Object>> InvalidGoodsStatus(InvalidGoodsStatusException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.INVALID_GOODS_STATUS));
    }

    @ExceptionHandler(value = NotOwnerException.class)
    public ResponseEntity<Api<Object>> notOwnerException(NotOwnerException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.NOT_OWNER));
    }
}
